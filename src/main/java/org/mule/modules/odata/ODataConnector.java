/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.odata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.MuleMessage;
import org.mule.api.NestedProcessor;
import org.mule.api.annotations.*;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.api.transport.PropertyScope;
import org.mule.modules.odata.exception.NotAuthorizedException;
import org.mule.modules.odata.factory.ODataConsumerFactory;
import org.mule.modules.odata.factory.ODataConsumerFactoryImpl;
import org.mule.modules.odata.odata4j.extensions.OBatchRequest;
import org.mule.modules.odata.reflection.FieldDescriptor;
import org.mule.modules.odata.reflection.ReflectionUtils;
import org.mule.util.ClassUtils;
import org.mule.odata4j.consumer.ODataClientRequest;
import org.mule.odata4j.consumer.ODataConsumer;
import org.mule.odata4j.core.Guid;
import org.mule.odata4j.core.OCollection;
import org.mule.odata4j.core.OCollections;
import org.mule.odata4j.core.OComplexObjects;
import org.mule.odata4j.core.OCreateRequest;
import org.mule.odata4j.core.ODataVersion;
import org.mule.odata4j.core.OEntity;
import org.mule.odata4j.core.OModifyRequest;
import org.mule.odata4j.core.OObject;
import org.mule.odata4j.core.OProperties;
import org.mule.odata4j.core.OProperty;
import org.mule.odata4j.core.OQueryRequest;
import org.mule.odata4j.core.OSimpleObjects;
import org.mule.odata4j.edm.EdmCollectionType;
import org.mule.odata4j.edm.EdmComplexType;
import org.mule.odata4j.edm.EdmProperty.CollectionKind;
import org.mule.odata4j.edm.EdmSimpleType;
import org.mule.odata4j.edm.EdmType;
import org.mule.odata4j.format.FormatType;
import org.mule.odata4j.format.FormatWriter;
import org.mule.odata4j.jersey.consumer.ConsumerDeleteEntityRequest;
import org.mule.odata4j.jersey.consumer.JerseyClientUtil;
import org.mule.odata4j.producer.resources.BatchBodyPart;
import org.mule.odata4j.producer.resources.BatchResult;
import org.mule.odata4j.producer.resources.ODataBatchProvider.HTTP_METHOD;

/**
 * Connector for consuming OData feeds by performing read, create, update and delete operations.
 * Bath operations are also supported.
 * 
 * This version of the connector does not use any kind of authentication.  
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
@Connector(name = "odata", schemaVersion = "1.0", friendlyName = "OData", configElementName="config", metaData=MetaDataSwitch.OFF)
@ReconnectOn(exceptions = NotAuthorizedException.class)
public class ODataConnector {
	
	private static final Logger logger = Logger.getLogger(ODataConnector.class);
	public static final String BATCH_PARTS = "ODATA_CONNECTOR_BATCH_BODY_PARTS";
	
	private Map<Class<?>, Collection<FieldDescriptor>> propertiesCache = new HashMap<Class<?>, Collection<FieldDescriptor>>();
	
	/**
	 * The url of the target OData service
	 */
	private String baseServiceUri;
	
	/**
	 * The current user
	 */
	private String user;
	
	/**
	 * An instance of {@link org.mule.modules.odata.factory.ODataConsumerFactory}
	 * to intanciate the {@link ODataConsumer}. Normally you don't
	 * need to set this unless you require some custom initialization of the consumer
	 * or if you are doing test cases.
	 * 
	 * If this property is not specified, then an instance of
	 * {@link org.mule.modules.odata.factory.ODataConsumerFactoryImpl} is used
	 */
	@Configurable
	@Optional
	private ODataConsumerFactory consumerFactory;
	
	/**
	 * The protocol version to be used when consuming external services
	 */
	@Configurable
	@Default("V2")
	private ODataVersion consumerVersion = ODataVersion.V2 ;
	
	/**
	 * The consumer to use
	 */
	private ODataConsumer consumer;
	
	/**
	 * The namig policy to be used when mapping pojo's attributes to OData entities.
	 * Depending on the OData service you're consuming, you might find that attributes usually follows a
	 * lower camel case format (e.g.: theAttribute) or an upper camel case format (e.g.: TheAttribute).
	 * 
	 * The naming format assumes that your pojo's properties follow the lower camel case
	 * format (just as the java coding standard dictates) and translates to the format that the OData service
	 * is expecting.
	 * 
	 * Valid values are: LOWER_CAMEL_CASE and UPPER_CAMEL_CASE.
	 */
	@Configurable
	@Default("LOWER_CAMEL_CASE")
	private PropertyNamingFormat namingFormat = PropertyNamingFormat.LOWER_CAMEL_CASE;
	
	/**
	 * The format of the payload to be used during communication.
	 * Valid values are JSON and ATOM
	 */
	@Configurable
	@Default("JSON")
	private FormatType formatType = FormatType.JSON;
	
	/**
	 * @param username the authorization username
	 * @param password the authorization password
	 * @param serviceUri the URI of the target OData Service
	 * @throws ConnectionException
	 */
	@Connect
	public void connect(@Optional @ConnectionKey String username, @Optional @Password String password, String serviceUri) throws ConnectionException {
		if(StringUtils.isBlank(serviceUri)){
			throw new ConnectionException(ConnectionExceptionCode.UNKNOWN_HOST, "", "Service Uri was not configured");
		}
		if (StringUtils.isBlank(username) && StringUtils.isBlank(password)) {
			this.consumer = this.getConsumerFactory().newConsumer(serviceUri, this.getFormatType(), null, null, this.getConsumerVersion());
			this.user = "<<anonymous>>";
			this.baseServiceUri = serviceUri;
		} else if (!StringUtils.isBlank(username) && !StringUtils.isBlank(password)) {
			this.consumer = this.getConsumerFactory().newConsumer(serviceUri, this.getFormatType(), username, password, this.getConsumerVersion());
			this.user = username;
			this.baseServiceUri = serviceUri;
		} else {
			throw new ConnectionException(ConnectionExceptionCode.INCORRECT_CREDENTIALS, "", "username and password must either be both blank for anonymous access or both not blank for basic authentication");
		}
		try{
			this.consumer.getEntitySets();
		}catch(Exception ex){
			throw new ConnectionException(ConnectionExceptionCode.UNKNOWN, "", "Unexpected error\n"+ex.getMessage(),ex.getCause());
		}
	}
	
	@ConnectionIdentifier
	public String getConnectionIdentifier() {
		return String.format("%s@%s", this.user, this.baseServiceUri);
	}
	
	@ValidateConnection
	public boolean isConnected() {
		return this.consumer != null;
	}
	
	@Disconnect
	public void disconnect() {
		this.consumer = null;
		this.user = null;
		this.baseServiceUri = null;
	}
	
	/**
	 * This ooperation will force the disconnection of the current session.
	 * <p>
	 * Next time connection manager recieves a call to an operation it will reconnect using the new credentials (if tenant info was provided)
	 * 
	 * {@sample.xml ../../../doc/OData-connector.xml.sample odata:force-disconnect}
	 */
	@Processor
	public void forceDisconnect() {
		disconnect();
	}
	
	/**
     * Reads entities from an specified set and returns it as a list of pojos. You can optionally provide a
     * returnClass parameter which will allow you to get the result as a list of pojos (as long as that
     * class is compliant with the Bean contract). If you don't specify it, you'll get a list of generic
     *  ${OEntity} objects.
     *
     * {@sample.xml ../../../doc/OData-connector.xml.sample odata:get-entities}
     *
     * @param returnClass the canonical class name for the pojo instances to be returned. If none especified then generic OEntity
     * 		  class will be returned. OEntity is a pojo which represents the set's metadata and allows for browsing
     * @param entitySetName the name of the set to be read
     * @param filter an OData filtering expression. If not provided, no filtering occurs (see http://www.odata.org/developers/protocols/uri-conventions#FilterSystemQueryOption)
     * @param orderBy the ordering expression. If not provided, no ordering occurs (see http://www.odata.org/developers/protocols/uri-conventions#OrderBySystemQueryOption(
     * @param skip number of items to skip, usefull for pagination. If not provided, no records are skept (see http://www.odata.org/developers/protocols/uri-conventions#SkipSystemQueryOption)
     * @param expand Sets the expand expressions.
     * @param top number of items to return (see http://www.odata.org/developers/protocols/uri-conventions#TopSystemQueryOption)
     * @param select the selection clauses. If not specified, all fields are returned (see http://www.odata.org/developers/protocols/uri-conventions#SelectSystemQueryOption)
     * @return a list of objects of class "returnClass" representing the obtained entities
     */
    @Processor
    @SuppressWarnings("unchecked")
    public List<Object> getEntities(
    						@Default("OEntity") String returnClass,
    						String entitySetName,
    						@Optional String filter,
    						@Optional String orderBy,
    						@Optional String expand,
    						@Optional Integer skip,
    						@Optional Integer top,
    						@Optional String select) {
    	
    	Class<?> clazz = this.getClass(returnClass);
    	
    	
    	OQueryRequest<?> request =  this.consumer.getEntities(clazz, entitySetName)
										.filter(filter)
										.orderBy(orderBy)
										.expand(expand)
										.select(select);
    	if (skip != null) {
    		request.skip(skip);
    	}
    	
    	if (top != null) {
    		request.top(top);
    	}

    	return (List<Object>) request.execute().toList();
    }

    /**
     * Inserts an entity from an input pojo.
     * 
     * To specify the entitie's id, your pojo can rather have an attribute of type
     * {@link Guid} or to have a string attribute annotated with {@link org.mule.modules.odata.annotation.Guid}
     * 
     * {@sample.xml ../../../doc/OData-connector.xml.sample odata:create-entity}
     * 
     * @param message the current mule message
     * @param entity an object representing the entity
     * @param entitySetName the name of the set. If not specified then it's inferred by adding the suffix 'Set' to the objects simple class name
     */
    @Processor
    @Inject
    public void createEntity(MuleMessage message,
    						@Default("#[payload]") Object entity,
    						@Optional String entitySetName) {
    	
    	OCreateRequest<OEntity> request = this.consumer.createEntity(this.getEntitySetName(entity, entitySetName));
    	Map<String, OProperty<?>> properties = this.populateODataProperties(entity);
    	
		if (properties != null) {
			request.properties(properties.values());
		}
		
		if (!this.isBatchOperation(message, request.getRawRequest(this.baseServiceUri))) {
			request.execute(this.baseServiceUri);
		}
    }
    
    /**
     * Updates an entity represented by a pojo on the OData service
     * 
     * To specify the entitie's id, your pojo can rather have an attribute of type
     * {@link Guid} or to have a string attribute annotated with {@link org.mule.modules.odata.annotation.Guid}
     * 
     * {@sample.xml ../../../doc/OData-connector.xml.sample odata:update-entity}
     * 
     * @param message the current mule message
     * @param entity an object representing the entity
     * @param entitySetName the name of the set. If not specified then it's inferred by adding the suffix 'Set' to the objects simple class name
     * @param keyAttribute the name of the pojo's attribute that holds the entity's key. The attribute cannot hold a null value
     */
    @Processor
    @Inject
    public void updateEntity(MuleMessage message,
    						@Default("#[payload]") Object entity,
    						@Optional String entitySetName,
    						String keyAttribute) {
    	
    	Map<String, OProperty<?>> properties = this.populateODataProperties(entity);
    	
    	OProperty<?> key = properties.get(keyAttribute);
    	
    	OModifyRequest<OEntity> request = this.consumer.mergeEntity(
    											this.getEntitySetName(entity, entitySetName),
    											key.getValue(),
    											key.getType().equals(EdmSimpleType.GUID));
    	
		if (properties != null) {
			request.properties(properties.values());
		}
		
		if (!this.isBatchOperation(message, request.getRawRequest(this.baseServiceUri))) {
			request.execute(this.baseServiceUri);
		}
    }
    
    
    /**
     * Deletes an entity represented by a pojo on the OData service
     * 
     * To specify the entitie's id, your pojo can rather have an attribute of type
     * {@link Guid} or to have a string attribute annotated with {@link org.mule.modules.odata.annotation.Guid}
     * 
     * {@sample.xml ../../../doc/OData-connector.xml.sample odata:delete-entity}
     * 
     * @param message the current mule message
     * @param entity an object representing the entity
     * @param entitySetName the name of the set. If not specified then it's inferred by adding the suffix 'Set' to the objects simple class name
     * @param keyAttribute the name of the pojo's attribute that holds the entity's key. The attribute cannot hold a null value
     */
    @Processor
    @Inject
    public void deleteEntity(
    						MuleMessage message,
    						@Default("#[payload]") Object entity,
    						@Optional String entitySetName,
    						String keyAttribute) {
    	
    	Map<String, OProperty<?>> properties = this.populateODataProperties(entity);
    	OProperty<?> key = properties.get(keyAttribute);
    	
    	ConsumerDeleteEntityRequest request = this.consumer.deleteEntity(
    												this.getEntitySetName(entity, entitySetName),
    												key.getValue(),
    												key.getType().equals(EdmSimpleType.GUID));
    	
    	if (!this.isBatchOperation(message, request.getRawRequest(this.baseServiceUri))) {
			request.execute(this.baseServiceUri);
		}
    }
    
    /**
     * Executes a series of insert/update/deletes in a batch grouped in one changeset.
     * 
     * {@sample.xml ../../../doc/OData-connector.xml.sample odata:batch}
     * 
     * @param message the current mule message
     * @param processors nested processors where each individual operation is to be performed
     * @return an instance of {@link BatchResult}
     */
    @Processor
    @Inject
    public BatchResult batch(
    			MuleMessage message,
    			List<NestedProcessor> processors) {
    	
    	List<BatchBodyPart> parts = new ArrayList<BatchBodyPart>();
    	message.setInvocationProperty(BATCH_PARTS, parts);
    	
    	try {
    		for (NestedProcessor processor : processors) {
    			processor.process();
    		}
    	} catch (Exception e) {
    		throw new RuntimeException("Error on batch nested processor", e);
    	} finally {
    		message.removeProperty(BATCH_PARTS, PropertyScope.INVOCATION);
    	}
    	
    	if (parts.isEmpty()) {
    		if (logger.isDebugEnabled()) {
    			logger.debug("No parts where added by nested processors, exiting without sending a batch request");
    		}
    	}
    	
    	OBatchRequest request = this.consumer.createBatch(this.baseServiceUri);
    	BatchResult result = request.execute(parts, this.formatType);
    	
    	return result;
    	
    }

    private boolean isBatchOperation(MuleMessage message, ODataClientRequest request) {
    	List<BatchBodyPart> batchParts = message.getInvocationProperty(BATCH_PARTS);
    	
    	if (batchParts != null) {
    		batchParts.add(this.toBatchBodyPart(request));
    		return true;
    	}
    	
    	return false;
    	
    }
    
    private BatchBodyPart toBatchBodyPart(ODataClientRequest request) {
    	BatchBodyPart part = new BatchBodyPart();
    	String payload = null;
    	
    	if (request.getPayload() instanceof String) {
    		payload = (String) request.getPayload();
    	} else {
    		FormatWriter<Object> formatWriter = JerseyClientUtil.newFormatWriter(request, this.formatType, this.consumerVersion);
    		payload = JerseyClientUtil.toString(request, formatWriter);
    	}
		
		part.setEntity(payload);
		part.setHttpMethod(HTTP_METHOD.valueOf(request.getMethod()));
		part.setUri(request.getUrl());
		
		return part;
    }
    
    private String getEntitySetName(Object pojo, String entitySetName) {
    	if (pojo == null) {
    		throw new IllegalArgumentException("cannot use a null pojo");
    	}
    	
    	return StringUtils.isBlank(entitySetName) ? pojo.getClass().getSimpleName() + "Set" : entitySetName; 
    }
     
    private <T> Map<String, OProperty<?>> populateODataProperties(T object) {
		Class<?> clazz = object.getClass();
    	Collection<FieldDescriptor> fields = this.propertiesCache.get(clazz);
		
		if (fields == null) {
			fields = ReflectionUtils.getFieldDescriptors(object);
			this.propertiesCache.put(clazz, fields);
		}
		
		if (fields.isEmpty()) {
			return null;
		}
		
		Map<String, OProperty<?>> result = new HashMap<String, OProperty<?>>();
		
		try {
			for (FieldDescriptor field : fields) {
				Object value = field.getValue(object);
				String name = field.getName();
				
				if (value != null) {
					String key = this.namingFormat.toOData(name);
					
					OProperty<?> property = null;
					
					if (field.isGuid()) {
						property = OProperties.guid(key, Guid.fromString(value.toString()));
					} else {
						property = this.toOProperty(key, value);
					}
					
					if (property != null) {
						result.put(name, property);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Error populating odata properties", e);
		}

		return result;
	}
    
	private Class<?> getClass(String returnClass) {
		Class<?> clazz = null;
    	
    	try {
    		clazz = ClassUtils.loadClass(returnClass, getClass());
    	} catch (ClassNotFoundException e) {
    		throw new IllegalArgumentException(String.format("return class %s not found in classpath", returnClass), e);
    	}
		return clazz;
	}
    
    private OProperty<?> toOProperty(String key, Object value) {
    	
    	if (value instanceof Guid) {
			return OProperties.guid(key, (Guid) value);
		} else if (this.isSimpleType(value)) {
			return OProperties.simple(key, value);
		} else if (value instanceof Date) {
			return OProperties.datetime(key, (Date) value);
		} else if (value instanceof Collection) {
			Collection<?> collection = (Collection<?>) value;
			return collection.isEmpty() ? null : this.toCollectionProperty(key, collection); 
		} else {
			return this.toObjectProperty(key, value);
		}
    }
    
    private OProperty<List<OProperty<?>>> toObjectProperty(String key, Object value) {
    	return OProperties.complex(key,
    			this.getEdmComplexType(key, value),
    			new ArrayList<OProperty<?>>(this.populateODataProperties(value).values())
    			);
    }
    
    private <T> OProperty<OCollection<? extends OObject>> toCollectionProperty(String key, Collection<T> collection) {
		EdmCollectionType type = this.getCollectionType(key, collection);
		OCollection.Builder<OObject> builder = OCollections.newBuilder(type);
		EdmType itemType = null;
		
		for (T item : collection) {
			
			if (itemType == null) {
				itemType = this.getEdmType(key, item);
			}
			
			if (this.isSimpleType(item)) {
				builder.add(OSimpleObjects.create(EdmSimpleType.forJavaType(item.getClass()), item));
			} else {
				builder.add(OComplexObjects.create(
							this.getEdmComplexType(key, item),
							new ArrayList<OProperty<?>>(this.populateODataProperties(item).values())
						));
			}
					
		}
		
		return OProperties.collection(key, type, builder.build());
    }
    
    private EdmType getEdmType(String key, Object value) {
    	EdmType type = EdmSimpleType.forJavaType(value.getClass());
    	return type != null ? type : this.getEdmComplexType(key, value);
    }
    
    private EdmComplexType getEdmComplexType(String key, Object value) {
    	return EdmComplexType.newBuilder().setName(key).build();
    }
    
    private <T> EdmCollectionType getCollectionType(String key, Collection<T> collection) {
    	T sample = null;
    	
    	for (T value : collection) {
    		
    		if (value != null) {
    			sample = value;
    			break;
    		}
    	}
    	
    	if (sample == null) {
    		throw new IllegalArgumentException("Collection only had null values");
    	}
    	
    	return new EdmCollectionType(CollectionKind.List, this.getEdmType(key, sample));
    }
    
    
	private boolean isSimpleType(Object value) {
		return this.isSimpleType(value.getClass());
	}
	
	private boolean isSimpleType(Class<?> clazz) {
		return EdmSimpleType.forJavaType(clazz) != null;
	}
	
	public ODataConsumerFactory getConsumerFactory() {
		
		if (this.consumerFactory == null) {
			this.consumerFactory = new ODataConsumerFactoryImpl();
		}
		
		return consumerFactory;
	}

	public void setConsumerFactory(ODataConsumerFactory consumerFactory) {
		this.consumerFactory = consumerFactory;
	}

	public PropertyNamingFormat getNamingFormat() {
		return namingFormat;
	}

	public void setNamingFormat(PropertyNamingFormat namingFormat) {
		this.namingFormat = namingFormat;
	}

	public FormatType getFormatType() {
		return formatType;
	}

	public void setFormatType(FormatType formatType) {
		this.formatType = formatType;
	}

	public ODataVersion getConsumerVersion() {
		return consumerVersion;
	}

	public void setConsumerVersion(ODataVersion consumerVersion) {
		this.consumerVersion = consumerVersion;
	}
	
}

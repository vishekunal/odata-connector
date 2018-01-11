/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.jersey.consumer;

import java.util.HashMap;
import java.util.Map;

import org.core4j.Enumerable;
import org.mule.modules.odata.odata4j.extensions.ConsumerBatchRequest;
import org.mule.modules.odata.odata4j.extensions.OBatchRequest;
import org.mule.odata4j.core.OEntityKey;
import org.mule.odata4j.core.OQueryRequest;
import org.mule.odata4j.consumer.AbstractODataConsumer;
import org.mule.odata4j.consumer.ODataClientRequest;
import org.mule.odata4j.consumer.behaviors.OClientBehavior;
import org.mule.odata4j.core.EntitySetInfo;
import org.mule.odata4j.core.OCreateRequest;
import org.mule.odata4j.core.ODataVersion;
import org.mule.odata4j.core.OEntity;
import org.mule.odata4j.core.OEntityGetRequest;
import org.mule.odata4j.core.OEntityId;
import org.mule.odata4j.core.OEntityRequest;
import org.mule.odata4j.core.OFunctionRequest;
import org.mule.odata4j.core.OModifyRequest;
import org.mule.odata4j.core.OObject;
import org.mule.odata4j.edm.EdmDataServices;
import org.mule.odata4j.edm.EdmEntitySet;
import org.mule.odata4j.edm.EdmEntityType;
import org.mule.odata4j.edm.EdmProperty;
import org.mule.odata4j.format.FormatType;
import org.mule.odata4j.internal.EdmDataServicesDecorator;
import org.mule.odata4j.internal.FeedCustomizationMapping;

/**
 * <code>ODataConsumer</code> is the client-side interface to an OData service.
 *
 * <p>Use {@link #create(String)} or one of the other static factory methods to connect to an existing OData service.</p>
 */
public class ODataJerseyConsumer extends AbstractODataConsumer {

  private final Map<String, FeedCustomizationMapping> cachedMappings = new HashMap<String, FeedCustomizationMapping>();
  private final ODataJerseyClient client;
  private ODataVersion version;

  private EdmDataServices cachedMetadata;

  private ODataJerseyConsumer(FormatType type, String serviceRootUri, JerseyClientFactory clientFactory, ODataVersion version, OClientBehavior... behaviors) {
    super(serviceRootUri);

    if (!serviceRootUri.endsWith("/"))
      serviceRootUri = serviceRootUri + "/";
    
    this.version = version;
    this.client = new ODataJerseyClient(type, clientFactory, version, behaviors);
  }

  /**
   * Builder for {@link ODataJerseyConsumer} objects.
   */
  public static class Builder {

    private FormatType formatType;
    private String serviceRootUri;
    private JerseyClientFactory clientFactory;
    private OClientBehavior[] clientBehaviors;
    private ODataVersion version;

    private Builder(String serviceRootUri, ODataVersion version) {
      this.serviceRootUri = serviceRootUri;
      this.version = version;
      this.formatType = FormatType.ATOM;
      this.clientFactory = DefaultJerseyClientFactory.INSTANCE;
    }

    /**
     * Sets a preferred {@link FormatType}. Defaults to {@code FormatType.ATOM}.
     *
     * @param formatType  the format type
     * @return this builder
     */
    public Builder setFormatType(FormatType formatType) {
      this.formatType = formatType;
      return this;
    }

    /**
     * Sets a specific {@link JerseyClientFactory}.
     *
     * @param clientFactory  the jersey client factory
     * @return this builder
     */
    public Builder setClientFactory(JerseyClientFactory clientFactory) {
      this.clientFactory = clientFactory;
      return this;
    }

    /**
     * Sets one or more client behaviors.
     *
     * <p>Client behaviors transform http requests to interact with services that require custom extensions.
     *
     * @param clientBehaviors  the client behaviors
     * @return this builder
     */
    public Builder setClientBehaviors(OClientBehavior... clientBehaviors) {
      this.clientBehaviors = clientBehaviors;
      return this;
    }

    /**
     * Builds the {@link ODataJerseyConsumer} object.
     *
     * @return a new OData consumer
     */
    public ODataJerseyConsumer build() {
      if (this.clientBehaviors != null)
        return new ODataJerseyConsumer(this.formatType, this.serviceRootUri, this.clientFactory, this.version, this.clientBehaviors);
      else
        return new ODataJerseyConsumer(this.formatType, this.serviceRootUri, this.clientFactory, this.version);
    }
  }

  /**
   * Constructs a new builder for an {@link ODataJerseyConsumer} object.
   *
   * @param serviceRootUri  the OData service root uri
   */
  public static Builder newBuilder(String serviceRootUri, ODataVersion version) {
    return new Builder(serviceRootUri, version);
  }

  /**
   * Creates a new consumer for the given OData service uri.
   *
   * <p>Wrapper for {@code ODataConsumer.newBuilder(serviceRootUri).build()}.
   *
   * @param serviceRootUri  the service uri <p>e.g. <code>http://services.odata.org/Northwind/Northwind.svc/</code></p>
   * @param version the OData protocol version to use
   * @return a new OData consumer
   */
  public static ODataJerseyConsumer create(String serviceRootUri, ODataVersion version) {
    return ODataJerseyConsumer.newBuilder(serviceRootUri, version).build();
  }

  /* (non-Javadoc)
   * @see org.odata4j.jersey.consumer.ODataConsumer#getEntitySets()
   */
  @Override
  public Enumerable<EntitySetInfo> getEntitySets() {
    ODataClientRequest request = ODataClientRequest.get(this.getServiceRootUri());
    return Enumerable.create(client.getCollections(request)).cast(EntitySetInfo.class);
  }

  /* (non-Javadoc)
   * @see org.odata4j.jersey.consumer.ODataConsumer#getMetadata()
   */
  @Override
  public EdmDataServices getMetadata() {
    if (cachedMetadata == null)
      cachedMetadata = new CachedEdmDataServices();
    return cachedMetadata;
  }

  /* (non-Javadoc)
    * @see org.odata4j.jersey.consumer.ODataConsumer#getEntities(java.lang.Class, java.lang.String)
    */
  @Override
  public <T> OQueryRequest<T> getEntities(Class<T> entityType, String entitySetHref) {
    FeedCustomizationMapping mapping = getFeedCustomizationMapping(entitySetHref);
    return new ConsumerQueryEntitiesRequest<T>(client, entityType, this.getServiceRootUri(), getMetadata(), entitySetHref, mapping);
  }

  /* (non-Javadoc)
   * @see org.odata4j.jersey.consumer.ODataConsumer#getEntity(java.lang.Class, java.lang.String, OEntityKey)
   */
  @Override
  public <T> OEntityGetRequest<T> getEntity(Class<T> entityType, String entitySetName, OEntityKey key) {
    FeedCustomizationMapping mapping = getFeedCustomizationMapping(entitySetName);
    return new ConsumerGetEntityRequest<T>(client,
        entityType, this.getServiceRootUri(), getMetadata(),
        entitySetName, OEntityKey.create(key), mapping, this.version);
  }

  /* (non-Javadoc)
   * @see org.odata4j.jersey.consumer.ODataConsumer#getLinks(OEntityId, java.lang.String)
   */
  @Override
  public OQueryRequest<OEntityId> getLinks(OEntityId sourceEntity, String targetNavProp) {
    return new ConsumerQueryLinksRequest(client, this.getServiceRootUri(), getMetadata(), sourceEntity, targetNavProp);
  }

  /* (non-Javadoc)
   * @see org.odata4j.jersey.consumer.ODataConsumer#createLink(OEntityId, java.lang.String, OEntityId)
   */
  @Override
  public OEntityRequest<Void> createLink(OEntityId sourceEntity, String targetNavProp, OEntityId targetEntity) {
    return new ConsumerCreateLinkRequest(client, this.getServiceRootUri(), getMetadata(), sourceEntity, targetNavProp, targetEntity);
  }

  /* (non-Javadoc)
   * @see org.odata4j.jersey.consumer.ODataConsumer#deleteLink(OEntityId, java.lang.String, java.lang.Object)
   */
  @Override
  public OEntityRequest<Void> deleteLink(OEntityId sourceEntity, String targetNavProp, Object... targetKeyValues) {
    return new ConsumerDeleteLinkRequest(client, this.getServiceRootUri(), getMetadata(), sourceEntity, targetNavProp, targetKeyValues);
  }

  /* (non-Javadoc)
   * @see org.odata4j.jersey.consumer.ODataConsumer#updateLink(OEntityId, OEntityId, java.lang.String, java.lang.Object)
   */
  @Override
  public OEntityRequest<Void> updateLink(OEntityId sourceEntity, OEntityId newTargetEntity, String targetNavProp, Object... oldTargetKeyValues) {
    return new ConsumerUpdateLinkRequest(client, this.getServiceRootUri(), getMetadata(), sourceEntity, newTargetEntity, targetNavProp, oldTargetKeyValues);
  }

  /* (non-Javadoc)
   * @see org.odata4j.jersey.consumer.ODataConsumer#createEntity(java.lang.String)
   */
  @Override
  public OCreateRequest<OEntity> createEntity(String entitySetName) {
    FeedCustomizationMapping mapping = getFeedCustomizationMapping(entitySetName);
    return new ConsumerCreateEntityRequest<OEntity>(client, this.getServiceRootUri(), getMetadata(),
        entitySetName, mapping);
  }
  
  @Override
  public OBatchRequest createBatch(String url) {
	  return new ConsumerBatchRequest(client, url);
  }

  /* (non-Javadoc)
   * @see org.odata4j.jersey.consumer.ODataConsumer#updateEntity(OEntity)
   */
  @Override
  public OModifyRequest<OEntity> updateEntity(OEntity entity) {
    return new ConsumerEntityModificationRequest<OEntity>(entity, client, this.getServiceRootUri(), getMetadata(),
        entity.getEntitySet().getName(), entity.getEntityKey());
  }

  /* (non-Javadoc)
   * @see org.odata4j.jersey.consumer.ODataConsumer#mergeEntity(OEntity)
   */
  @Override
  public OModifyRequest<OEntity> mergeEntity(OEntity entity) {
    return mergeEntity(entity.getEntitySet().getName(), entity.getEntityKey());
  }

  /* (non-Javadoc)
   * @see org.odata4j.jersey.consumer.ODataConsumer#mergeEntity(java.lang.String, java.lang.Object)
   */
  @Override
  public OModifyRequest<OEntity> mergeEntity(String entitySetName, Object keyValue, boolean keyIsGuid) {
	  OEntityKey key = OEntityKey.create(keyValue);
	  key.setIsGuid(keyIsGuid);
	  return mergeEntity(entitySetName, key);
  }

  /* (non-Javadoc)
   * @see org.odata4j.jersey.consumer.ODataConsumer#mergeEntity(java.lang.String, OEntityKey)
   */
  @Override
  public OModifyRequest<OEntity> mergeEntity(String entitySetName, OEntityKey key) {
    return new ConsumerEntityModificationRequest<OEntity>(null, client, this.getServiceRootUri(),
        getMetadata(), entitySetName, key);
  }

  /* (non-Javadoc)
   * @see org.odata4j.jersey.consumer.ODataConsumer#deleteEntity(OEntityId)
   */
  @Override
  public ConsumerDeleteEntityRequest deleteEntity(OEntityId entity) {
    return deleteEntity(entity.getEntitySetName(), entity.getEntityKey());
  }

  /* (non-Javadoc)
   * @see org.odata4j.jersey.consumer.ODataConsumer#deleteEntity(java.lang.String, java.lang.Object)
   */
  @Override
  public ConsumerDeleteEntityRequest deleteEntity(String entitySetName, Object keyValue, boolean keyIsGuid) {
	  OEntityKey key = OEntityKey.create(keyValue);
	  key.setIsGuid(keyIsGuid);
	  return deleteEntity(entitySetName, key);
  }

  /* (non-Javadoc)
   * @see org.odata4j.jersey.consumer.ODataConsumer#deleteEntity(java.lang.String, OEntityKey)
   */
  @Override
  public ConsumerDeleteEntityRequest deleteEntity(String entitySetName, OEntityKey key) {
    return new ConsumerDeleteEntityRequest(client, this.getServiceRootUri(), getMetadata(), entitySetName, key);
  }

  /* (non-Javadoc)
   * @see org.odata4j.jersey.consumer.ODataConsumer#callFunction(java.lang.String)
   */
  @Override
  public OFunctionRequest<OObject> callFunction(String functionName) {
    return new ConsumerFunctionCallRequest<OObject>(client, this.getServiceRootUri(), getMetadata(), functionName);
  }

  private FeedCustomizationMapping getFeedCustomizationMapping(String entitySetName) {
    if (!cachedMappings.containsKey(entitySetName)) {
      FeedCustomizationMapping rt = new FeedCustomizationMapping();
      EdmDataServices metadata = getMetadata();
      if (metadata != null) {
        EdmEntitySet ees = metadata.findEdmEntitySet(entitySetName);
        if (ees == null) {
          rt = null;
        } else {
          EdmEntityType eet = ees.getType();
          for (EdmProperty ep : eet.getProperties()) {
            if ("SyndicationTitle".equals(ep.getFcTargetPath()) && "false".equals(ep.getFcKeepInContent()))
              rt.titlePropName = ep.getName();
            if ("SyndicationSummary".equals(ep.getFcTargetPath()) && "false".equals(ep.getFcKeepInContent()))
              rt.summaryPropName = ep.getName();
          }
        }
      }
      cachedMappings.put(entitySetName, rt);
    }
    return cachedMappings.get(entitySetName);
  }

  private class CachedEdmDataServices extends EdmDataServicesDecorator {

    private EdmDataServices delegate;

    public CachedEdmDataServices() {}

    @Override
    protected EdmDataServices getDelegate() {
      if (delegate == null)
        refreshDelegate();
      return delegate;
    }

    private void refreshDelegate() {
      ODataClientRequest request = ODataClientRequest.get(ODataJerseyConsumer.this.getServiceRootUri() + "$metadata");
      EdmDataServices metadata = client.getMetadata(request);
      delegate = metadata == null ? EdmDataServices.EMPTY : metadata;
    }

    @Override
    public EdmEntitySet findEdmEntitySet(String entitySetName) {
      EdmEntitySet rt = super.findEdmEntitySet(entitySetName);
      if (rt == null) {
        refreshDelegate();
        rt = super.findEdmEntitySet(entitySetName);
      }
      return rt;
    }
  }

}

/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.odata4j.jersey.consumer;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.core4j.Enumerable;
import org.core4j.xml.XDocument;
import org.core4j.xml.XmlFormat;
import org.mule.modules.odata.exception.NotAuthorizedException;
import org.odata4j.consumer.AbstractODataClient;
import org.odata4j.consumer.ODataClientRequest;
import org.odata4j.consumer.ODataConsumer;
import org.odata4j.consumer.behaviors.OClientBehavior;
import org.odata4j.consumer.behaviors.OClientBehaviors;
import org.odata4j.core.ODataConstants;
import org.odata4j.core.ODataVersion;
import org.odata4j.core.OEntities;
import org.odata4j.core.OEntity;
import org.odata4j.core.OEntityKey;
import org.odata4j.core.OLink;
import org.odata4j.core.OProperty;
import org.odata4j.core.Throwables;
import org.odata4j.edm.EdmDataServices;
import org.odata4j.edm.EdmEntitySet;
import org.odata4j.format.Entry;
import org.odata4j.format.FormatType;
import org.odata4j.format.FormatWriter;
import org.odata4j.format.SingleLink;
import org.odata4j.format.xml.AtomCollectionInfo;
import org.odata4j.format.xml.AtomServiceDocumentFormatParser;
import org.odata4j.format.xml.AtomSingleLinkFormatParser;
import org.odata4j.format.xml.AtomWorkspaceInfo;
import org.odata4j.format.xml.EdmxFormatParser;
import org.odata4j.internal.BOMWorkaroundReader;
import org.odata4j.internal.InternalUtil;
import org.odata4j.stax2.XMLEventReader2;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.PartialRequestBuilder;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.MultiPart;

public class ODataJerseyClient extends AbstractODataClient {

  private final OClientBehavior[] requiredBehaviors = new OClientBehavior[] { OClientBehaviors.methodTunneling("MERGE") }; // jersey hates MERGE, tunnel through POST
  private final OClientBehavior[] behaviors;
  private final ODataVersion version;
  
  private static final Logger logger = Logger.getLogger(ODataJerseyClient.class);

  private final Client client;

  public ODataJerseyClient(FormatType type, JerseyClientFactory clientFactory, ODataVersion version, OClientBehavior... behaviors) {
    super(type);
    this.behaviors = Enumerable.create(requiredBehaviors).concat(Enumerable.create(behaviors)).toArray(OClientBehavior.class);
    this.client = JerseyClientUtil.newClient(clientFactory, behaviors);
    this.version = version;
  }
  
  public Client getClient() {
	return client;
  }

  public EdmDataServices getMetadata(ODataClientRequest request) {
    ClientResponse response = doRequest(FormatType.ATOM, request, 200, 404, 400);
    if (response.getStatus() == 404 || response.getStatus() == 400)
      return null;
    XMLEventReader2 reader = doXmlRequest(response);
    return new EdmxFormatParser().parseMetadata(reader);
  }

  public Iterable<AtomCollectionInfo> getCollections(ODataClientRequest request) {
    ClientResponse response = doRequest(FormatType.ATOM, request, 200);
    XMLEventReader2 reader = doXmlRequest(response);
    return Enumerable.create(AtomServiceDocumentFormatParser.parseWorkspaces(reader))
        .selectMany(AtomWorkspaceInfo.GET_COLLECTIONS);
  }

  public Iterable<SingleLink> getLinks(ODataClientRequest request) {
    ClientResponse response = doRequest(FormatType.ATOM, request, 200);
    XMLEventReader2 reader = doXmlRequest(response);
    return AtomSingleLinkFormatParser.parseLinks(reader);
  }

  public ClientResponse getEntity(ODataClientRequest request) {
    ClientResponse response = doRequest(this.getFormatType(), request, 404, 200, 204);
    if (response.getStatus() == 404)
      return null;
    if (response.getStatus() == 204)
      return null;

    return response;
  }

  public ClientResponse getEntities(ODataClientRequest request) {
    ClientResponse response = doRequest(this.getFormatType(), request, 200);
    return response;
  }

  public ClientResponse callFunction(ODataClientRequest request) {
    ClientResponse response = doRequest(this.getFormatType(), request, 200, 204);
    return response;
  }

  public ClientResponse createEntity(ODataClientRequest request) {
    return doRequest(this.getFormatType(), request, 201);
  }
  
  public ClientResponse batch(ODataClientRequest request) {
	  return this.doRequest(this.getFormatType(), request, 202);
  }

  public boolean updateEntity(ODataClientRequest request) {
    doRequest(this.getFormatType(), request, 200, 204);
    return true;
  }

  public boolean deleteEntity(ODataClientRequest request) {
    doRequest(this.getFormatType(), request, 200, 204, 404);
    return true;
  }

  public void deleteLink(ODataClientRequest request) {
    doRequest(this.getFormatType(), request, 204);
  }

  public void createLink(ODataClientRequest request) {
    doRequest(this.getFormatType(), request, 204);
  }

  public void updateLink(ODataClientRequest request) {
    doRequest(this.getFormatType(), request, 204);
  }

  Entry createRequestEntry(EdmEntitySet entitySet, OEntityKey entityKey, List<OProperty<?>> props, List<OLink> links) {
    final OEntity oentity = entityKey == null
        ? OEntities.createRequest(entitySet, props, links)
        : OEntities.create(entitySet, entityKey, props, links);

    return new Entry() {

      @Override
      public String getUri() {
        return null;
      }

      @Override
      public OEntity getEntity() {
        return oentity;
      }

      @Override
      public String getETag() {
        return null;
      }
    };
  }

  private ClientResponse doRequest(FormatType reqType, ODataClientRequest request, Integer... expectedResponseStatus) {

    if (behaviors != null) {
      for (OClientBehavior behavior : behaviors)
        request = behavior.transform(request);
    }

    WebResource webResource = JerseyClientUtil.resource(client, request.getUrl(), behaviors);
    
    // set query params
    for (Map.Entry<String, String> entry : request.getQueryParams().entrySet()) {
      webResource = webResource.queryParam(entry.getKey(), entry.getValue());
    }

    WebResource.Builder b = webResource.getRequestBuilder();

    // set headers
    b = b.accept(reqType.getAcceptableMediaTypes());

    for (String header : request.getHeaders().keySet()) {
      b.header(header, request.getHeaders().get(header));
    }
    if (!request.getHeaders().containsKey(ODataConstants.Headers.USER_AGENT))
      b.header(ODataConstants.Headers.USER_AGENT, "MuleESB OData connector");

    if (ODataConsumer.dump.requestHeaders())
      dumpHeaders(request, webResource, b);

    // request body
    Object entity = null;
    Object payload = request.getPayload();
    String contentType = request.getHeaders().get(ODataConstants.Headers.CONTENT_TYPE);
    
    if (payload != null) {
    	
    	if (payload instanceof String) {
    		entity = payload;
    		
    		if (StringUtils.isBlank(contentType)) {
    			contentType = reqType == FormatType.JSON ? MediaType.APPLICATION_JSON : MediaType.APPLICATION_ATOM_XML;
    		}
    		
    	} else if (payload instanceof MultiPart) {
    		entity = payload;
    	}
    	
    	else {
    		FormatWriter<Object> fw = JerseyClientUtil.newFormatWriter(request, this.getFormatType(), this.version);
    		entity = JerseyClientUtil.toString(request, fw);
    		
    		if (StringUtils.isBlank(contentType)) {
    			contentType = fw.getContentType();
    		}
    	}

    	if (ODataConsumer.dump.requestBody() && logger.isDebugEnabled()) {
            logger.debug(entity);
    	}
    	
    	// allow the client to override the default format writer content-type
      b.entity(entity, contentType);
      
      if (logger.isDebugEnabled()) {
    	 StringBuilder builder = new StringBuilder();
    	 builder.append("executing OData request:\n")
    	 		.append("url: ").append(webResource.toString())
    	 		.append("\nentity: ");
    	 
    	 
    	 if (entity instanceof MultiPart) {
    		@SuppressWarnings("resource") 
			MultiPart mp = (MultiPart) entity;
    		 for (BodyPart part : mp.getBodyParts()) {
    			builder.append("\n").append(part.getEntity()); 
    		 }
    	 } else {
    		 builder.append(entity);
    	 }
    	 
    	 builder.append("\ncontent-type: ").append(contentType)
    	 		.append("\nquery-params: ").append(request.getQueryParams())
    	 		.append("\nheaders: ").append(request.getHeaders());
    	 		
    	 logger.debug(builder.toString());
      }
      
    }
    // execute request
    ClientResponse response = b.method(request.getMethod(), ClientResponse.class);

    if (ODataConsumer.dump.responseHeaders())
      dumpHeaders(response);
    int status = response.getStatus();
    
    if (status == 401) {
    	throw new NotAuthorizedException();
    }
    
    for (int expStatus : expectedResponseStatus) {
      if (status == expStatus) {
        return response;
      }
    }
    throw new RuntimeException(String.format("Expected status %s, found %s:",
        Enumerable.create(expectedResponseStatus).join(" or "), status) + "\n" + response.getEntity(String.class));
  }

  Reader getFeedReader(ClientResponse response) {
    if (ODataConsumer.dump.responseBody()) {
      String textEntity = response.getEntity(String.class);
      dumpResponseBody(textEntity, response.getType());
      return new BOMWorkaroundReader(new StringReader(textEntity));
    }

    InputStream textEntity = response.getEntityInputStream();
    
    try {
      return new BOMWorkaroundReader(new InputStreamReader(textEntity, "UTF-8"));
    } catch (Exception e) {
      throw Throwables.propagate(e);
    }
  }

  private XMLEventReader2 doXmlRequest(ClientResponse response) {

    if (ODataConsumer.dump.responseBody()) {
      String textEntity = response.getEntity(String.class);
      dumpResponseBody(textEntity, response.getType());
      return InternalUtil.newXMLEventReader(new BOMWorkaroundReader(new StringReader(textEntity)));
    }

    InputStream textEntity = response.getEntityInputStream();
    try {
      return InternalUtil.newXMLEventReader(new BOMWorkaroundReader(new InputStreamReader(textEntity, "UTF-8")));
    } catch (Exception e) {
      throw Throwables.propagate(e);
    }
  }

  private void dumpResponseBody(String textEntity, MediaType type) {
    if (logger.isDebugEnabled()) {
    	String logXml = textEntity;
    	if (type.toString().contains("xml") || logXml != null && logXml.startsWith("<feed")) {
    		try {
    			logXml = XDocument.parse(logXml).toString(XmlFormat.INDENTED);
    		} catch (Exception ignore) {}
    	}
    	logger.debug(logXml);
    }
  }

  private void dumpHeaders(ClientResponse response) {
    if (logger.isDebugEnabled()) {
    	logger.debug("Status: " + response.getStatus());
    	dump(response.getHeaders());
    }
  }

  private static boolean dontTryRequestHeaders;

  @SuppressWarnings("unchecked")
  private MultivaluedMap<String, Object> getRequestHeaders(WebResource.Builder b) {
    if (dontTryRequestHeaders)
      return null;

    //  protected MultivaluedMap<String, Object> metadata;
    try {
      Field f = PartialRequestBuilder.class.getDeclaredField("metadata");
      f.setAccessible(true);
      return (MultivaluedMap<String, Object>) f.get(b);
    } catch (Exception e) {
      dontTryRequestHeaders = true;
      return null;
    }

  }

  private void dumpHeaders(ODataClientRequest request, WebResource webResource, WebResource.Builder b) {
    if (logger.isDebugEnabled()) {
    	logger.debug(request.getMethod() + " " + webResource);
    	dump(getRequestHeaders(b));
    }
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  private void dump(MultivaluedMap headers) {
    if (headers == null)
      return;

    if (logger.isDebugEnabled()) {
    	StringBuilder log = new StringBuilder();
    	for (Object header : headers.keySet()) {
    		log.append(header).append(": ").append(headers.getFirst(header));
    	}
    	
    	logger.debug(log.toString());
    }
  }

}

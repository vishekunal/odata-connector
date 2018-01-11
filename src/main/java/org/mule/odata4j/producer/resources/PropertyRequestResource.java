/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.producer.resources;

import java.io.StringWriter;
import java.net.URI;
import java.util.logging.Logger;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ContextResolver;

import org.mule.odata4j.core.OEntityKey;
import org.mule.odata4j.producer.CountResponse;
import org.mule.odata4j.producer.PropertyResponse;
import org.mule.odata4j.producer.QueryInfo;
import org.mule.odata4j.core.ODataConstants;
import org.mule.odata4j.core.ODataVersion;
import org.mule.odata4j.core.OEntity;
import org.mule.odata4j.edm.EdmDataServices;
import org.mule.odata4j.edm.EdmEntitySet;
import org.mule.odata4j.format.FormatWriter;
import org.mule.odata4j.format.FormatWriterFactory;
import org.mule.odata4j.internal.InternalUtil;
import org.mule.odata4j.producer.BaseResponse;
import org.mule.odata4j.producer.EntitiesResponse;
import org.mule.odata4j.producer.EntityResponse;
import org.mule.odata4j.producer.ODataProducer;
import org.mule.odata4j.producer.exceptions.NotFoundException;
import org.mule.odata4j.producer.exceptions.NotImplementedException;

public class PropertyRequestResource extends BaseResource {

  private static final Logger log =
      Logger.getLogger(PropertyRequestResource.class.getName());

  @PUT
  public Response updateEntity(
      @Context ContextResolver<ODataProducer> producerResolver,
      @PathParam("entitySetName") String entitySetName,
      @PathParam("id") String id,
      @PathParam("navProp") String navProp) {

    log.info("NavProp: updateEntity Not supported yet.");
    throw new NotImplementedException("NavProp: updateEntity not supported yet.");
  }

  @POST
  public Response mergeEntity(
      @Context HttpHeaders httpHeaders,
      @Context UriInfo uriInfo,
      @Context ContextResolver<ODataProducer> producerResolver,
      @PathParam("entitySetName") String entitySetName,
      @PathParam("id") String id,
      @PathParam("navProp") String navProp,
      String payload) throws Exception {

    String method = httpHeaders.getRequestHeaders().getFirst(ODataConstants.Headers.X_HTTP_METHOD);
    if (!"MERGE".equals(method)) {

      ODataProducer producer = producerResolver.getContext(ODataProducer.class);
      ODataVersion version = InternalUtil.getDataServiceVersion(httpHeaders);

      // determine the expected entity set
      EdmDataServices metadata = producer.getMetadata();
      EdmEntitySet ees = metadata
          .getEdmEntitySet(metadata.getEdmEntitySet(entitySetName).getType()
              .findNavigationProperty(navProp).getToRole().getType());

      // parse the request entity
      OEntity entity = getRequestEntity(httpHeaders, uriInfo, payload, metadata, ees.getName(), OEntityKey.parse(id));

      // execute the create
      EntityResponse response = producer.createEntity(entitySetName, OEntityKey.parse(id), navProp, entity);

      if (response == null) {
        throw new NotFoundException();
      }

      // get the FormatWriter for the accepted media types requested by client
      StringWriter sw = new StringWriter();
      FormatWriter<EntityResponse> fw = FormatWriterFactory
          .getFormatWriter(EntityResponse.class, httpHeaders.getAcceptableMediaTypes(), null, null, InternalUtil.getDataServiceVersion(httpHeaders));
      fw.write(uriInfo, sw, response);

      // calculate the uri for the location header
      String relid = InternalUtil.getEntityRelId(response.getEntity());
      String entryId = uriInfo.getBaseUri().toString() + relid;

      // create the response
      String responseEntity = sw.toString();
      return Response
          .ok(responseEntity, fw.getContentType())
          .status(Status.CREATED)
          .location(URI.create(entryId))
          .header(ODataConstants.Headers.DATA_SERVICE_VERSION, version.asString)
          .build();
    }

    throw new NotImplementedException("Not supported yet.");
  }

  @DELETE
  public Response deleteEntity(
      @Context ContextResolver<ODataProducer> producerResolver,
      @PathParam("entitySetName") String entitySetName,
      @PathParam("id") String id,
      @PathParam("navProp") String navProp) {
    throw new NotImplementedException("Not supported yet.");
  }

  @GET
  @Produces({
      ODataConstants.APPLICATION_ATOM_XML_CHARSET_UTF8,
      ODataConstants.TEXT_JAVASCRIPT_CHARSET_UTF8,
      ODataConstants.APPLICATION_JAVASCRIPT_CHARSET_UTF8 })
  public Response getNavProperty(
      @Context HttpHeaders httpHeaders,
      @Context UriInfo uriInfo,
      @Context ContextResolver<ODataProducer> producerResolver,
      @PathParam("entitySetName") String entitySetName,
      @PathParam("id") String id,
      @PathParam("navProp") String navProp,
      @QueryParam("$inlinecount") String inlineCount,
      @QueryParam("$top") String top,
      @QueryParam("$skip") String skip,
      @QueryParam("$filter") String filter,
      @QueryParam("$orderby") String orderBy,
      @QueryParam("$format") String format,
      @QueryParam("$callback") String callback,
      @QueryParam("$skiptoken") String skipToken,
      @QueryParam("$expand") String expand,
      @QueryParam("$select") String select) throws Exception {

    QueryInfo query = new QueryInfo(
        OptionsQueryParser.parseInlineCount(inlineCount),
        OptionsQueryParser.parseTop(top),
        OptionsQueryParser.parseSkip(skip),
        OptionsQueryParser.parseFilter(filter),
        OptionsQueryParser.parseOrderBy(orderBy),
        OptionsQueryParser.parseSkipToken(skipToken),
        OptionsQueryParser.parseCustomOptions(uriInfo),
        OptionsQueryParser.parseSelect(expand),
        OptionsQueryParser.parseSelect(select));

    ODataProducer producer = producerResolver.getContext(ODataProducer.class);

    if (navProp.endsWith("/$count")
        || navProp.endsWith("/$count/")
        || navProp.contains("/$count?")
        || navProp.contains("/$count/?")) {

      navProp = navProp.replace("/$count", "");

      CountResponse response = producer.getNavPropertyCount(
          entitySetName,
          OEntityKey.parse(id),
          navProp,
          query);

      if (response == null) {
        throw new NotFoundException();
      }

      String entity = Long.toString(response.getCount());

      ODataVersion version = InternalUtil.getDataServiceVersion(httpHeaders);

      return Response
          .ok(entity, ODataConstants.TEXT_PLAIN_CHARSET_UTF8)
          .header(ODataConstants.Headers.DATA_SERVICE_VERSION, version.asString)
          .build();
    } else {
      BaseResponse response = producer.getNavProperty(
          entitySetName,
          OEntityKey.parse(id),
          navProp,
          query);

      if (response == null) {
        throw new NotFoundException();
      }

      ODataVersion version = InternalUtil.getDataServiceVersion(httpHeaders);

      StringWriter sw = new StringWriter();
      FormatWriter<?> fwBase;
      if (response instanceof PropertyResponse) {
        FormatWriter<PropertyResponse> fw =
            FormatWriterFactory.getFormatWriter(
                PropertyResponse.class,
                httpHeaders.getAcceptableMediaTypes(),
                format,
                callback,
                version);
        fw.write(uriInfo, sw, (PropertyResponse) response);
        fwBase = fw;
      } else if (response instanceof EntityResponse) {
        FormatWriter<EntityResponse> fw =
            FormatWriterFactory.getFormatWriter(
                EntityResponse.class,
                httpHeaders.getAcceptableMediaTypes(),
                format,
                callback,
                version);
        fw.write(uriInfo, sw, (EntityResponse) response);
        fwBase = fw;
      } else if (response instanceof EntitiesResponse) {
        FormatWriter<EntitiesResponse> fw =
            FormatWriterFactory.getFormatWriter(
                EntitiesResponse.class,
                httpHeaders.getAcceptableMediaTypes(),
                format,
                callback,
                version);
        fw.write(uriInfo, sw, (EntitiesResponse) response);
        fwBase = fw;

      } else {
        throw new NotImplementedException("Unknown BaseResponse type: " + response.getClass().getName());
      }

      String entity = sw.toString();
      return Response
          .ok(entity, fwBase.getContentType())
          .header(ODataConstants.Headers.DATA_SERVICE_VERSION, version.asString)
          .build();
    }
  }
}

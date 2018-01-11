/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.producer.resources;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Logger;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ContextResolver;

import org.core4j.Enumerable;
import org.mule.odata4j.core.ODataConstants;
import org.mule.odata4j.core.OEntityId;
import org.mule.odata4j.core.OEntityKey;
import org.mule.odata4j.edm.EdmMultiplicity;
import org.mule.odata4j.format.FormatWriter;
import org.mule.odata4j.format.SingleLink;
import org.mule.odata4j.producer.EntityIdResponse;
import org.mule.odata4j.producer.ODataProducer;
import org.mule.odata4j.core.ODataVersion;
import org.mule.odata4j.core.OEntityIds;
import org.mule.odata4j.format.FormatParser;
import org.mule.odata4j.format.FormatParserFactory;
import org.mule.odata4j.format.FormatWriterFactory;
import org.mule.odata4j.format.SingleLinks;
import org.mule.odata4j.internal.InternalUtil;
import org.mule.odata4j.producer.exceptions.NotFoundException;

public class LinksRequestResource extends BaseResource {

  private static final Logger log = Logger.getLogger(LinksRequestResource.class.getName());

  private final OEntityId sourceEntity;
  private final String targetNavProp;
  private final OEntityKey targetEntityKey;

  public LinksRequestResource(OEntityId sourceEntity, String targetNavProp, OEntityKey targetEntityKey) {
    this.sourceEntity = sourceEntity;
    this.targetNavProp = targetNavProp;
    this.targetEntityKey = targetEntityKey;
  }

  @POST
  public Response createLink(@Context HttpHeaders httpHeaders, @Context UriInfo uriInfo, @Context ContextResolver<ODataProducer> producerResolver, String payload) {
    log.info(String.format(
        "createLink(%s,%s,%s,%s)",
        sourceEntity.getEntitySetName(),
        sourceEntity.getEntityKey(),
        targetNavProp,
        targetEntityKey));

    ODataProducer producer = producerResolver.getContext(ODataProducer.class);

    OEntityId newTargetEntity = parseRequestUri(httpHeaders, uriInfo, payload);
    producer.createLink(sourceEntity, targetNavProp, newTargetEntity);
    return noContent(InternalUtil.getDataServiceVersion(httpHeaders));
  }

  @PUT
  public Response updateLink(@Context HttpHeaders httpHeaders, @Context UriInfo uriInfo, @Context ContextResolver<ODataProducer> producerResolver, String payload) {
    log.info(String.format(
        "updateLink(%s,%s,%s,%s)",
        sourceEntity.getEntitySetName(),
        sourceEntity.getEntityKey(),
        targetNavProp,
        targetEntityKey));

    ODataProducer producer = producerResolver.getContext(ODataProducer.class);
    
    OEntityId newTargetEntity = parseRequestUri(httpHeaders, uriInfo, payload);
    producer.updateLink(sourceEntity, targetNavProp, targetEntityKey, newTargetEntity);
    return noContent(InternalUtil.getDataServiceVersion(httpHeaders));
  }

  private OEntityId parseRequestUri(HttpHeaders httpHeaders, UriInfo uriInfo, String payload) {
    FormatParser<SingleLink> parser = FormatParserFactory.getParser(SingleLink.class, httpHeaders.getMediaType(), null);
    SingleLink link = parser.parse(new StringReader(payload));
    return OEntityIds.parse(uriInfo.getBaseUri().toString(), link.getUri());
  }

  private Response noContent(ODataVersion version) {
    return Response.noContent().header(ODataConstants.Headers.DATA_SERVICE_VERSION, version.asString).build();
  }

  @DELETE
  public Response deleteLink(@Context HttpHeaders httpHeaders, @Context UriInfo uriInfo, @Context ContextResolver<ODataProducer> producerResolver) {
    log.info(String.format(
        "deleteLink(%s,%s,%s,%s)",
        sourceEntity.getEntitySetName(),
        sourceEntity.getEntityKey(),
        targetNavProp,
        targetEntityKey));

    ODataProducer producer = producerResolver.getContext(ODataProducer.class);

    producer.deleteLink(sourceEntity, targetNavProp, targetEntityKey);
    return noContent(InternalUtil.getDataServiceVersion(httpHeaders));
  }

  @GET
  public Response getLinks(@Context HttpHeaders httpHeaders, @Context UriInfo uriInfo, @Context ContextResolver<ODataProducer> producerResolver,
      @QueryParam("$format") String format,
      @QueryParam("$callback") String callback) {

    log.info(String.format(
        "getLinks(%s,%s,%s,%s)",
        sourceEntity.getEntitySetName(),
        sourceEntity.getEntityKey(),
        targetNavProp,
        targetEntityKey));

    ODataProducer producer = producerResolver.getContext(ODataProducer.class);

    EntityIdResponse response = producer.getLinks(sourceEntity, targetNavProp);

    StringWriter sw = new StringWriter();
    String serviceRootUri = uriInfo.getBaseUri().toString();
    String contentType;
    ODataVersion version = InternalUtil.getDataServiceVersion(httpHeaders);
    
    if (response.getMultiplicity() == EdmMultiplicity.MANY) {
      SingleLinks links = SingleLinks.create(serviceRootUri, response.getEntities());
      FormatWriter<SingleLinks> fw = FormatWriterFactory.getFormatWriter(SingleLinks.class, httpHeaders.getAcceptableMediaTypes(), format, callback, version);
      fw.write(uriInfo, sw, links);
      contentType = fw.getContentType();
    } else {
      OEntityId entityId = Enumerable.create(response.getEntities()).firstOrNull();
      if (entityId == null)
        throw new NotFoundException();

      SingleLink link = SingleLinks.create(serviceRootUri, entityId);
      FormatWriter<SingleLink> fw = FormatWriterFactory.getFormatWriter(SingleLink.class, httpHeaders.getAcceptableMediaTypes(), format, callback, version);
      fw.write(uriInfo, sw, link);
      contentType = fw.getContentType();
    }

    String entity = sw.toString();

    return Response.ok(entity, contentType).header(ODataConstants.Headers.DATA_SERVICE_VERSION, version.asString).build();
  }

}

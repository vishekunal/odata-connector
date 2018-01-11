/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.producer.resources;

import java.io.StringWriter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ContextResolver;

import org.mule.odata4j.format.FormatWriter;
import org.mule.odata4j.producer.ODataProducer;
import org.mule.odata4j.core.ODataConstants;
import org.mule.odata4j.core.ODataVersion;
import org.mule.odata4j.edm.EdmDataServices;
import org.mule.odata4j.format.FormatWriterFactory;
import org.mule.odata4j.internal.InternalUtil;

@Path("")
public class ServiceDocumentResource {

  @GET
  @Produces({ ODataConstants.APPLICATION_XML_CHARSET_UTF8, ODataConstants.TEXT_JAVASCRIPT_CHARSET_UTF8, ODataConstants.APPLICATION_JAVASCRIPT_CHARSET_UTF8 })
  public Response getServiceDocument(
      @Context HttpHeaders httpHeaders,
      @Context UriInfo uriInfo,
      @Context ContextResolver<ODataProducer> producerResolver,
      @QueryParam("$format") String format,
      @QueryParam("$callback") String callback) {

    ODataProducer producer = producerResolver.getContext(ODataProducer.class);

    EdmDataServices metadata = producer.getMetadata();
    ODataVersion version = InternalUtil.getDataServiceVersion(httpHeaders);
    
    StringWriter w = new StringWriter();
    FormatWriter<EdmDataServices> fw = FormatWriterFactory.getFormatWriter(EdmDataServices.class, httpHeaders.getAcceptableMediaTypes(), format, callback, version);
    fw.write(uriInfo, w, metadata);

    return Response.ok(w.toString(), fw.getContentType())
        .header(ODataConstants.Headers.DATA_SERVICE_VERSION, version.asString)
        .build();
  }

}

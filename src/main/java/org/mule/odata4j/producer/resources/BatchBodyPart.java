/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.producer.resources;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.mule.odata4j.producer.resources.ODataBatchProvider.HTTP_METHOD;

public class BatchBodyPart {

  private MultivaluedMap<String, String> headers = new HeaderMap();
  private HTTP_METHOD httpMethod;
  private HttpHeaders httpHeaders;
  private UriInfo uriInfo;
  private String entity;
  private String uri;
  private String uriLast;

  public BatchBodyPart(){}
  
  public BatchBodyPart(HttpHeaders httpHeaders, UriInfo uriInfo) {
    this();
	this.httpHeaders = httpHeaders;
    this.uriInfo = uriInfo;
  }

  public HttpHeaders getHttpHeaders() {
    return httpHeaders;
  }

  public UriInfo getUriInfo() {
    return uriInfo;
  }

  public String getEntity() {
    return this.entity;
  }

  public void setEntity(String entity) {
    this.entity = entity;
  }

  public MultivaluedMap<String, String> getHeaders() {
    return this.headers;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;

    int i = this.uri.lastIndexOf('/');
    if (this.uri.length() > i) {
      this.uriLast = this.uri.substring(this.uri.lastIndexOf('/') + 1);
    } else {
      this.uriLast = this.uri;
    }
  }

  public HTTP_METHOD getHttpMethod() {
    return httpMethod;
  }

  public void setHttpMethod(HTTP_METHOD httpMethod) {
    this.httpMethod = httpMethod;
  }

  public String getEntitySetName() {
    int i = this.uriLast.indexOf('(');
    return i != -1
        ? this.uriLast.substring(0, i)
        : this.uriLast;
  }

  public String getEntityKey() {
    int i = this.uriLast.indexOf('(');
    return i > -1 && i < this.uriLast.length()
        ? this.uriLast.substring(i)
        : null;
  }

}

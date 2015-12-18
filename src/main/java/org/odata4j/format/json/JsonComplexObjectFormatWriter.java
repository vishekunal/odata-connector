/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.odata4j.format.json;

import javax.ws.rs.core.UriInfo;

import org.odata4j.core.ODataVersion;
import org.odata4j.producer.ComplexObjectResponse;

/**
 * Writer for OComplexObjects in JSON
 */
public class JsonComplexObjectFormatWriter extends JsonFormatWriter<ComplexObjectResponse> {

  public JsonComplexObjectFormatWriter(String jsonpCallback, ODataVersion version) {
    super(jsonpCallback, version);
  }

  @Override
  protected void writeContent(UriInfo uriInfo, JsonWriter jw, ComplexObjectResponse target) {
    super.writeComplexObject(jw, target.getObject().getType().getFullyQualifiedTypeName(), target.getObject().getProperties());
  }

}

/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.format.json;

import javax.ws.rs.core.UriInfo;

import org.mule.odata4j.core.ODataVersion;
import org.mule.odata4j.producer.EntityResponse;

public class JsonEntryFormatWriter extends JsonFormatWriter<EntityResponse> {

  public JsonEntryFormatWriter(String jsonpCallback, ODataVersion version) {
    super(jsonpCallback, version);
  }

  @Override
  protected void writeContent(UriInfo uriInfo, JsonWriter jw, EntityResponse target) {
    writeOEntity(uriInfo, jw, target.getEntity(), target.getEntity().getEntitySet(), true);
  }

}

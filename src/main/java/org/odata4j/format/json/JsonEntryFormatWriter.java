/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.format.json;

import javax.ws.rs.core.UriInfo;

import org.odata4j.producer.EntityResponse;

public class JsonEntryFormatWriter extends JsonFormatWriter<EntityResponse> {

  public JsonEntryFormatWriter(String jsonpCallback) {
    super(jsonpCallback);
  }

  @Override
  protected void writeContent(UriInfo uriInfo, JsonWriter jw, EntityResponse target) {
    writeOEntity(uriInfo, jw, target.getEntity(), target.getEntity().getEntitySet(), true);
  }

}

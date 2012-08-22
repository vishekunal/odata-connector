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

import org.odata4j.core.OCollection;
import org.odata4j.core.ODataVersion;
import org.odata4j.edm.EdmType;
import org.odata4j.producer.CollectionResponse;

/**
 * Writer for OCollections in JSON
 */
public class JsonCollectionFormatWriter extends JsonFormatWriter<CollectionResponse<?>> {

  public JsonCollectionFormatWriter(String jsonpCallback, ODataVersion version) {
    super(jsonpCallback, version);
  }

  @Override
  protected void writeContent(UriInfo uriInfo, JsonWriter jw, CollectionResponse<?> target) {
    OCollection<?> c = target.getCollection();
    EdmType ctype = c.getType();
    jw.startArray();
    {
      boolean isFirst = true;
      for (Object o : c) {
        if (!isFirst) {
          jw.writeSeparator();
        }
        else {
          isFirst = false;
        }
        super.writeValue(jw, ctype, o);
      }
    }
    jw.endArray();
  }

}

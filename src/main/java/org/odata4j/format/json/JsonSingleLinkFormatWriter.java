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

import org.odata4j.format.SingleLink;

public class JsonSingleLinkFormatWriter extends JsonFormatWriter<SingleLink> {

  public JsonSingleLinkFormatWriter(String jsonpCallback) {
    super(jsonpCallback);
  }

  @Override
  protected void writeContent(UriInfo uriInfo, JsonWriter jw, SingleLink link) {
    writeUri(jw, link);
  }

  static void writeUri(JsonWriter jw, SingleLink link) {
    jw.startObject();
    {
      jw.writeName("uri");
      jw.writeString(link.getUri());
    }
    jw.endObject();
  }

}

/*
{
"d" : {
"uri": "http://services.odata.org/northwind/Northwind.svc/Categories(1)"
}
}
*/

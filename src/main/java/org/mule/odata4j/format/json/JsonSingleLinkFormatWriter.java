/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.format.json;

import javax.ws.rs.core.UriInfo;

import org.mule.odata4j.core.ODataVersion;
import org.mule.odata4j.format.SingleLink;

public class JsonSingleLinkFormatWriter extends JsonFormatWriter<SingleLink> {

  public JsonSingleLinkFormatWriter(String jsonpCallback, ODataVersion version) {
    super(jsonpCallback, version);
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

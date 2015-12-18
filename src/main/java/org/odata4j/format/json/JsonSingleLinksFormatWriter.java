/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.odata4j.format.json;

import javax.ws.rs.core.UriInfo;

import org.odata4j.core.ODataVersion;
import org.odata4j.format.SingleLink;
import org.odata4j.format.SingleLinks;

public class JsonSingleLinksFormatWriter extends JsonFormatWriter<SingleLinks> {

  public JsonSingleLinksFormatWriter(String jsonpCallback, ODataVersion version) {
    super(jsonpCallback, version);
  }

  @Override
  protected void writeContent(UriInfo uriInfo, JsonWriter jw, SingleLinks links) {
    boolean isV2OrGreater = this.isV2OrGreater();
    
    if (isV2OrGreater) {
    	jw.startObject();
    	jw.writeName(JsonFormatParser.RESULTS_PROPERTY);    	
    }
    	
	jw.startArray();
	for (SingleLink link : links) {
		JsonSingleLinkFormatWriter.writeUri(jw, link);
	}
	
	jw.endArray();
	
	if (isV2OrGreater) {
		jw.endObject();
	}
  }

}

/*
{
"d" : {
"results": [
{
"uri": "http://services.odata.org/northwind/Northwind.svc/Order_Details(OrderID=10285,ProductID=1)"
}, {
"uri": "http://services.odata.org/northwind/Northwind.svc/Order_Details(OrderID=10294,ProductID=1)"
}
]
}
}
*/

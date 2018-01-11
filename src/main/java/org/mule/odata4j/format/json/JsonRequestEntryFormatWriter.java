/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.format.json;

import java.io.Writer;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.mule.odata4j.core.ODataVersion;
import org.mule.odata4j.format.Entry;

public class JsonRequestEntryFormatWriter extends JsonFormatWriter<Entry> {

  public JsonRequestEntryFormatWriter(String jsonpCallback, ODataVersion version) {
    super(jsonpCallback, version);
  }

  @Override
  public String getContentType() {
    return MediaType.APPLICATION_JSON;
  }

  @Override
  public void write(UriInfo uriInfo, Writer w, Entry target) {

    JsonWriter jw = new JsonWriter(w);
    if (getJsonpCallback() != null) {
      jw.startCallback(getJsonpCallback());
    }

    writeContent(uriInfo, jw, target);
  }

  @Override
  protected void writeContent(UriInfo uriInfo, JsonWriter jw, Entry target) {
    writeOEntity(uriInfo, jw, target.getEntity(),
        target.getEntity().getEntitySet(), false);
  }

}

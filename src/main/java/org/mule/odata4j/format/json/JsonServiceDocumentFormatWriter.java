/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.format.json;

import javax.ws.rs.core.UriInfo;

import org.mule.odata4j.core.ODataVersion;
import org.mule.odata4j.edm.EdmDataServices;
import org.mule.odata4j.edm.EdmEntitySet;

public class JsonServiceDocumentFormatWriter extends JsonFormatWriter<EdmDataServices> {

  public JsonServiceDocumentFormatWriter(String jsonpCallback, ODataVersion version) {
    super(jsonpCallback, version);
  }

  @Override
  public void writeContent(UriInfo uriInfo, JsonWriter jw, EdmDataServices target) {

    jw.startObject();
    {
      jw.writeName("EntitySets");
      jw.startArray();
      {
        boolean isFirst = true;
        for (EdmEntitySet ees : target.getEntitySets()) {
          if (isFirst)
            isFirst = false;
          else
            jw.writeSeparator();

          jw.writeString(ees.getName());
        }

      }
      jw.endArray();
    }
    jw.endObject();

  }

}

/*
// jsonp
callback({
"d" : {
"EntitySets": [
"TitleAudioFormats", "TitleAwards", "Titles", "TitleScreenFormats", "Genres", "Languages", "People"
]
}
});

// json
{
"d" : {
"EntitySets": [
"TitleAudioFormats", "TitleAwards", "Titles", "TitleScreenFormats", "Genres", "Languages", "People"
]
}
}
*/
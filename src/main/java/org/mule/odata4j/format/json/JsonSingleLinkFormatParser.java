/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.format.json;

import java.io.Reader;

import org.mule.odata4j.format.Settings;
import org.mule.odata4j.format.FormatParser;
import org.mule.odata4j.format.SingleLink;
import org.mule.odata4j.format.SingleLinks;
import org.mule.odata4j.format.json.JsonStreamReaderFactory.JsonStreamReader;

public class JsonSingleLinkFormatParser extends JsonFormatParser implements FormatParser<SingleLink> {

  public JsonSingleLinkFormatParser(Settings settings) {
    super(settings);
  }

  @Override
  public SingleLink parse(Reader reader) {
    // {"uri": "http://host/service.svc/Orders(1)"}
    JsonStreamReader jsr = JsonStreamReaderFactory.createJsonStreamReader(reader);
    try {
      ensureStartObject(jsr.nextEvent());
      ensureStartProperty(jsr.nextEvent(), "uri");
      String uri = jsr.nextEvent().asEndProperty().getValue();
      return SingleLinks.create(uri);
    } finally {
      jsr.close();
    }
  }

}

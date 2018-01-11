/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.format.json;

import java.io.Reader;

import org.mule.odata4j.format.Settings;
import org.mule.odata4j.core.ODataVersion;
import org.mule.odata4j.format.Entry;
import org.mule.odata4j.format.FormatParser;
import org.mule.odata4j.format.json.JsonStreamReaderFactory.JsonStreamReader;

public class JsonEntryFormatParser extends JsonFormatParser implements FormatParser<Entry> {

  public JsonEntryFormatParser(Settings settings) {
    super(settings);
  }

  @Override
  public Entry parse(Reader reader) {
    JsonStreamReader jsr = JsonStreamReaderFactory.createJsonStreamReader(reader);
    try {
      ensureNext(jsr);

      // skip the StartObject event
      ensureStartObject(jsr.nextEvent());

      if (isResponse) {
        // "d" property
        ensureNext(jsr);
        ensureStartProperty(jsr.nextEvent(), DATA_PROPERTY);

        // skip the StartObject event
        ensureStartObject(jsr.nextEvent());
      }

      // "result" for DataServiceVersion > 1.0
      if (version.compareTo(ODataVersion.V1) > 0) {
        ensureNext(jsr);
        ensureStartObject(jsr.nextEvent());
        ensureNext(jsr);
        ensureStartProperty(jsr.nextEvent(), RESULTS_PROPERTY);

        // skip the StartObject event
        ensureStartObject(jsr.nextEvent());
      }

      // parse the entry
      return parseEntry(metadata.getEdmEntitySet(entitySetName), jsr);

      // no interest in the closing events
    } finally {
      jsr.close();
    }
  }

}

/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.format.xml;

import java.io.Reader;

import org.mule.odata4j.core.OEntityKey;
import org.mule.odata4j.edm.EdmDataServices;
import org.mule.odata4j.format.Entry;
import org.mule.odata4j.format.FormatParser;
import org.mule.odata4j.internal.FeedCustomizationMapping;
import org.mule.odata4j.internal.InternalUtil;

public class AtomEntryFormatParser implements FormatParser<Entry> {

  protected EdmDataServices metadata;
  protected String entitySetName;
  protected OEntityKey entityKey;
  protected FeedCustomizationMapping fcMapping;

  public AtomEntryFormatParser(EdmDataServices metadata, String entitySetName, OEntityKey entityKey, FeedCustomizationMapping fcMapping) {
    this.metadata = metadata;
    this.entitySetName = entitySetName;
    this.entityKey = entityKey;
    this.fcMapping = fcMapping;
  }

  @Override
  public Entry parse(Reader reader) {
    return new AtomFeedFormatParser(metadata, entitySetName, entityKey, fcMapping)
        .parseFeed(InternalUtil.newXMLEventReader(reader)).entries.iterator().next();
  }

}

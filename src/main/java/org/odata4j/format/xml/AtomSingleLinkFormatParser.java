/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.format.xml;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.odata4j.format.FormatParser;
import org.odata4j.format.SingleLink;
import org.odata4j.format.SingleLinks;
import org.odata4j.internal.InternalUtil;
import org.odata4j.stax2.QName2;
import org.odata4j.stax2.XMLEvent2;
import org.odata4j.stax2.XMLEventReader2;

public class AtomSingleLinkFormatParser extends XmlFormatParser implements FormatParser<SingleLink> {

  private static final QName2 URI = new QName2(NS_DATASERVICES, "uri");

  public static Iterable<SingleLink> parseLinks(XMLEventReader2 reader) {
    List<SingleLink> rt = new ArrayList<SingleLink>();
    while (reader.hasNext()) {
      XMLEvent2 event = reader.nextEvent();
      if (isStartElement(event, URI)) {
        rt.add(SingleLinks.create(reader.getElementText()));
      }
    }
    return rt;
  }

  @Override
  public SingleLink parse(Reader reader) {
    return parseLinks(InternalUtil.newXMLEventReader(reader)).iterator().next();
  }

}

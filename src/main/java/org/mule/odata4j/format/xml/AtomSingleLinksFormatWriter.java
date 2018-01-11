/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.format.xml;

import java.io.Writer;

import javax.ws.rs.core.UriInfo;

import org.mule.odata4j.format.FormatWriter;
import org.mule.odata4j.stax2.QName2;
import org.mule.odata4j.core.ODataConstants;
import org.mule.odata4j.format.SingleLink;
import org.mule.odata4j.format.SingleLinks;
import org.mule.odata4j.stax2.XMLFactoryProvider2;
import org.mule.odata4j.stax2.XMLWriter2;

public class AtomSingleLinksFormatWriter implements FormatWriter<SingleLinks> {

  @Override
  public void write(UriInfo uriInfo, Writer w, SingleLinks target) {
    XMLWriter2 writer = XMLFactoryProvider2.getInstance().newXMLWriterFactory2().createXMLWriter(w);
    writer.startDocument();
    writer.startElement(new QName2("link"), AtomSingleLinkFormatWriter.d);
    for (SingleLink link : target)
      AtomSingleLinkFormatWriter.writeUri(writer, link, null);
    writer.endElement("link");
    writer.endDocument();
  }

  @Override
  public String getContentType() {
    return ODataConstants.APPLICATION_XML_CHARSET_UTF8;
  }

}
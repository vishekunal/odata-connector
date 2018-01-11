/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.format.xml;

import java.io.Writer;

import javax.ws.rs.core.UriInfo;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.mule.odata4j.core.ODataConstants;
import org.mule.odata4j.core.OEntity;
import org.mule.odata4j.edm.EdmEntitySet;
import org.mule.odata4j.format.FormatWriter;
import org.mule.odata4j.producer.EntityResponse;
import org.mule.odata4j.stax2.QName2;
import org.mule.odata4j.stax2.XMLFactoryProvider2;
import org.mule.odata4j.stax2.XMLWriter2;
import org.mule.odata4j.format.Entry;
import org.mule.odata4j.internal.InternalUtil;

public class AtomEntryFormatWriter extends XmlFormatWriter implements FormatWriter<EntityResponse> {

  protected String baseUri;

  public void writeRequestEntry(Writer w, Entry entry) {

    DateTime utc = new DateTime().withZone(DateTimeZone.UTC);
    String updated = InternalUtil.toString(utc);

    XMLWriter2 writer = XMLFactoryProvider2.getInstance().newXMLWriterFactory2().createXMLWriter(w);
    writer.startDocument();

    writer.startElement(new QName2("entry"), atom);
    writer.writeNamespace("d", d);
    writer.writeNamespace("m", m);

    OEntity entity = entry.getEntity();
    writeEntry(writer, null, entity.getProperties(), entity.getLinks(),
        null, updated, entity.getEntitySet(), false);
    writer.endDocument();

  }

  @Override
  public String getContentType() {
    return ODataConstants.APPLICATION_ATOM_XML_CHARSET_UTF8;
  }

  @Override
  public void write(UriInfo uriInfo, Writer w, EntityResponse target) {
    String baseUri = uriInfo.getBaseUri().toString();
    EdmEntitySet ees = target.getEntity().getEntitySet();

    DateTime utc = new DateTime().withZone(DateTimeZone.UTC);
    String updated = InternalUtil.toString(utc);

    XMLWriter2 writer = XMLFactoryProvider2.getInstance().newXMLWriterFactory2().createXMLWriter(w);
    writer.startDocument();

    writer.startElement(new QName2("entry"), atom);
    writer.writeNamespace("m", m);
    writer.writeNamespace("d", d);
    writer.writeAttribute("xml:base", baseUri);

    writeEntry(writer, target.getEntity(), target.getEntity().getProperties(), target.getEntity().getLinks(), baseUri, updated, ees, true);
    writer.endDocument();
  }

}

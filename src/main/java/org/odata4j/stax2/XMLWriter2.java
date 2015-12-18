/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.odata4j.stax2;

public interface XMLWriter2 {

  void startElement(String name);

  void startElement(QName2 qname);

  void startElement(QName2 qname, String xmlns);

  void writeAttribute(String localName, String value);

  void writeAttribute(QName2 qname, String value);

  void writeText(String content);

  void writeNamespace(String prefix, String namespaceUri);

  void startDocument();

  void endElement(String localName);

  void endDocument();

}
/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.stax2;

public interface XMLEvent2 {

  boolean isStartElement();

  StartElement2 asStartElement();

  boolean isEndElement();

  EndElement2 asEndElement();

}

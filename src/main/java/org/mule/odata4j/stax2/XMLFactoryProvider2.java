/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.stax2;

import org.mule.odata4j.core.Throwables;
import org.mule.odata4j.internal.PlatformUtil;

public abstract class XMLFactoryProvider2 {

  private static XMLFactoryProvider2 STAX;

  static {
    try {
      String clazz = PlatformUtil.runningOnAndroid() ? "XmlPullXMLFactoryProvider2" : "StaxXMLFactoryProvider2";
      STAX = (XMLFactoryProvider2) Class.forName(clazz).newInstance();
    } catch (Exception e) {
      throw Throwables.propagate(e);
    }
  }

  public static void setInstance(XMLFactoryProvider2 instance) {
    STAX = instance;
  }

  public static XMLFactoryProvider2 getInstance() {
    return STAX;
  }

  public abstract XMLOutputFactory2 newXMLOutputFactory2();

  public abstract XMLInputFactory2 newXMLInputFactory2();

  public abstract XMLWriterFactory2 newXMLWriterFactory2();

}

/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.odata4j.internal;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PlatformUtil {

  private static boolean RUNNING_ON_ANDROID;

  static {
    try {
      Class.forName("android.app.Activity");
      RUNNING_ON_ANDROID = true;
    } catch (Exception e) {
      RUNNING_ON_ANDROID = false;
    }

    if (runningOnAndroid()) {
      androidInit();
    }
  }

  private static void androidInit() {

  }

  public static boolean runningOnAndroid() {
    return RUNNING_ON_ANDROID;
  }

  public static String getTextContent(Element element) {
    // FOR ANDROID
    StringBuilder buffer = new StringBuilder();
    NodeList childList = element.getChildNodes();
    for (int i = 0; i < childList.getLength(); i++) {
      Node child = childList.item(i);
      if (child.getNodeType() == Node.TEXT_NODE)
        buffer.append(child.getNodeValue());
    }

    return buffer.toString();
  }

}

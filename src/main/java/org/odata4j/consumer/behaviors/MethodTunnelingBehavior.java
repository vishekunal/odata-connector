/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.consumer.behaviors;

import org.odata4j.consumer.ODataClientRequest;
import org.odata4j.core.ODataConstants;

public class MethodTunnelingBehavior implements OClientBehavior {

  private final String[] methodsToTunnel;

  public MethodTunnelingBehavior(String... methodsToTunnel) {
    this.methodsToTunnel = methodsToTunnel;
  }

  @Override
  public ODataClientRequest transform(ODataClientRequest request) {
    String method = request.getMethod();
    for (String methodToTunnel : methodsToTunnel) {
      if (method.equals(methodToTunnel)) {
        return request.header(ODataConstants.Headers.X_HTTP_METHOD, method).method("POST");
      }
    }
    return request;
  }

}

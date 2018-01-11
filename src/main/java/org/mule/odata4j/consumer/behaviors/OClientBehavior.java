/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.consumer.behaviors;

import org.mule.odata4j.consumer.ODataClientRequest;

/**
 * Extension-point for modifying client http requests.
 * <p>The {@link OClientBehaviors} static factory class can be used to create built-in <code>OClientBehavior</code> instances.</p>
 */
public interface OClientBehavior {

  /**
   * Transforms the current http request.
   *
   * @param request  the current http request
   * @return the modified http request
   */
  ODataClientRequest transform(ODataClientRequest request);

}

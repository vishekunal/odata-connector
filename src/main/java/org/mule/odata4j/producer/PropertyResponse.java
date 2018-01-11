/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.producer;

import org.mule.odata4j.core.OProperty;

/**
 * An <code>PropertyResponse</code> is a response to a client request expecting a single property value.
 *
 * <p>The {@link Responses} static factory class can be used to create <code>PropertyResponse</code> instances.</p>
 */
public interface PropertyResponse extends BaseResponse {

  /**
   * Gets the property value.
   *
   * @return the property value
   */
  OProperty<?> getProperty();

}

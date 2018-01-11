/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.core;

/**
 * An object with a title.
 *
 * <p>No behavior or semantics are implied, this is simply a convenient reusable interface.</p>
 */
public interface Titled {

  /**
   * Gets the title.
   *
   * @return the title
   */
  String getTitle();

}

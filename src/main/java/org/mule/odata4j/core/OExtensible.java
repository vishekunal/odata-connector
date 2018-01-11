/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.core;

/**
 * Basic extension mechanism.
 *
 * @param <T>  the type being extended
 *
 * @see OExtension
 */
public interface OExtensible<T> {

  /**
   * Find extension instance given an interface, if one exists.
   *
   * @param clazz  the extension interface
   * @param <TExtension>  type of extension
   * @return the extension instance or null
   *
   * @see OAtomStreamEntity
   * @see OAtomEntity
   */
  <TExtension extends OExtension<T>> TExtension findExtension(Class<TExtension> clazz);

}

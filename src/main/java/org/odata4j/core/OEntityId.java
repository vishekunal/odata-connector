/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.odata4j.core;

/**
 * The identity of a single OData entity, consisting of an entity-set name and a unique entity-key within that set.
 * <p>The {@link OEntityIds} static factory class can be used to create <code>OEntityId</code> instances.</p>
 *
 * @see OEntityIds
 * @see OEntityKey
 */
public interface OEntityId {

  /**
   * Gets the entity-set name for this instance.
   *
   * @return the entity-set name
   */
  String getEntitySetName();

  /**
   * Gets the entity-key for this instance.
   *
   * @return the entity-key
   */
  OEntityKey getEntityKey();

}

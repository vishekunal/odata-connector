/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.core;

import org.mule.odata4j.edm.EdmType;

/**
 * A homogeneous collection of OData objects of a given {@link EdmType}.
 * <p>The {@link OCollections} static factory class can be used to create <code>OCollection</code> instances.</p>
 *
 * @param <T>  type of instances in the collection.
 * @see OCollections
 */
public interface OCollection<T extends OObject> extends OObject, Iterable<T> {

  /** Builds an {@link OCollection} instance. */
  public interface Builder<T extends OObject> {
    Builder<T> add(T value);

    OCollection<T> build();
  }

  /** Gets the size of this collection */
  int size();

}

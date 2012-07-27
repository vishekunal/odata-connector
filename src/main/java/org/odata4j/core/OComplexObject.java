/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.core;

import org.odata4j.edm.EdmComplexType;

/**
 * An instance of an {@link EdmComplexType}.
 * <p>The {@link OComplexObjects} static factory class can be used to create <code>OComplexObject</code> instances.</p>
 *
 * @see OComplexObjects
 */
public interface OComplexObject extends OStructuralObject {

  /** Builds an {@link OComplexObject} instance. */
  public interface Builder {
    Builder add(OProperty<?> prop);

    EdmComplexType getType();

    OComplexObject build();
  }

}

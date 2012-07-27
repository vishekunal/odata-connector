/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.core;

/**
 * A strongly-typed named value.
 * <p>No behavior or semantics are implied, this is simply a convenient reusable interface.</p>
 * <p>The {@link NamedValues} static factory class can be used to create <code>NamedValue</code> instances.</p>
 *
 * @param <T>  the value's java-type
 * @see NamedValues
 */
public interface NamedValue<T> extends Named {

  /**
   * Gets the value.
   *
   * @return the value
   */
  T getValue();

}

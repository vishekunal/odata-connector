/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.core;

import org.odata4j.edm.EdmType;

/**
 * An immutable OData property instance, consisting of a name, a strongly-typed value, and an edm-type.
 *
 * <p>The {@link OProperties} static factory class can be used to create <code>OProperty</code> instances.</p>
 *
 * @param <T>  the java-type of the property
 * @see OProperties
 */
public interface OProperty<T> extends NamedValue<T> {

  /**
   * Gets the edm-type for this property.
   *
   * @return the edm-type
   */
  EdmType getType();

}

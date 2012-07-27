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
 * An annotation (typed name-value pair) that lives in a namespace.
 */
public interface NamespacedAnnotation<T> extends NamedValue<T> {

  /** Gets the namespace (and namespace prefix) for this annotation. */
  PrefixedNamespace getNamespace();

  /** Gets the java-type of the annotation value. */
  Class<T> getValueType();

}

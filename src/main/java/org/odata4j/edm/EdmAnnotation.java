/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.odata4j.edm;

import org.odata4j.core.GenericAnnotation;

/**
 * Base annotation in the edm type system.
 *
 * <p>Either an {@link EdmAnnotationAttribute} or an {@link EdmAnnotationElement}.
 */
public abstract class EdmAnnotation<T> extends GenericAnnotation<T> {

  protected EdmAnnotation(String namespaceUri, String namespacePrefix, String name, Class<T> valueType, T value) {
    super(namespaceUri, namespacePrefix, name, valueType, value);
  }

  /** Creates an element annotation */
  public static <T> EdmAnnotationElement<T> element(String namespaceUri, String namespacePrefix, String name, Class<T> valueType, T value) {
    return new EdmAnnotationElement<T>(namespaceUri, namespacePrefix, name, valueType, value);
  }

  /** Creates an attribute annotation */
  public static EdmAnnotationAttribute attribute(String namespaceUri, String namespacePrefix, String name, String value) {
    return new EdmAnnotationAttribute(namespaceUri, namespacePrefix, name, value);
  }

}

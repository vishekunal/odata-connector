/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.odata4j.core;

/**
 * Generic implementation of {@link NamespacedAnnotation}.
 */
public class GenericAnnotation<T> implements NamespacedAnnotation<T> {

  private final PrefixedNamespace namespace;
  private final String localName;
  private final Class<T> valueType;
  private final T value;

  public GenericAnnotation(String namespaceUri, String namespacePrefix, String localName, Class<T> valueType, T value) {
    this.namespace = new PrefixedNamespace(namespaceUri, namespacePrefix);
    this.localName = localName;
    this.valueType = valueType;
    this.value = value;
  }

  @Override
  public PrefixedNamespace getNamespace() {
    return namespace;
  }

  public String getName() {
    return localName;
  }

  public String getFullyQualifiedName() {
    return namespace.getPrefix() + ":" + localName;
  }

  public Class<T> getValueType() {
    return valueType;
  }

  public T getValue() {
    return value;
  }

}

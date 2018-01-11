/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.producer.inmemory;

public interface PropertyModel {

  Object getPropertyValue(Object target, String propertyName);

  Iterable<String> getPropertyNames();

  Class<?> getPropertyType(String propertyName);

  Iterable<?> getCollectionValue(Object target, String collectionName);

  Iterable<String> getCollectionNames();

  Class<?> getCollectionElementType(String collectionName);

}

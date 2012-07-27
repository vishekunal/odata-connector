/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.producer.inmemory;

public class EnumsAsStringsPropertyModelDelegate extends PropertyModelDelegate {

  private final PropertyModel propertyModel;

  public EnumsAsStringsPropertyModelDelegate(PropertyModel propertyModel) {
    this.propertyModel = propertyModel;
  }

  @Override
  public PropertyModel getDelegate() {
    return propertyModel;
  }

  @Override
  public Class<?> getPropertyType(String propertyName) {
    Class<?> rt = super.getPropertyType(propertyName);
    if (rt.isEnum())
      return String.class;
    return rt;
  }

  @Override
  public Object getPropertyValue(Object target, String propertyName) {
    Class<?> baseType = super.getPropertyType(propertyName);
    Object rt = super.getPropertyValue(target, propertyName);
    if (baseType.isEnum())
      return ((Enum<?>) rt).name();
    return rt;
  }

}

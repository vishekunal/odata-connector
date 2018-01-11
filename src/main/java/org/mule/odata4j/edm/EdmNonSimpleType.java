/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.edm;

import org.mule.odata4j.core.ImmutableList;

/**
 * Non-primitive type in the EDM type system.
 */
public abstract class EdmNonSimpleType extends EdmType {

  public EdmNonSimpleType(String fullyQualifiedTypeName) {
    this(fullyQualifiedTypeName, null, null);
  }

  public EdmNonSimpleType(String fullyQualifiedTypeName, EdmDocumentation documentation,
      ImmutableList<EdmAnnotation<?>> annotations) {
    super(fullyQualifiedTypeName, documentation, annotations);
  }

  @Override
  public boolean isSimple() {
    return false;
  }

}

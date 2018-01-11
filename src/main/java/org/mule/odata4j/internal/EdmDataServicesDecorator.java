/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.internal;

import org.mule.odata4j.core.PrefixedNamespace;
import org.mule.odata4j.core.ImmutableList;
import org.mule.odata4j.edm.EdmAssociation;
import org.mule.odata4j.edm.EdmComplexType;
import org.mule.odata4j.edm.EdmDataServices;
import org.mule.odata4j.edm.EdmEntitySet;
import org.mule.odata4j.edm.EdmEntityType;
import org.mule.odata4j.edm.EdmFunctionImport;
import org.mule.odata4j.edm.EdmPropertyBase;
import org.mule.odata4j.edm.EdmSchema;
import org.mule.odata4j.edm.EdmStructuralType;
import org.mule.odata4j.edm.EdmType;

public abstract class EdmDataServicesDecorator extends EdmDataServices {

  protected abstract EdmDataServices getDelegate();

  public EdmDataServicesDecorator() {
    super(null, null, null);
  }

  @Override
  public String getVersion() {
    return getDelegate().getVersion();
  }

  @Override
  public ImmutableList<EdmSchema> getSchemas() {
    return getDelegate().getSchemas();
  }

  @Override
  public EdmEntitySet getEdmEntitySet(String entitySetName) {
    return getDelegate().getEdmEntitySet(entitySetName);
  }

  @Override
  public EdmEntitySet getEdmEntitySet(final EdmEntityType type) {
    return getDelegate().getEdmEntitySet(type);
  }

  @Override
  public EdmEntitySet findEdmEntitySet(String entitySetName) {
    return getDelegate().findEdmEntitySet(entitySetName);
  }

  @Override
  public EdmFunctionImport findEdmFunctionImport(String functionImportName) {
    return getDelegate().findEdmFunctionImport(functionImportName);
  }

  @Override
  public EdmComplexType findEdmComplexType(String complexTypeFQName) {
    return getDelegate().findEdmComplexType(complexTypeFQName);
  }

  @Override
  public EdmPropertyBase findEdmProperty(String propName) {
    return getDelegate().findEdmProperty(propName);
  }

  @Override
  public Iterable<EdmEntityType> getEntityTypes() {
    return getDelegate().getEntityTypes();
  }

  @Override
  public Iterable<EdmComplexType> getComplexTypes() {
    return getDelegate().getComplexTypes();
  }

  @Override
  public Iterable<EdmAssociation> getAssociations() {
    return getDelegate().getAssociations();
  }

  @Override
  public Iterable<EdmEntitySet> getEntitySets() {
    return getDelegate().getEntitySets();
  }

  @Override
  public EdmType findEdmEntityType(String fqName) {
    return getDelegate().findEdmEntityType(fqName);
  }

  @Override
  public EdmSchema findSchema(String namespace) {
    return getDelegate().findSchema(namespace);
  }

  @Override
  public Iterable<EdmStructuralType> getStructuralTypes() {
    return getDelegate().getStructuralTypes();
  }

  @Override
  public ImmutableList<PrefixedNamespace> getNamespaces() {
    return getDelegate().getNamespaces();
  }

  @Override
  public Iterable<EdmStructuralType> getSubTypes(EdmStructuralType t) {
    return getDelegate().getSubTypes(t);
  }

  @Override
  public String toString() {
    return getDelegate().toString();
  }

}

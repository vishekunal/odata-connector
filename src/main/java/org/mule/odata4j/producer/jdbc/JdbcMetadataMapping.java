/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.producer.jdbc;

import java.util.Map;

import org.mule.odata4j.edm.EdmDataServicesProvider;
import org.mule.odata4j.edm.EdmDataServices;
import org.mule.odata4j.edm.EdmEntitySet;
import org.mule.odata4j.edm.EdmProperty;
import org.mule.odata4j.producer.jdbc.JdbcModel.JdbcColumn;
import org.mule.odata4j.producer.jdbc.JdbcModel.JdbcTable;

public class JdbcMetadataMapping implements EdmDataServicesProvider {

  private final EdmDataServices metadata;
  private final JdbcModel model;
  private final Map<EdmEntitySet, JdbcTable> entitySetMapping;
  private final Map<EdmProperty, JdbcColumn> propertyMapping;

  public JdbcMetadataMapping(EdmDataServices metadata, JdbcModel model, Map<EdmEntitySet, JdbcTable> entitySetMapping, Map<EdmProperty, JdbcColumn> propertyMapping) {
    this.metadata = metadata;
    this.model = model;
    this.entitySetMapping = entitySetMapping;
    this.propertyMapping = propertyMapping;
  }

  @Override
  public EdmDataServices getMetadata() {
    return metadata;
  }

  public JdbcModel getModel() {
    return model;
  }

  public JdbcTable getMappedTable(EdmEntitySet entitySet) {
    return entitySetMapping.get(entitySet);
  }

  public JdbcColumn getMappedColumn(EdmProperty edmProperty) {
    edmProperty.getDeclaringType().getFullyQualifiedTypeName();
    return propertyMapping.get(edmProperty);
  }

}

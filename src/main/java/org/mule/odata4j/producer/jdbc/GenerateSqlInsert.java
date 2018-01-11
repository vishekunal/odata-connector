/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.producer.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.mule.odata4j.core.OProperty;
import org.mule.odata4j.edm.EdmEntitySet;
import org.mule.odata4j.edm.EdmProperty;
import org.mule.odata4j.core.ImmutableList;
import org.mule.odata4j.core.OEntity;
import org.mule.odata4j.producer.jdbc.JdbcModel.JdbcColumn;
import org.mule.odata4j.producer.jdbc.JdbcModel.JdbcTable;

public class GenerateSqlInsert {

  public SqlStatement generate(JdbcMetadataMapping mapping, EdmEntitySet entitySet, OEntity entity) {
    JdbcTable table = mapping.getMappedTable(entitySet);
    StringBuilder sql = new StringBuilder("INSERT INTO " + table.tableName + "(");
    StringBuilder columns = new StringBuilder();
    StringBuilder values = new StringBuilder();
    List<SqlStatement.SqlParameter> params = new ArrayList<SqlStatement.SqlParameter>();
    for (OProperty<?> prop : entity.getProperties()) {
      if (columns.length() > 0)
        columns.append(", ");
      if (values.length() > 0)
        values.append(", ");
      EdmProperty edmProp = entitySet.getType().findProperty(prop.getName());
      JdbcColumn column = mapping.getMappedColumn(edmProp);
      columns.append(column.columnName);
      values.append("?");
      params.add(new SqlStatement.SqlParameter(prop.getValue(), null));
    }
    sql.append(columns);
    sql.append(") VALUES (");
    sql.append(values);
    sql.append(")");

    return new SqlStatement(sql.toString(), ImmutableList.copyOf(params));
  }

}

/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.producer.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.mule.odata4j.core.ImmutableList;

public class SqlStatement {
  public static class SqlParameter {
    public final Object value;
    public final Integer sqlType;
    public SqlParameter(Object value, Integer sqlType) {
      this.value = value;
      this.sqlType = sqlType;
    }
  }
  public final String sql;
  public final ImmutableList<SqlParameter> params;

  public SqlStatement(String sql, ImmutableList<SqlParameter> params) {
   this.sql = sql;
   this.params = params;
  }

  public PreparedStatement asPreparedStatement(Connection conn) throws SQLException {
    PreparedStatement stmt = conn.prepareStatement(sql);
    for (int i = 0; i < params.size(); i++) {
      SqlParameter p = params.get(i);
      if (p.sqlType == null) {
        stmt.setObject(i + 1, p.value);
      } else {
        stmt.setObject(i + 1, p.value, p.sqlType);
      }
    }
    return stmt;
  }

}
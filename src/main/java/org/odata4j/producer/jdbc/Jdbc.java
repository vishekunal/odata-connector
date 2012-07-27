/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.producer.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.core4j.ThrowingFunc1;
import org.odata4j.core.Throwables;

public class Jdbc {

  public final String driverClassname;
  public final String url;
  public final String user;
  public final String password;

  public Jdbc(String driverClassname, String url, String user, String password) {
    this.driverClassname = driverClassname;
    this.url = url;
    this.user = user;
    this.password = password;
  }

  public <T> T execute(ThrowingFunc1<Connection, T> execute) {
    try {
      Class.forName(driverClassname);
    } catch (ClassNotFoundException e) {
      throw Throwables.propagate(e);
    }
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(url, user, password);
      return execute.apply(conn);
    } catch (Exception e) {
      throw Throwables.propagate(e);
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e) {
          throw Throwables.propagate(e);
        }
      }
    }
  }

}

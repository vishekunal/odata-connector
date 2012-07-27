/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.producer.jdbc;

import org.core4j.Enumerable;
import org.odata4j.core.Action1;
import org.odata4j.producer.jdbc.JdbcModel.JdbcSchema;

public class LimitJdbcModelToDefaultSchema implements Action1<JdbcModel> {

  @Override
  public void apply(JdbcModel model) {
    for (JdbcSchema schema : Enumerable.create(model.schemas).toList()) {
      if (!schema.isDefault)
        model.schemas.remove(schema);
    }
  }

}

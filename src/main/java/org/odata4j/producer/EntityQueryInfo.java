/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.producer;

import java.util.List;
import java.util.Map;

import org.odata4j.expression.BoolCommonExpression;
import org.odata4j.expression.EntitySimpleProperty;

/**
 * <code>QueryInfo</code> represents an OData single-entity query as a strongly-typed immutable data structure.
 */
public class EntityQueryInfo extends QueryInfo {

  /**
   * Creates a new <code>EntityQueryInfo</code> instance.
   */
  public EntityQueryInfo(
      BoolCommonExpression filter,
      Map<String, String> customOptions,
      List<EntitySimpleProperty> expand,
      List<EntitySimpleProperty> select) {
    super(null, null, null, filter, null, null, customOptions, expand, select);
  }

  public static EntityQueryInfo.Builder newBuilder() {
    return new EntityQueryInfo.Builder();
  }

  public static class Builder extends QueryInfo.Builder {

    private BoolCommonExpression filter;

    private Map<String, String> customOptions;
    private List<EntitySimpleProperty> expand;
    private List<EntitySimpleProperty> select;

    public Builder setFilter(BoolCommonExpression filter) {
      this.filter = filter;
      return this;
    }

    public EntityQueryInfo build() {
      return new EntityQueryInfo(filter, customOptions, expand, select);
    }
  }

}

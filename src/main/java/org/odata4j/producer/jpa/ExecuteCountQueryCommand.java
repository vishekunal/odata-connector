/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.odata4j.producer.jpa;

import javax.persistence.Query;

import org.odata4j.producer.QueryInfo;

public class ExecuteCountQueryCommand implements Command {

  @Override
  public boolean execute(JPAContext context) {
    // get the jpql
    String jpql = context.getJPQLQuery();

    // jpql -> jpa query
    Query tq = context.getEntityManager().createQuery(jpql);

    // execute jpa query
    Long count = (Long) tq.getSingleResult();

    QueryInfo query = context.getQueryInfo();
    // apply $skip.
    // example: http://odata.netflix.com/Catalog/Titles/$count?$skip=100
    if (query != null && query.skip != null)
      count = Math.max(0, count - query.skip);

    // apply $top.
    // example: http://odata.netflix.com/Catalog/Titles/$count?$top=10
    if (query != null && query.top != null)
      count = Math.min(count, query.top);

    context.setResult(JPAResults.count(count));

    return false;
  }
}
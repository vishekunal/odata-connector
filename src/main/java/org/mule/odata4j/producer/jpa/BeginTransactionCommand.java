/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.producer.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class BeginTransactionCommand implements Filter {

  @Override
  public boolean execute(JPAContext context) {
    EntityManager em = context.getEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    context.setEntityTransaction(tx);

    return false;
  }

  public boolean postProcess(JPAContext context, Exception ex) {
    EntityTransaction tx = context.getEntityTransaction();
    if (tx != null) {
      if (tx.isActive()) {
        tx.rollback();
      } else {
        context.setEntityTransaction(null);
      }
    }
    return false;
  }
}
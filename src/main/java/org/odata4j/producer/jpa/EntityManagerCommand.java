/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.producer.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class EntityManagerCommand implements Filter {

  private final EntityManagerFactory emf;

  public EntityManagerCommand(EntityManagerFactory emf) {
    this.emf = emf;
  }

  @Override
  public boolean execute(JPAContext context) {
    EntityManager em = this.emf.createEntityManager();
    context.setEntityManager(em);

    return false;
  }

  @Override
  public boolean postProcess(JPAContext context, Exception exception) {
    context.getEntityManager().close();
    context.setEntityManager(null);

    return false;
  }
}
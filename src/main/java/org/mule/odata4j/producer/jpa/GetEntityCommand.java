/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.producer.jpa;

import javax.persistence.metamodel.EntityType;

import org.mule.odata4j.producer.exceptions.NotFoundException;

public class GetEntityCommand implements Command {

  private JPAContext.EntityAccessor accessor;

  public GetEntityCommand() {
    this(JPAContext.EntityAccessor.ENTITY);
  }

  public GetEntityCommand(JPAContext.EntityAccessor accessor) {
    this.accessor = accessor;
  }

  @Override
  public boolean execute(JPAContext context) {

    EntityType<?> jpaEntityType = accessor.getEntity(context)
        .getJPAEntityType();
    Object typeSafeEntityKey = accessor.getEntity(context)
        .getTypeSafeEntityKey();
    Object jpaEntity = context.getEntityManager().find(
        jpaEntityType.getJavaType(), typeSafeEntityKey);

    if (jpaEntity == null) {
      throw new NotFoundException(jpaEntityType
          .getJavaType()
          + " not found with key "
          + typeSafeEntityKey);
    }

    accessor.getEntity(context).setJpaEntity(jpaEntity);

    context.setResult(JPAResults.entity(accessor
        .getEntity(context).getJpaEntity()));

    return false;
  }
}
/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.producer.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.core4j.ThrowingFunc1;
import org.mule.odata4j.command.Command;
import org.mule.odata4j.command.CommandResult;
import org.mule.odata4j.edm.EdmEntitySet;
import org.mule.odata4j.producer.exceptions.BadRequestException;
import org.mule.odata4j.expression.BoolCommonExpression;
import org.mule.odata4j.producer.command.DeleteEntityCommandContext;
import org.mule.odata4j.producer.exceptions.NotFoundException;

public class JdbcDeleteEntityCommand extends JdbcBaseCommand implements Command<DeleteEntityCommandContext> {

  @Override
  public CommandResult execute(DeleteEntityCommandContext context) throws Exception {
    JdbcProducerCommandContext jdbcContext = (JdbcProducerCommandContext) context;

    String entitySetName = context.getEntitySetName();

    final JdbcMetadataMapping mapping = jdbcContext.getBackend().getMetadataMapping();
    final EdmEntitySet entitySet = mapping.getMetadata().findEdmEntitySet(entitySetName);
    if (entitySet == null)
      throw new NotFoundException();

    GenerateSqlDelete deleteGen = jdbcContext.get(GenerateSqlDelete.class);
    BoolCommonExpression filter =  prependPrimaryKeyFilter(mapping, entitySet.getType(), context.getEntityKey(), null);
    final SqlStatement sqlStatement = deleteGen.generate(mapping, entitySet, filter);
    jdbcContext.getJdbc().execute(new ThrowingFunc1<Connection, Void>() {
      @Override
      public Void apply(Connection conn) throws Exception {
        PreparedStatement stmt = sqlStatement.asPreparedStatement(conn);
        int updated = stmt.executeUpdate();
        if (updated == 0)
          throw new BadRequestException("Entity not deleted");
        if (updated > 1)
          throw new BadRequestException(updated + " entities deleted");
        return null;
      }
    });

    return CommandResult.CONTINUE;
  }

}

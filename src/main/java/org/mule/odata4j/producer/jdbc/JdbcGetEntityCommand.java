/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.producer.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.core4j.ThrowingFunc1;
import org.mule.odata4j.command.Command;
import org.mule.odata4j.command.CommandResult;
import org.mule.odata4j.core.OEntity;
import org.mule.odata4j.edm.EdmEntitySet;
import org.mule.odata4j.expression.BoolCommonExpression;
import org.mule.odata4j.producer.EntityResponse;
import org.mule.odata4j.producer.Responses;
import org.mule.odata4j.producer.command.GetEntityCommandContext;
import org.mule.odata4j.producer.exceptions.NotFoundException;

public class JdbcGetEntityCommand extends JdbcBaseCommand implements Command<GetEntityCommandContext> {

  @Override
  public CommandResult execute(final GetEntityCommandContext context) throws Exception {
    JdbcProducerCommandContext jdbcContext = (JdbcProducerCommandContext) context;

    String entitySetName = context.getEntitySetName();

    final JdbcMetadataMapping mapping = jdbcContext.getBackend().getMetadataMapping();
    final EdmEntitySet entitySet = mapping.getMetadata().findEdmEntitySet(entitySetName);
    if (entitySet == null)
      throw new NotFoundException();

    GenerateSqlQuery queryGen = jdbcContext.get(GenerateSqlQuery.class);
    BoolCommonExpression filter = context.getQueryInfo() == null ? null : context.getQueryInfo().filter;
    filter = prependPrimaryKeyFilter(mapping, entitySet.getType(), context.getEntityKey(), filter);
    final SqlStatement sqlStatement = queryGen.generate(mapping, entitySet, filter);
    OEntity entity = jdbcContext.getJdbc().execute(new ThrowingFunc1<Connection, OEntity>() {
      @Override
      public OEntity apply(Connection conn) throws Exception {
        PreparedStatement stmt = sqlStatement.asPreparedStatement(conn);
        ResultSet results = stmt.executeQuery();
        if (results.next()) {
          return toOEntity(mapping, entitySet, results);
        }
        return null;
      }});

    if (entity == null)
      throw new NotFoundException();

    EntityResponse response = Responses.entity(entity);
    context.setResult(response);
    return CommandResult.CONTINUE;
  }

}
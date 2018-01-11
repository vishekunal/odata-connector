/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.producer.jdbc;

import org.mule.odata4j.command.Command;
import org.mule.odata4j.command.CommandResult;
import org.mule.odata4j.producer.command.GetMetadataCommandContext;

public class JdbcGetMetadataCommand implements Command<GetMetadataCommandContext> {

  @Override
  public CommandResult execute(GetMetadataCommandContext context) throws Exception {
    JdbcProducerCommandContext jdbcContext = (JdbcProducerCommandContext) context;

    // 1. generate jdbc model
    JdbcModel model = generateModel(jdbcContext);

    // 2. apply model cleanup
    cleanupModel(model);

    // 3. project jdbc model into edm metadata
    JdbcMetadataMapping mapping = modelToMapping(jdbcContext, model);

    context.setResult(mapping);
    return CommandResult.CONTINUE;
  }

  public JdbcModel generateModel(JdbcProducerCommandContext jdbcContext) {
    return jdbcContext.getJdbc().execute(new GenerateJdbcModel());
  }

  public void cleanupModel(JdbcModel model) {
    new LimitJdbcModelToDefaultSchema().apply(model);
  }

  public JdbcMetadataMapping modelToMapping(JdbcProducerCommandContext jdbcContext, JdbcModel model) {
    return jdbcContext.get(JdbcModelToMetadata.class).apply(model);
  }

}
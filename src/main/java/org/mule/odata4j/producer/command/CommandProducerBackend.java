/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.producer.command;

import java.util.Map;

import org.mule.odata4j.command.Command;
import org.mule.odata4j.command.CommandExecution;
import org.mule.odata4j.core.OEntity;
import org.mule.odata4j.core.OEntityId;
import org.mule.odata4j.core.OEntityKey;
import org.mule.odata4j.core.OFunctionParameter;
import org.mule.odata4j.edm.EdmFunctionImport;
import org.mule.odata4j.producer.EntityQueryInfo;
import org.mule.odata4j.producer.QueryInfo;
import org.mule.odata4j.command.CommandContext;

public interface CommandProducerBackend {

  CommandExecution getCommandExecution();

  <TContext extends CommandContext> Command<TContext> getCommand(Class<TContext> contextType);

  GetMetadataCommandContext newGetMetadataCommandContext();

  GetMetadataProducerCommandContext newGetMetadataProducerCommandContext();

  GetEntitiesCommandContext newGetEntitiesCommandContext(String entitySetName, QueryInfo queryInfo);

  GetEntitiesCountCommandContext newGetEntitiesCountCommandContext(String entitySetName, QueryInfo queryInfo);

  GetEntityCommandContext newGetEntityCommandContext(String entitySetName, OEntityKey entityKey, EntityQueryInfo queryInfo);

  GetNavPropertyCommandContext newGetNavPropertyCommandContext(String entitySetName, OEntityKey entityKey, String navProp, QueryInfo queryInfo);

  GetNavPropertyCountCommandContext newGetNavPropertyCountCommandContext(String entitySetName, OEntityKey entityKey, String navProp, QueryInfo queryInfo);

  CloseCommandContext newCloseCommandContext();

  CreateEntityCommandContext newCreateEntityCommandContext(String entitySetName, OEntity entity);

  CreateEntityAtPropertyCommandContext newCreateEntityAtPropertyCommandContext(String entitySetName, OEntityKey entityKey, String navProp, OEntity entity);

  DeleteEntityCommandContext newDeleteEntityCommandContext(String entitySetName, OEntityKey entityKey);

  MergeEntityCommandContext newMergeEntityCommandContext(String entitySetName, OEntity entity);

  UpdateEntityCommandContext newUpdateEntityCommandContext(String entitySetName, OEntity entity);

  GetLinksCommandContext newGetLinksCommandContext(OEntityId sourceEntity, String targetNavProp);

  CreateLinkCommandContext newCreateLinkCommandContext(OEntityId sourceEntity, String targetNavProp, OEntityId targetEntity);

  UpdateLinkCommandContext newUpdateLinkCommandContext(OEntityId sourceEntity, String targetNavProp, OEntityKey oldTargetEntityKey, OEntityId newTargetEntity);

  DeleteLinkCommandContext newDeleteLinkCommandContext(OEntityId sourceEntity, String targetNavProp, OEntityKey targetEntityKey);

  CallFunctionCommandContext newCallFunctionCommandContext(EdmFunctionImport name, Map<String, OFunctionParameter> params, QueryInfo queryInfo);

}
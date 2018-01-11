/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.jersey.consumer;

import org.core4j.Enumerable;
import org.mule.odata4j.consumer.ODataClientRequest;
import org.mule.odata4j.core.OEntityId;
import org.mule.odata4j.edm.EdmDataServices;

class ConsumerUpdateLinkRequest extends ConsumerEntityRequestBase<Void> {

  private final String targetNavProp;
  private final Object[] oldTargetKeyValues;
  private final OEntityId newTargetEntity;

  ConsumerUpdateLinkRequest(ODataJerseyClient client, String serviceRootUri,
      EdmDataServices metadata, OEntityId sourceEntity, OEntityId newTargetEntity, String targetNavProp, Object... oldTargetKeyValues) {
    super(client, serviceRootUri, metadata, sourceEntity.getEntitySetName(), sourceEntity.getEntityKey());
    this.targetNavProp = targetNavProp;
    this.oldTargetKeyValues = oldTargetKeyValues;
    this.newTargetEntity = newTargetEntity;
  }

  @Override
  public Void execute() {
    getClient().updateLink(this.getRawRequest());
    return null;
  }
  
  @Override
  public ODataClientRequest getRawRequest() {
	  String path = Enumerable.create(getSegments()).join("/");
	  path = ConsumerQueryLinksRequest.linksPath(targetNavProp, oldTargetKeyValues).apply(path);
	  
	  return ODataClientRequest.put(getServiceRootUri() + path, toSingleLink(newTargetEntity));
  }

}
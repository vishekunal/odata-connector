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

class ConsumerCreateLinkRequest extends ConsumerEntityRequestBase<Void> {

  private final String targetNavProp;
  private final OEntityId targetEntity;

  ConsumerCreateLinkRequest(ODataJerseyClient client, String serviceRootUri,
      EdmDataServices metadata, OEntityId sourceEntity, String targetNavProp, OEntityId targetEntity) {
    super(client, serviceRootUri, metadata, sourceEntity.getEntitySetName(), sourceEntity.getEntityKey());
    this.targetNavProp = targetNavProp;
    this.targetEntity = targetEntity;
  }

  @Override
  public Void execute() {
    getClient().createLink(this.getRawRequest());
    return null;
  }
  
  @Override
  public ODataClientRequest getRawRequest() {
	  String path = Enumerable.create(getSegments()).join("/");
	  path = ConsumerQueryLinksRequest.linksPath(targetNavProp, null).apply(path);
	  
	  return ODataClientRequest.post(getServiceRootUri() + path, toSingleLink(targetEntity));
  }

}

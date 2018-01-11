/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.jersey.consumer;

import org.core4j.Enumerable;
import org.mule.odata4j.consumer.ODataClientRequest;
import org.mule.odata4j.core.OEntityKey;
import org.mule.odata4j.edm.EdmDataServices;

public class ConsumerDeleteEntityRequest extends ConsumerEntityRequestBase<Void> {

  ConsumerDeleteEntityRequest(ODataJerseyClient client, String serviceRootUri,
      EdmDataServices metadata, String entitySetName, OEntityKey key) {
    super(client, serviceRootUri, metadata, entitySetName, key);
  }

  @Override
  public Void execute() {
	  return this.execute(null);
  }
  
  public Void execute(String serviceUri) {
    getClient().deleteEntity(this.getRawRequest(serviceUri));
    return null;
  }
  

  @Override
  public ODataClientRequest getRawRequest() {
	  return this.getRawRequest(null);
  }
  
  public ODataClientRequest getRawRequest(String serviceUri) {
	  String path = Enumerable.create(getSegments()).join("/");
	  ODataClientRequest request = ODataClientRequest.delete(serviceUri + path);
	  
	  return request;
  }

}

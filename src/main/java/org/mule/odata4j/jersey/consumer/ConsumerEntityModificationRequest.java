/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.jersey.consumer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.core4j.Enumerable;
import org.core4j.Predicate1;
import org.mule.odata4j.consumer.AbstractConsumerEntityPayloadRequest;
import org.mule.odata4j.consumer.ODataClientRequest;
import org.mule.odata4j.core.OEntity;
import org.mule.odata4j.core.OEntityKey;
import org.mule.odata4j.core.OModifyRequest;
import org.mule.odata4j.core.OProperty;
import org.mule.odata4j.edm.EdmEntitySet;
import org.mule.odata4j.internal.EntitySegment;
import org.mule.odata4j.edm.EdmDataServices;
import org.mule.odata4j.format.Entry;

class ConsumerEntityModificationRequest<T> extends AbstractConsumerEntityPayloadRequest implements OModifyRequest<T> {

  private final T updateRoot;
  private final ODataJerseyClient client;

  private final List<EntitySegment> segments = new ArrayList<EntitySegment>();

  private EdmEntitySet entitySet;

  ConsumerEntityModificationRequest(T updateRoot, ODataJerseyClient client, String serviceRootUri, EdmDataServices metadata, String entitySetName, OEntityKey key) {
    super(entitySetName, serviceRootUri, metadata);
    this.updateRoot = updateRoot;
    this.client = client;

    segments.add(new EntitySegment(entitySetName, key));
    this.entitySet = metadata.getEdmEntitySet(entitySetName);
  }

  @Override
  public OModifyRequest<T> nav(String navProperty, OEntityKey key) {
    segments.add(new EntitySegment(navProperty, key));
    entitySet = metadata.getEdmEntitySet(entitySet.getType().findNavigationProperty(navProperty).getToRole().getType());
    return this;
  }

  @Override
  public boolean execute(String serviceUri) {
	ODataClientRequest request = this.getRawRequest(serviceUri);
    boolean rt = client.updateEntity(request);
    return rt;
  }
  
  /**
   * @see OModifyRequest#getRawRequest()
   */
  @Override
  public ODataClientRequest getRawRequest(String serviceUri) {
	  serviceUri = StringUtils.isBlank(serviceUri) ? this.serviceRootUri : serviceUri;
	  
	  List<OProperty<?>> requestProps = props;
	  if (updateRoot != null) {
		  OEntity updateRootEntity = (OEntity) updateRoot;
		  requestProps = Enumerable.create(updateRootEntity.getProperties()).toList();
		  for (final OProperty<?> prop : props) {
			  OProperty<?> requestProp = Enumerable.create(requestProps).firstOrNull(new Predicate1<OProperty<?>>() {
				  public boolean apply(OProperty<?> input) {
					  return input.getName().equals(prop.getName());
				  }
			  });
			  requestProps.remove(requestProp);
			  requestProps.add(prop);
		  }
	  }
	  
	  OEntityKey entityKey = Enumerable.create(segments).last().key;
	  Entry entry = client.createRequestEntry(entitySet, entityKey, requestProps, links);
	  
	  String path = Enumerable.create(segments).join("/");
	  
	  ODataClientRequest request = updateRoot != null ? ODataClientRequest.put(serviceUri + path, entry) : ODataClientRequest.merge(serviceUri + path, entry);
	  
	  return request;
  }

  @Override
  public OModifyRequest<T> properties(OProperty<?>... props) {
    return super.properties(this, props);
  }

  @Override
  public OModifyRequest<T> properties(Iterable<OProperty<?>> props) {
    return super.properties(this, props);
  }

  @Override
  public OModifyRequest<T> link(String navProperty, OEntity target) {
    return super.link(this, navProperty, target);
  }

  @Override
  public OModifyRequest<T> link(String navProperty, OEntityKey targetKey) {
    return super.link(this, navProperty, targetKey);
  }

}

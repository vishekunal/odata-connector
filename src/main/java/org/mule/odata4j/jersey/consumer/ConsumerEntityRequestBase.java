/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.jersey.consumer;

import java.util.ArrayList;
import java.util.List;

import org.mule.odata4j.core.OEntityKey;
import org.mule.odata4j.core.OEntityId;
import org.mule.odata4j.core.OEntityIds;
import org.mule.odata4j.core.OEntityRequest;
import org.mule.odata4j.edm.EdmDataServices;
import org.mule.odata4j.format.SingleLink;
import org.mule.odata4j.format.SingleLinks;
import org.mule.odata4j.internal.EntitySegment;

abstract class ConsumerEntityRequestBase<T> implements OEntityRequest<T> {

  private final ODataJerseyClient client;

  private final EdmDataServices metadata;
  private final String serviceRootUri;
  private final List<EntitySegment> segments = new ArrayList<EntitySegment>();

  ConsumerEntityRequestBase(ODataJerseyClient client, String serviceRootUri,
      EdmDataServices metadata, String entitySetName, OEntityKey key) {

    this.client = client;
    this.serviceRootUri = serviceRootUri;
    this.metadata = metadata;

    segments.add(new EntitySegment(entitySetName, key));
  }

  protected ODataJerseyClient getClient() {
    return client;
  }

  protected EdmDataServices getMetadata() {
    return metadata;
  }

  protected List<EntitySegment> getSegments() {
    return segments;
  }

  protected String getServiceRootUri() {
    return serviceRootUri;
  }

  @Override
  public OEntityRequest<T> nav(String navProperty, OEntityKey key) {
    segments.add(new EntitySegment(navProperty, key));
    return this;
  }

  @Override
  public OEntityRequest<T> nav(String navProperty) {
    segments.add(new EntitySegment(navProperty, null));
    return this;
  }

  protected SingleLink toSingleLink(OEntityId entity) {
    String uri = getServiceRootUri();
    if (!uri.endsWith("/"))
      uri += "/";
    uri += OEntityIds.toKeyString(entity);
    return SingleLinks.create(uri);
  }

}
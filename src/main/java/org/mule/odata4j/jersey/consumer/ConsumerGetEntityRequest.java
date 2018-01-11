/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.jersey.consumer;

import org.core4j.Enumerable;
import org.mule.odata4j.consumer.ODataClientRequest;
import org.mule.odata4j.core.OEntityKey;
import org.mule.odata4j.format.Settings;
import org.mule.odata4j.core.ODataVersion;
import org.mule.odata4j.core.OEntityGetRequest;
import org.mule.odata4j.edm.EdmDataServices;
import org.mule.odata4j.edm.EdmEntitySet;
import org.mule.odata4j.edm.EdmNavigationProperty;
import org.mule.odata4j.format.Entry;
import org.mule.odata4j.format.Feed;
import org.mule.odata4j.format.FormatParser;
import org.mule.odata4j.format.FormatParserFactory;
import org.mule.odata4j.internal.EntitySegment;
import org.mule.odata4j.internal.FeedCustomizationMapping;
import org.mule.odata4j.internal.InternalUtil;

import com.sun.jersey.api.client.ClientResponse;

class ConsumerGetEntityRequest<T> extends ConsumerEntityRequestBase<T> implements OEntityGetRequest<T> {

  private final Class<T> entityType;
  private final FeedCustomizationMapping fcMapping;

  private String select;
  private String expand;
  private ODataVersion version;

  ConsumerGetEntityRequest(ODataJerseyClient client, Class<T> entityType, String serviceRootUri,
                           EdmDataServices metadata, String entitySetName, OEntityKey key, FeedCustomizationMapping fcMapping, ODataVersion version) {
    
	super(client, serviceRootUri, metadata, entitySetName, key);
    this.entityType = entityType;
    this.fcMapping = fcMapping;
    this.version = version;
  }

  @Override
  public ConsumerGetEntityRequest<T> select(String select) {
    this.select = select;
    return this;
  }

  @Override
  public ConsumerGetEntityRequest<T> expand(String expand) {
    this.expand = expand;
    return this;
  }
  
  @Override
  public ODataClientRequest getRawRequest() {
	  String path = Enumerable.create(getSegments()).join("/");
	  
	  ODataClientRequest request = ODataClientRequest.get(getServiceRootUri() + path);
	  
	  if (select != null) {
		  request = request.queryParam("$select", select);
	  }
	  
	  if (expand != null) {
		  request = request.queryParam("$expand", expand);
	  }
	  
	  return request;
  }

  @Override
  public T execute() {

    ClientResponse response = getClient().getEntity(this.getRawRequest());
    if (response == null)
      return null;

    //  the first segment contains the entitySetName we start from
    EdmEntitySet entitySet = getMetadata().getEdmEntitySet(getSegments().get(0).segment);
    for (EntitySegment segment : getSegments().subList(1, getSegments().size())) {
      EdmNavigationProperty navProperty = entitySet.getType().findNavigationProperty(segment.segment);
      entitySet = getMetadata().getEdmEntitySet(navProperty.getToRole().getType());
    }

    OEntityKey key = Enumerable.create(getSegments()).last().key;

    // TODO determine the service version from header (and metadata?)
    FormatParser<Feed> parser = FormatParserFactory
        .getParser(Feed.class, getClient().getFormatType(),
            new Settings(this.version, getMetadata(), entitySet.getName(), key, fcMapping));

    Entry entry = Enumerable.create(parser.parse(getClient().getFeedReader(response)).getEntries())
        .firstOrNull();

    return (T) InternalUtil.toEntity(entityType, entry.getEntity());
  }

}

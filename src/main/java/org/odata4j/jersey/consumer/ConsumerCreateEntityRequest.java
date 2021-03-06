/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.odata4j.jersey.consumer;

import java.util.Arrays;

import org.odata4j.consumer.AbstractConsumerEntityPayloadRequest;
import org.odata4j.consumer.ODataClientRequest;
import org.odata4j.core.OCreateRequest;
import org.odata4j.core.ODataConstants;
import org.odata4j.core.ODataVersion;
import org.odata4j.core.OEntities;
import org.odata4j.core.OEntity;
import org.odata4j.core.OEntityKey;
import org.odata4j.core.OLinks;
import org.odata4j.core.OProperty;
import org.odata4j.edm.EdmDataServices;
import org.odata4j.edm.EdmEntitySet;
import org.odata4j.edm.EdmMultiplicity;
import org.odata4j.edm.EdmNavigationProperty;
import org.odata4j.format.Entry;
import org.odata4j.format.FormatParser;
import org.odata4j.format.FormatParserFactory;
import org.odata4j.format.Settings;
import org.odata4j.format.xml.XmlFormatWriter;
import org.odata4j.internal.FeedCustomizationMapping;
import org.odata4j.internal.InternalUtil;

import com.sun.jersey.api.client.ClientResponse;

class ConsumerCreateEntityRequest<T> extends AbstractConsumerEntityPayloadRequest implements OCreateRequest<T> {

  private final ODataJerseyClient client;
  private OEntity parent;
  private String navProperty;

  private final FeedCustomizationMapping fcMapping;

  ConsumerCreateEntityRequest(ODataJerseyClient client, String serviceRootUri, EdmDataServices metadata, String entitySetName, FeedCustomizationMapping fcMapping) {
    super(entitySetName, serviceRootUri, metadata);
    this.client = client;
    this.fcMapping = fcMapping;
  }

  @SuppressWarnings("unchecked")
  @Override
  public T execute(String url) {

    ODataClientRequest request = this.getRawRequest(url);
    ClientResponse response = client.createEntity(request);

    ODataVersion version = InternalUtil.getDataServiceVersion(response.getHeaders().getFirst(ODataConstants.Headers.DATA_SERVICE_VERSION));

    FormatParser<Entry> parser = FormatParserFactory.getParser(Entry.class, client.getFormatType(), new Settings(version, metadata, entitySetName, null, fcMapping));
    
    Entry entry = parser.parse(client.getFeedReader(response));

    return (T) entry.getEntity();
  }
  
  /**
   * @see org.odata4j.core.OCreateRequest#getRawRequest()
   */
  @Override
  public ODataClientRequest getRawRequest(String serviceUri) {
	  
	  EdmEntitySet ees = metadata.getEdmEntitySet(entitySetName);
	  Entry entry = client.createRequestEntry(ees, null, props, links);
	  
	  StringBuilder url = new StringBuilder(serviceUri);
	  if (parent != null) {
		  url.append(InternalUtil.getEntityRelId(parent))
		  .append("/")
		  .append(navProperty);
	  } else {
		  url.append(entitySetName);
	  }
	  
	  return ODataClientRequest.post(url.toString(), entry);
  }

  @SuppressWarnings("unchecked")
  @Override
  public T get() {
    EdmEntitySet entitySet = metadata.getEdmEntitySet(entitySetName);
    return (T) OEntities.createRequest(entitySet, props, links);
  }

  @Override
  public OCreateRequest<T> properties(OProperty<?>... props) {
    return super.properties(this, props);
  }

  @Override
  public OCreateRequest<T> properties(Iterable<OProperty<?>> props) {
    return super.properties(this, props);
  }

  @Override
  public OCreateRequest<T> link(String navProperty, OEntity target) {
    return super.link(this, navProperty, target);
  }

  @Override
  public OCreateRequest<T> link(String navProperty, OEntityKey targetKey) {
    return super.link(this, navProperty, targetKey);
  }

  @Override
  public OCreateRequest<T> addToRelation(OEntity parent, String navProperty) {
    if (parent == null || navProperty == null) {
      throw new IllegalArgumentException("please provide the parent and the navProperty");
    }

    this.parent = parent;
    this.navProperty = navProperty;
    return this;
  }

  @Override
  public OCreateRequest<T> inline(String navProperty, OEntity... entities) {
    EdmEntitySet entitySet = metadata.getEdmEntitySet(entitySetName);
    EdmNavigationProperty navProp = entitySet.getType().findNavigationProperty(navProperty);
    if (navProp == null)
      throw new IllegalArgumentException("unknown navigation property " + navProperty);

    // TODO get rid of XmlFormatWriter
    String rel = XmlFormatWriter.related + navProperty;
    String href = entitySetName + "/" + navProperty;
    if (navProp.getToRole().getMultiplicity() == EdmMultiplicity.MANY) {
      links.add(OLinks.relatedEntitiesInline(rel, navProperty, href, Arrays.asList(entities)));
    } else {
      if (entities.length > 1)
        throw new IllegalArgumentException("only one entity is allowed for this navigation property " + navProperty);

      links.add(OLinks.relatedEntityInline(rel, navProperty, href,
          entities.length > 0 ? entities[0] : null));
    }

    return this;
  }

}

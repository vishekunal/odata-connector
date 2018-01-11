/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.consumer;

import org.mule.odata4j.core.OEntity;
import org.mule.odata4j.core.OEntityGetRequest;
import org.mule.odata4j.core.OEntityKey;
import org.mule.odata4j.core.OQueryRequest;
import org.mule.odata4j.core.ORelatedEntitiesLink;
import org.mule.odata4j.core.ORelatedEntityLink;

public abstract class AbstractODataConsumer implements ODataConsumer {

  private String serviceRootUri;

  protected AbstractODataConsumer(String serviceRootUri) {
    this.serviceRootUri = serviceRootUri;
  }

  @Override
  public String getServiceRootUri() {
    return this.serviceRootUri;
  }

  @Override
  public OEntityGetRequest<OEntity> getEntity(String entitySetName, Object keyValue) {
    return this.getEntity(entitySetName, OEntityKey.create(keyValue));
  }

  @Override
  public OEntityGetRequest<OEntity> getEntity(String entitySetName, OEntityKey key) {
    return this.getEntity(OEntity.class, entitySetName, key);
  }

  @Override
  public <T> OEntityGetRequest<T> getEntity(Class<T> entityType, String entitySetName, Object keyValue) {
    return this.getEntity(entityType, entitySetName, OEntityKey.create(keyValue));
  }

  @Override
  public OEntityGetRequest<OEntity> getEntity(OEntity entity) {
    return this.getEntity(entity.getEntitySet().getName(), entity.getEntityKey());
  }

  @Override
  public OEntityGetRequest<OEntity> getEntity(ORelatedEntityLink link) {
    ParsedHref parsed = ParsedHref.parse(link.getHref());
    return (OEntityGetRequest<OEntity>) this.getEntity(parsed.entitySetName, parsed.entityKey).nav(parsed.navProperty);
  }

  @Override
  public OQueryRequest<OEntity> getEntities(ORelatedEntitiesLink link) {
    ParsedHref parsed = ParsedHref.parse(link.getHref());
    return getEntities(parsed.entitySetName).nav(parsed.entityKey, parsed.navProperty);
  }

  @Override
  public OQueryRequest<OEntity> getEntities(String entitySetHref) {
    return getEntities(OEntity.class, entitySetHref);
  }

  private static class ParsedHref {
    public String entitySetName;
    public OEntityKey entityKey;
    public String navProperty;

    private ParsedHref() {}

    public static ParsedHref parse(String href) {
      // href: entityset(keyvalue[,keyvalue])/navprop[/navprop]
      // keyvalue: <literal> for one key value -or- <name=literal> for multiple key values

      int slashIndex = href.indexOf('/');
      String head = href.substring(0, slashIndex);
      String navProperty = href.substring(slashIndex + 1);

      int pIndex = head.indexOf('(');
      String entitySetName = head.substring(0, pIndex);

      String keyString = head.substring(pIndex + 1, head.length() - 1); // keyvalue[,keyvalue]

      ParsedHref rt = new ParsedHref();
      rt.entitySetName = entitySetName;
      rt.entityKey = OEntityKey.parse(keyString);
      rt.navProperty = navProperty;
      return rt;
    }
  }

}

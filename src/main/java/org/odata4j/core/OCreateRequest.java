/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.odata4j.core;

import org.odata4j.consumer.ODataClientRequest;

/**
 * A consumer-side create-request builder.  Call {@link #execute()} to issue the request.
 *
 * @param <T>  the entity representation as a java type
 */
public interface OCreateRequest<T> {

  /**
   * Set properties on the new entity.
   * 
   * @param props  the properties
   * @return the create-request builder 
   */
  OCreateRequest<T> properties(OProperty<?>... props);

  /**
   * Set properties on the new entity.
   * 
   * @param props  the properties
   * @return the create-request builder 
   */
  OCreateRequest<T> properties(Iterable<OProperty<?>> props);

  /**
  * Define an explicit link to another related entity.
  * 
  * @param navProperty  the new entity's relationship navigation property
  * @param target  the link target entity
  * @return the create-request builder
  * 
  * @see #addToRelation(OEntity, String)
   * @see <a href="http://www.odata.org/developers/protocols/operations#CreatingnewEntries">http://www.odata.org/developers/protocols/operations#CreatingnewEntries</a>
  */
  OCreateRequest<T> link(String navProperty, OEntity target);

  /**
   * Define an explicit link to another related entity.
   * 
   * @param navProperty  the new entity's relationship navigation property
   * @param targetKey  the key of the link target entity
   * @return the create-request builder
   * 
   * @see #addToRelation(OEntity, String)
     * @see <a href="http://www.odata.org/developers/protocols/operations#CreatingnewEntries">http://www.odata.org/developers/protocols/operations#CreatingnewEntries</a>
   */
  OCreateRequest<T> link(String navProperty, OEntityKey targetKey);

  /**
   * Use a related parent entity's relationship collection to define an implicit link. 
   * <p>e.g. create a new Product entity using /Categories(10)/Products instead of /Products</p>
   * 
   * @param parent  the parent entity
   * @param navProperty  the parent entity's relationship collection navigation property
   * @return the create-request builder
   * 
   * @see #link(String, OEntity)
   * @see <a href="http://www.odata.org/developers/protocols/operations#CreatingnewEntries">http://www.odata.org/developers/protocols/operations#CreatingnewEntries</a>
   */
  OCreateRequest<T> addToRelation(OEntity parent, String navProperty);

  /**
   * Create related entities inline as part of a single request.
   *  
   * @param navProperty  the new entity's relationship navigation property
   * @param entities  related entities, returned by {@link #get()}
   * @return  the create-request builder
   * 
   * @see #get()
   */
  OCreateRequest<T> inline(String navProperty, OEntity... entities);

  /**
   * Sends the create-request to the OData service and returns the newly 
   * created entity.
   * 
   * @return newly created entity
   */
  T execute(String serviceUri);

  /**
   * Returns a locally-built entity and does not send the create-request
   * to the service. The locally-built entity can be used inline as part of
   * other create-requests.
   * 
   * @return new locally built entity
   * @see #inline(String, OEntity...)
   */
  T get();
  
  /**
   * Returns the low level, not yet executed OData request
   * @return
   */
  public ODataClientRequest getRawRequest(String serviceUri);

}

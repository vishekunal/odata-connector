/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.core;

import org.mule.odata4j.consumer.ODataClientRequest;

/**
 * A consumer-side modification-request builder, used for operations such as MERGE and UPDATE.  Call {@link #execute()} to issue the request.
 *
 * @param <T>  the entity representation as a java type
 */
public interface OModifyRequest<T> {

  /**
    * Set properties on the new entity.
    * 
    * @param props  the properties
    * @return the modification-request builder 
    */
  OModifyRequest<T> properties(OProperty<?>... props);

  /**
   * Set properties on the new entity.
   * 
   * @param props  the properties
   * @return the modification-request builder 
   */
  OModifyRequest<T> properties(Iterable<OProperty<?>> props);

  /**
  * Define an explicit link to another related entity.
  * 
  * @param navProperty  the entity's relationship navigation property
  * @param target  the link target entity
  * @return the modification-request builder
  */
  OModifyRequest<T> link(String navProperty, OEntity target);

  /**
   * Define an explicit link to another related entity.
   * 
   * @param navProperty  the entity's relationship navigation property
   * @param targetKey  the key of the link target entity
   * @return the modification-request builder
   */
  OModifyRequest<T> link(String navProperty, OEntityKey targetKey);

  /**
  * Sends the modification-request to the OData service and returns success or failure.
  * 
  * @return success or failure
  */
  boolean execute(String serviceUri);

  /**
   * Select a new modification entity by navigating to a referenced entity in a child collection.
   * 
   * @param navProperty  the child collection
   * @param key  the referenced entity's key
   * @return the modification-request builder
   */
  OModifyRequest<T> nav(String navProperty, OEntityKey key);
  
  /**
   * Returns the low level, not yet executed OData request
   * @return
   */
  public ODataClientRequest getRawRequest(String serviceUri);

}

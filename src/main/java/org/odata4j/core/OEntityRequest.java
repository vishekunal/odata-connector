/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.core;

import org.odata4j.consumer.ODataClientRequest;

/**
 * A consumer-side entity-request builder, used for operations on a single entity such as DELETE.  Call {@link #execute()} to issue the request.
 *
 * @param <T>  the java-type of the operation response
 */
public interface OEntityRequest<T> {

  /**
   * Sends the entity-request to the OData service and returns the response.
   *
   * @return the operation response
   */
  T execute();

  /**
   * Navigates to a related entity using a collection navigation property.
   *
   * @param navProperty  the collection navigation property
   * @param key  the entity-key within the collection
   * @return the entity-request builder
   */
  OEntityRequest<T> nav(String navProperty, OEntityKey key);

  /**
   * Navigates to a related entity using a single-valued navigation property.
   *
   * @param navProperty  the single-valued collection navigation property.
   * @return the entity-request builder
   */
  OEntityRequest<T> nav(String navProperty);
  
  /**
   * Returns the low level, not yet executed OData request
   * @return
   */
  public ODataClientRequest getRawRequest();

}

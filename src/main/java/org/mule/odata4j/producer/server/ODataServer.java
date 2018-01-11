/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.producer.server;

import javax.ws.rs.core.Application;

import org.mule.odata4j.producer.resources.DefaultODataApplication;
import org.mule.odata4j.producer.resources.AbstractODataApplication;
import org.mule.odata4j.producer.resources.RootApplication;

/**
 * Generic OData server
 */
public interface ODataServer {

  /**
   * Starts the OData server.
   *
   * @return this server
   */
  ODataServer start();

  /**
   * Stops the OData server.
   *
   * @return this server
   */
  ODataServer stop();

  /**
   * Sets the OData application.
   *
   * @param odataApp  the OData application class
   * @return this server
   * @see {@link AbstractODataApplication}, {@link DefaultODataApplication}
   */
  ODataServer setODataApplication(Class<? extends Application> odataApp);

  /**
   * Sets the root application.
   *
   * @param rootApp  the root application class
   * @return this server
   * @see {@link RootApplication}
   */
  ODataServer setRootApplication(Class<? extends Application> rootApp);
}

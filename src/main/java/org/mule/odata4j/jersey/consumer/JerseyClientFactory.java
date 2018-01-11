/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.jersey.consumer;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;

/**
 * Client-side extension mechanism - provides a Jersey {@link Client} implementation given a configuration.
 */
public interface JerseyClientFactory {

  /**
   * Creates a new Jersey client.
   *
   * @param clientConfig  the Jersey client api configuration
   */
  Client createClient(ClientConfig clientConfig);

}

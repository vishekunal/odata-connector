/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.odata4j.jersey.consumer;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;

/**
 * The default factory implementation for Jersey clients.
 *
 * <p>Use {@link #INSTANCE} to obtain a reference to the singleton instance of this factory.</p>
 */
public class DefaultJerseyClientFactory implements JerseyClientFactory {

  public static final DefaultJerseyClientFactory INSTANCE = new DefaultJerseyClientFactory();

  private DefaultJerseyClientFactory() {}

  /**
   * Creates a new default {@link Client} by calling: <code>Client.create(clientConfig)</code>
   */
  @Override
  public Client createClient(ClientConfig clientConfig) {
    Client client = Client.create(clientConfig);

    return client;
  }

}

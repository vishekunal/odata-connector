/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.odata4j.jersey.consumer.behaviors;

import org.odata4j.consumer.behaviors.OClientBehavior;

import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.filter.Filterable;

public interface JerseyClientBehavior extends OClientBehavior {

  public void modify(ClientConfig clientConfig);

  public void modifyClientFilters(Filterable client);

  public void modifyWebResourceFilters(Filterable webResource);

}

/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.jersey.producer.resources;

import java.util.Properties;

import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.odata4j.producer.ODataProducer;
import org.odata4j.producer.resources.DefaultODataProducerProvider;

import com.sun.jersey.api.core.ResourceConfig;

/**
 * Jersey-specific OData producer provider.
 *
 * <p>This provider allows specifying a factory as part of the container's deployment
 * descriptor (web.xml). The factory is used to create an OData producer instance.
 * <pre>
 * {@code
 * <init-param>
 *   <param-name>odata4j.producerfactory</param-name>
 *   <param-value>... full qualified class name ...</param-value>
 * </init-param>
 * }
 * </pre>
 */
@Provider
public class ODataProducerProvider extends DefaultODataProducerProvider {

  @Context
  private ResourceConfig resourceConfig;

  @Override
  protected ODataProducer createInstanceFromFactoryInContainerSpecificSetting() {
    if (resourceConfig != null && resourceConfig.getProperty(FACTORY_PROPNAME) != null) {
      String factoryTypeName = (String) resourceConfig.getProperty(FACTORY_PROPNAME);
      log("Creating producer from factory in resource config: " + factoryTypeName);
      Properties props = new Properties();
      props.putAll(resourceConfig.getProperties());
      return newProducerFromFactory(factoryTypeName, props);
    }
    return null;
  }
}

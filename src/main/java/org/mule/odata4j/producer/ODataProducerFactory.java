/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.producer;

import java.util.Properties;

/**
 * An <code>ODataProducerFactory</code> creates new OData producer instances given properties.
 */
public interface ODataProducerFactory {

  /**
   * Create a new OData producer from the given properties.
   * 
   * @param properties  the properties to use when constructing the producer
   * @return the new producer
   */
  ODataProducer create(Properties properties);
}

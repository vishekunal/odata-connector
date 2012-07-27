/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.producer.resources;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

/**
 * Abstract OData application.
 *
 * <p>Implementers should override the {@code getClasses} method, but call
 * {@code super.getClasses()} before adding container-specific resources and providers as
 * required.
 */
public abstract class AbstractODataApplication extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> classes = new HashSet<Class<?>>();
    classes.add(EntitiesRequestResource.class);
    classes.add(EntityRequestResource.class);
    classes.add(MetadataResource.class);
    classes.add(ServiceDocumentResource.class);
    classes.add(ODataBatchProvider.class);
    return classes;
  }
}

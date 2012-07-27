/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.edm;

/**
 * An object that knows how to produce an {@link EdmDataServices} model in the
 * context of an {@link EdmDecorator}.
 */
public interface EdmGenerator {

  /** Produces a new mutable {@link EdmDataServices} model given an optional decorator. */
  EdmDataServices.Builder generateEdm(EdmDecorator decorator);

}

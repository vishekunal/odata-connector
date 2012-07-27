/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.producer;

import org.odata4j.core.OComplexObject;

/**
 * An <code>ComplexObjectResponse</code> is a response to a client request expecting a single instance of a complex type.
 *
 * <p>The {@link Responses} static factory class can be used to create <code>ComplexObjectResponse</code> instances.</p>
 */
public interface ComplexObjectResponse extends BaseResponse {

  OComplexObject getObject();

}

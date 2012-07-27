/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.format;

import org.odata4j.core.OEntity;

/**
 * Building block for OData payload. 
 * 
 * @see Feed
 */
public interface Entry {

  String getUri();

  String getETag();

  OEntity getEntity();

}

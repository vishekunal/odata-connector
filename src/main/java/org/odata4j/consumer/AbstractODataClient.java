/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.consumer;

import org.odata4j.format.FormatType;

public abstract class AbstractODataClient implements ODataClient {

  protected AbstractODataClient(FormatType formatType) {
    this.formatType = formatType;
  }

  private FormatType formatType;

  @Override
  public FormatType getFormatType() {
    return this.formatType;
  }

}

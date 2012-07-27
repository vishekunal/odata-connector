/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.producer.exceptions;

import javax.ws.rs.core.Response;

public class BadRequestException extends ODataException {

  private static final long serialVersionUID = 1L;

  public BadRequestException() {
    super(Response.status(400).build());
  }

  public BadRequestException(String message) {
    super(Response.status(400).entity(message).build(), message);
  }

}

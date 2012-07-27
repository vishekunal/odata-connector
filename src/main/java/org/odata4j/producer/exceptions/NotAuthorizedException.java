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

public class NotAuthorizedException extends ODataException {

  private static final long serialVersionUID = 1L;

  public NotAuthorizedException() {
    super(Response.status(401).build());
  }

  public NotAuthorizedException(String message) {
    super(Response.status(401).entity(message).build());
  }

}

/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.producer.exceptions;

import javax.ws.rs.core.Response;

public class ServerErrorException extends ODataException {

  private static final long serialVersionUID = 1L;

  public ServerErrorException() {
    super(Response.status(500).build());
  }

  public ServerErrorException(String message) {
    super(Response.status(500).entity(message).build());
  }

}

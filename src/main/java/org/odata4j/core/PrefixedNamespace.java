/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.core;

/**
 * A namespace - consists of a uri and local prefix.
 */
public class PrefixedNamespace {

  private final String uri;
  private final String prefix;

  public PrefixedNamespace(String uri, String prefix) {
    this.uri = uri;
    this.prefix = prefix;
  }

  public String getUri() {
    return uri;
  }

  public String getPrefix() {
    return prefix;
  }

}

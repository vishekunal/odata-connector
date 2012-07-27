/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.internal;

import org.odata4j.core.OEntityKey;

public class EntitySegment {

  public final String segment;
  public final OEntityKey key;

  public EntitySegment(String segment, OEntityKey key) {
    this.segment = segment;
    this.key = key;
  }

  @Override
  public String toString() {
    return this.segment + (key == null ? "" : key.toKeyString());
  }

}

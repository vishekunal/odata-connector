/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.internal;

import org.mule.odata4j.core.OEntityKey;

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

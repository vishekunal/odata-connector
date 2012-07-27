/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.core;

import java.util.UUID;

/**
 * A custom Guid class is necessary to interop with .net Guid strings incompatible with {@link UUID}.
 * <p>Guids are equal if their string representations are equal.</p>
 */
public class Guid {

  private String value;

  public Guid() {}
  
  public Guid(String value) {
    this.value = value;
  }

  /**
   * Return a Guid for a given string.
   *
   * @param value  the guid's string representation
   * @return a new Guid
   */
  public static Guid fromString(String value) {
    return new Guid(value);
  }

  /**
   * Return a Guid for a given UUID.
   *
   * @param uuid  an existing UUID
   * @return a new Guid
   */
  public static Guid fromUUID(UUID uuid) {
    return new Guid(uuid.toString());
  }

  /**
   * Generate a new Guid.
   *
   * @return a new Guid
   */
  public static Guid randomGuid() {
    return new Guid(UUID.randomUUID().toString());
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public boolean equals(Object other) {
    return (other instanceof Guid) && ((Guid) other).value.equals(value);
  }
  
  public String getValue() {
	return value;
  }

  public void setValue(String value) {
	this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }

}

/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.core;

/**
 * V1 or V2
 */
public enum ODataVersion {

  // order of definition is important
  V1("1.0"),
  V2("2.0");

  /**
   * 1.0 or 2.0
   */
  public final String asString;

  private ODataVersion(String asString) {
    this.asString = asString;
  }

  /**
   * Identify a version by version string.
   *
   * @param str  the version string
   * @return the version enum
   */
  public static ODataVersion parse(String str) {
    if (V1.asString.equals(str))
      return V1;
    else if (V2.asString.equals(str)) {
      return V2;
    } else {
      throw new IllegalArgumentException("Unknown ODataVersion " + str);
    }
  }

  /** Returns true if the version this is greater than the target version */
  public boolean isVersionGreaterThan(ODataVersion target) {
    return this.compareTo(target) > 0;
  }

  /** Returns true if the version this is greater than or equal to the target version */
  public boolean isVersionGreaterThanOrEqualTo(ODataVersion target) {
    return this.compareTo(target) >= 0;
  }

}

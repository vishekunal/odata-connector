/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.odata4j.core;

import java.util.Comparator;

/**
 * A static factory to create useful {@link Comparator} instances.
 */
public class OComparators {

  private OComparators() {}

  @SuppressWarnings("rawtypes")
  public static Comparator<NamedValue> namedValueByNameRaw() {
    return new Comparator<NamedValue>() {
      public int compare(NamedValue lhs, NamedValue rhs) {
        return lhs.getName().compareTo(rhs.getName());
      }
    };
  }

  public static Comparator<OProperty<?>> propertyByName() {
    return new Comparator<OProperty<?>>() {
      public int compare(OProperty<?> lhs, OProperty<?> rhs) {
        return lhs.getName().compareTo(rhs.getName());
      }
    };
  }

}

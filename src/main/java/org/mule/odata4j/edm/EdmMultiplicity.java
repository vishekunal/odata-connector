/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.edm;

/**
 * The possible number of entity types at each end of the relationship.
 * The multiplicity of an association end can have a value of one (1), zero or one (0..1), or many (*).
 */
public enum EdmMultiplicity {

  ZERO_TO_ONE("0..1"), MANY("*"), ONE("1");

  private final String symbolString;

  private EdmMultiplicity(String symbolString) {
    this.symbolString = symbolString;
  }

  public String getSymbolString() {
    return symbolString;
  }

  public static EdmMultiplicity fromSymbolString(String symbolString) {
    for (EdmMultiplicity m : EdmMultiplicity.values()) {
      if (m.getSymbolString().equals(symbolString))
        return m;
    }
    throw new IllegalArgumentException("Invalid symbolString " + symbolString);
  }

}

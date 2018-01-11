/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.producer;

/**
 * A recursive path has an associated depth limiter.
 *
 * <p>The last component of the path is a number indicating the maximum depth
 * to recurse.  0 means do not limit the recursion.
 *
 * <p>Examples:
 * <pre>
 * Properties/0
 * SubTypes/1
 * </pre>
 */
public class RecursivePropertyPath extends PropertyPath {

  private final int depth;

  public RecursivePropertyPath(PropertyPath path, int depth) {
    super(path);
    this.depth = depth;
  }

  public int getDepth() {
    return depth;
  }

  public boolean isUnlimited() {
    return depth <= 0;
  }

  public boolean isValidAtDepth(int d) {
    return isUnlimited() || d <= this.depth;
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj) && obj instanceof RecursivePropertyPath && ((RecursivePropertyPath) obj).depth == this.depth;
  }

  @Override
  public int hashCode() {
    return super.hashCode() + this.depth;
  }

}

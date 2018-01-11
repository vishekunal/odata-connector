/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.edm;

/**
 * A CSDL Documentation element.
 *
 * <p>The Documentation element in conceptual schema definition language (CSDL) can be used
 * to provide information about an object that is defined in a parent element.
 *
 * @see <a href="http://msdn.microsoft.com/en-us/library/bb738641.aspx">[msdn] Documentation Element (CSDL)</a>
 */
public class EdmDocumentation {

  private final String summary;
  private final String longDescription;

  public EdmDocumentation(String summary, String longDescription) {
    this.summary = summary;
    this.longDescription = longDescription;
  }

  /** A brief description of the parent element. */
  public String getSummary() {
    return this.summary;
  }

  /** An extensive description of the parent element. */
  public String getLongDescription() {
    return this.longDescription;
  }

}

/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.odata4j.edm;

/**
 * A CSDL Annotation element.
 *
 * <p>Annotation elements in conceptual schema definition language (CSDL) are custom XML elements
 * in the conceptual model. Annotation elements can be used to provide extra metadata about
 * the elements in a conceptual model.
 *
 * @see <a href="http://msdn.microsoft.com/en-us/library/ee473443.aspx">[msdn] Annotation Elements (CSDL)</a>
 */
public class EdmAnnotationElement<T> extends EdmAnnotation<T> {

  public EdmAnnotationElement(String namespaceUri, String namespacePrefix, String name, Class<T> valueType, T value) {
    super(namespaceUri, namespacePrefix, name, valueType, value);
  }

}

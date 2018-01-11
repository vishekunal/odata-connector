/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.stax2;

public class QName2 {

  private final String namespaceUri;
  private final String localPart;
  private final String prefix;
  private final String fqName;

  public QName2(String localPart) {
    this(null, localPart);
  }

  public QName2(String namespaceUri, String localPart) {
    this(namespaceUri, localPart, null);
  }

  public QName2(String namespaceUri, String localPart, String prefix) {
    this.namespaceUri = namespaceUri;
    this.localPart = localPart;
    this.prefix = prefix;
    this.fqName = "{" + namespaceUri + "}" + "{" + prefix + "}" + localPart;
  }

  public String getLocalPart() {
    return localPart;
  }

  public String getNamespaceUri() {
    return namespaceUri;
  }

  public String getPrefix() {
    return prefix;
  }

  @Override
  public int hashCode() {
    return fqName.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof QName2))
      return false;
    return fqName.equals(((QName2) obj).fqName);
  }

  @Override
  public String toString() {
    return fqName;
  }

}

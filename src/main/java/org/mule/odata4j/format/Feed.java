/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.format;

/**
 * <code>Feed</code> and <code>Entry</code> are buildings block for the ODATA payload.
 * There are differences between the Atom and Json format. These interfaces are used
 * where we can handle both formats the same way.
 */
public interface Feed {

  String getNext();

  Iterable<Entry> getEntries();
}

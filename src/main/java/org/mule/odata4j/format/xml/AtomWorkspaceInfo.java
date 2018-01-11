/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.format.xml;

import java.util.List;

import org.core4j.Enumerable;
import org.core4j.Func1;

public class AtomWorkspaceInfo {

  public static final Func1<AtomWorkspaceInfo, Enumerable<AtomCollectionInfo>> GET_COLLECTIONS =
      new Func1<AtomWorkspaceInfo, Enumerable<AtomCollectionInfo>>() {
        public Enumerable<AtomCollectionInfo> apply(AtomWorkspaceInfo workspace) {
          return Enumerable.create(workspace.getCollections());
        }
      };

  private final String title;
  private final List<AtomCollectionInfo> collections;

  AtomWorkspaceInfo(String title, List<AtomCollectionInfo> collections) {
    this.title = title;
    this.collections = collections;
  }

  public String getTitle() {
    return title;
  }

  public List<AtomCollectionInfo> getCollections() {
    return collections;
  }

}

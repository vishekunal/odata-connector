/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.odata4j.consumer.behaviors;

import org.odata4j.consumer.ODataClientRequest;

public class OldStylePagingBehavior implements OClientBehavior {

  private final int startPage;
  private final int itemsPerPage;

  public OldStylePagingBehavior() {
    this(10);
  }

  public OldStylePagingBehavior(int itemsPerPage) {
    this(itemsPerPage, 1);
  }

  public OldStylePagingBehavior(int itemsPerPage, int startPage) {
    this.itemsPerPage = itemsPerPage;
    this.startPage = startPage;
  }

  @Override
  public ODataClientRequest transform(ODataClientRequest request) {
    if (request.getQueryParams().containsKey("$page"))
      return request;
    return request.queryParam("$page", Integer.toString(startPage)).queryParam("$itemsPerPage", Integer.toString(itemsPerPage));
  }

}
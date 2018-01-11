/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.consumer.behaviors;

import org.mule.odata4j.consumer.ODataClientRequest;

public class DallasCtp2AuthenticationBehavior implements OClientBehavior {

  private final String accountKey;
  private final String uniqueUserId;

  public DallasCtp2AuthenticationBehavior(String accountKey, String uniqueUserId) {
    this.accountKey = accountKey;
    this.uniqueUserId = uniqueUserId;
  }

  @Override
  public ODataClientRequest transform(ODataClientRequest request) {
    return request.header("$uniqueUserID", uniqueUserId).header("$accountKey", accountKey).header("DataServiceVersion", "2.0").queryParam("$format", "atom10");

  }

}
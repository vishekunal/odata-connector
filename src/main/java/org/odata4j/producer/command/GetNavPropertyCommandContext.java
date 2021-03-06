/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.odata4j.producer.command;

import org.odata4j.core.OEntityKey;
import org.odata4j.producer.BaseResponse;
import org.odata4j.producer.QueryInfo;

public interface GetNavPropertyCommandContext extends ProducerCommandContext<BaseResponse> {

  String getEntitySetName();

  OEntityKey getEntityKey();

  String getNavProp();

  QueryInfo getQueryInfo();

}

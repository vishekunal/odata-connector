/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.producer.command;

import org.mule.odata4j.producer.CountResponse;
import org.mule.odata4j.producer.QueryInfo;

public interface GetEntitiesCountCommandContext extends ProducerCommandContext<CountResponse> {

  String getEntitySetName();

  QueryInfo getQueryInfo();

}

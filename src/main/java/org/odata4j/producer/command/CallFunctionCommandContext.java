/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.producer.command;

import java.util.Map;

import org.odata4j.core.OFunctionParameter;
import org.odata4j.edm.EdmFunctionImport;
import org.odata4j.producer.BaseResponse;
import org.odata4j.producer.QueryInfo;

public interface CallFunctionCommandContext extends ProducerCommandContext<BaseResponse> {

  EdmFunctionImport getName();

  Map<String, OFunctionParameter> getParams();

  QueryInfo getQueryInfo();

}

/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.core;

import java.util.List;

import org.odata4j.format.FormatType;
import org.odata4j.producer.resources.BatchBodyPart;

import com.sun.jersey.api.client.ClientResponse;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public interface OBatchRequest {

	public ClientResponse execute(List<BatchBodyPart> parts, FormatType formatType);
	
}

/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.odata.factory;

import org.odata4j.consumer.ODataConsumer;
import org.odata4j.format.FormatType;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public interface ODataConsumerFactory {
	
	public ODataConsumer newConsumer(String baseServiceUri, FormatType formatType, String username, String password);

}

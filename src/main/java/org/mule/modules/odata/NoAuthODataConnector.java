/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.odata;

import org.mule.api.annotations.Connector;
import org.mule.api.annotations.lifecycle.Start;

/**
 * Mule Cloud Connector that provides the basic operations to consume
 * an OData service on a service that doesn't require authentication
 *  
 * @author mariano.gonzalez@mulesoft.com
 */
@Connector(name = "odata", schemaVersion = "1.0", friendlyName = "OData Connector without authentication", minMuleVersion = "3.3", configElementName="config-noauth")
public class NoAuthODataConnector extends BaseODataConnector {

	
	/**
	 * This method initializes the module by creating the consumer and the factory (if needed)
	 */
	@Start
	public void init() {
		this.setConsumer(this.getConsumerFactory().newConsumer(this.getBaseServiceUri(), this.getFormatType(), null, null, this.getConsumerVersion()));
	}
	
}

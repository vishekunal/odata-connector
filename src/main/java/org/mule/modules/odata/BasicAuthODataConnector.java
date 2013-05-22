/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.odata;

import org.mule.api.ConnectionException;
import org.mule.api.annotations.Connect;
import org.mule.api.annotations.ConnectionIdentifier;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Disconnect;
import org.mule.api.annotations.MetaDataSwitch;
import org.mule.api.annotations.ValidateConnection;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.param.ConnectionKey;

/**
 * Connector for consuming OData feeds by performing read, create, update and delete operations.
 * Bath operations are also supported.
 * 
 * This version of the connector users basic authentication  
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
@Connector(name = "odata", schemaVersion = "1.0", friendlyName = "OData (Basic Auth)", minMuleVersion = "3.4", configElementName="config-with-basic-auth", metaData=MetaDataSwitch.OFF)
public class BasicAuthODataConnector extends BaseODataConnector {

	private String user;
	
	/**
	 * 
	 * @param username the authorization username
	 * @param password the authorization password
	 * @param serviceURI the URI of the target OData Service
	 * @throws ConnectionException
	 */
	@Connect
	public void connect(@ConnectionKey String username, @Password String password, String serviceURI) throws ConnectionException {
		this.setConsumer(this.getConsumerFactory().newConsumer(serviceURI, this.getFormatType(), username, password, this.getConsumerVersion()));
		this.setBaseServiceUri(serviceURI);
		this.user = username;
	}
	
	@ConnectionIdentifier
	public String getConnectionIdentifier() {
		return String.format("%s@%s", this.user, this.getBaseServiceUri());
	}
	
	@ValidateConnection
	public boolean isConnected() {
		return this.getConsumer() != null;
	}
	
	@Disconnect
	public void disconnect() {
		this.setConsumer(null);
		this.setBaseServiceUri(null);
		this.user = null;
	}
	
}

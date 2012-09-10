/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.odata;

import java.util.Date;

import junit.framework.TestCase;

import org.odata4j.core.ODataVersion;
import org.odata4j.format.FormatType;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class BaseODataConnectorTest extends TestCase {
	
	private ODataConnector connector;
	private TestSyncTask task;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.connector = new ODataConnector();
		this.connector.setBaseServiceUri("https://muleqa.aplicor.net/a7/OData.svc/");
		this.connector.setFormatType(FormatType.JSON);
		this.connector.setNamingFormat(PropertyNamingFormat.UPPER_CAMEL_CASE);
		this.connector.setConsumerVersion(ODataVersion.V2);
		this.connector.connect("4264DAA0-A5C7-4BB9-AE40-6B4AFABE64F4:Mulesoft", "Mulesoft11", null);
		
		this.task = new TestSyncTask();
		
		task.setEndTime(new Date());
		task.setDeleted(false);
		task.setGoogleId("");
		task.setNotes("Mulesoft|a note");
		task.setLastUpdated(new Date());
		task.setTitle("test task");
	}
	
	public void testBatch() {
		System.out.println(this.connector.batch(this.task, "SyncTaskSet"));
	}
	
	

}

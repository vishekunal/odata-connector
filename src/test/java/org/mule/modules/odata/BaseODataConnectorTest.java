/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.odata;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mule.api.MuleMessage;
import org.mule.api.NestedProcessor;
import org.odata4j.core.ODataVersion;
import org.odata4j.format.FormatType;
import org.odata4j.producer.resources.BatchBodyPart;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class BaseODataConnectorTest extends TestCase {
	
	private ODataConnector connector;
	private TestSyncTask task;
	private List<BatchBodyPart> batchParts;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.connector = new ODataConnector();
		this.connector.setBaseServiceUri("https://muleqa.aplicor.net/a7/OData.svc/");
		this.connector.setFormatType(FormatType.JSON);
		this.connector.setNamingFormat(PropertyNamingFormat.UPPER_CAMEL_CASE);
		this.connector.setConsumerVersion(ODataVersion.V2);
		this.connector.connect("4264DAA0-A5C7-4BB9-AE40-6B4AFABE64F4:Mulesoft", "Mulesoft11", null);
		
		this.batchParts = null;
		
		this.task = new TestSyncTask();
		
		task.setEndTime(new Date());
		task.setDeleted(false);
		task.setGoogleId("");
		task.setNotes("Mulesoft|a note");
		task.setLastUpdated(new Date());
		task.setTitle("test task");
	}
	
	public void testBatch() {
		final MuleMessage message = Mockito.mock(MuleMessage.class);
		
		NestedProcessor proc = new NestedProcessor() {
			
			@Override
			public Object processWithExtraProperties(Map<String, Object> arg0) throws Exception {
				return null;
			}
			
			@Override
			public Object process(Object arg0, Map<String, Object> arg1) throws Exception {
				return null;
			}
			
			@Override
			public Object process(Object arg0) throws Exception {
				return null;
			}
			
			@Override
			public Object process() throws Exception {
				return connector.createEntity(message, task, "SyncTaskSet");
			}
		};
		
		Mockito.when(message.getInvocationProperty(Mockito.eq("ODATA_CONNECTOR_BATCH_BODY_PARTS"))).thenAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return batchParts;
			}
		});
		
		Mockito.doAnswer(new Answer<Object>() {
			
			@Override
			@SuppressWarnings("unchecked")
			public Object answer(InvocationOnMock invocation) throws Throwable {
				batchParts = (List<BatchBodyPart>) invocation.getArguments()[1];
				return null;
			}
			
		}).when(message).setInvocationProperty(Mockito.eq("ODATA_CONNECTOR_BATCH_BODY_PARTS"), Mockito.any());
	
		this.connector.batch(message, Arrays.asList(proc));
	}
	
	

}

/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.odata.tests;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.construct.Flow;
import org.mule.tck.junit4.FunctionalTestCase;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class GetEntitiesTest extends FunctionalTestCase {

	@Override
	protected String getConfigResources() {
		return "mule-config.xml";
	}
	
	@Test
	public void testGetEntities() throws Exception {
		Object payload = this.runFlow("test-get-entities").getMessage().getPayload();
		
		Assert.assertTrue(payload instanceof Collection);
		
		@SuppressWarnings("unchecked")
		Collection<Object> buildings = (Collection<Object>) payload;
		
		Assert.assertFalse("No results obtained", buildings.isEmpty());
		
		for (Object record : buildings) {
			Assert.assertTrue("Expected type was CityBuilding", record instanceof CityBuilding);
			CityBuilding building = (CityBuilding) record;
			
			Assert.assertFalse(StringUtils.isBlank(building.getName()));
			Assert.assertFalse(StringUtils.isBlank(building.getAddress()));
		}
	}
	
	
	protected <T> MuleEvent runFlow(String flowName) throws Exception {
		return this.runFlow(flowName, null);
	}
	
	protected <T> MuleEvent runFlow(String flowName, Object payload) throws Exception {
    	Flow flow = lookupFlowConstruct(flowName);
    	return flow.process(getTestEvent(payload));
    }
	
	/**
    * Run the flow specified by name and assert equality on the expected output
    *
    * @param flowName The name of the flow to run
    * @param expect The expected output
    */
    protected <T> void runFlowAndExpect(String flowName, T expect) throws Exception {
        Assert.assertEquals(expect, this.runFlow(flowName).getMessage().getPayload());
    }

    /**
    * Run the flow specified by name using the specified payload and assert
    * equality on the expected output
    *
    * @param flowName The name of the flow to run
    * @param expect The expected output
    * @param payload The payload of the input event
    */
    protected <T, U> void runFlowWithPayloadAndExpect(String flowName, T expect, U payload) throws Exception {
        Assert.assertEquals(expect, this.runFlow(flowName).getMessage().getPayload());
    }

    /**
     * Retrieve a flow by name from the registry
     *
     * @param name Name of the flow to retrieve
     */
    protected Flow lookupFlowConstruct(String name) {
        return (Flow) muleContext.getRegistry().lookupFlowConstruct(name);
    }

}

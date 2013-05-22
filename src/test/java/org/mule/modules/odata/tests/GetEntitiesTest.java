/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.odata.tests;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.odata.ODataConnector;
import org.mule.modules.odata.PropertyNamingFormat;
import org.odata4j.core.ODataVersion;
import org.odata4j.format.FormatType;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class GetEntitiesTest {

	private ODataConnector connector;
	
	@Before
	public void setUp() throws Exception {
		this.connector = new ODataConnector();
		this.connector.setFormatType(FormatType.ATOM);
		this.connector.setNamingFormat(PropertyNamingFormat.LOWER_CAMEL_CASE);
		this.connector.setConsumerVersion(ODataVersion.V1);
		this.connector.connect("http://datafeed.medicinehat.ca/v1/data/");
	};
	
	
	@Test
	public void testGetEntities() {
		List<Object> buildings = this.connector.getEntities("org.mule.modules.odata.tests.CityBuilding", "CityBuildings", null , null, null, null, null, null);
		
		Assert.assertFalse("No results obtained", buildings.isEmpty());
		
		for (Object record : buildings) {
			Assert.assertTrue("Expected type was People", record instanceof CityBuilding);
			CityBuilding building = (CityBuilding) record;
			
			Assert.assertFalse(StringUtils.isBlank(building.getName()));
			Assert.assertFalse(StringUtils.isBlank(building.getAddress()));
		}
	}
	
}

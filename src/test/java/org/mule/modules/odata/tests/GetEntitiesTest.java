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

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.mule.modules.odata.ODataConnector;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class GetEntitiesTest extends TestCase {

	private ODataConnector connector;
	
	protected void setUp() throws Exception {
		this.connector = new ODataConnector();
		this.connector.setBaseServiceUri("http://odata.netflix.com/Catalog/");
		this.connector.init();
	};
	
	
	@Test
	public void testGetEntities() {
		List<Object> sheens = this.connector.getEntities("org.mule.modules.odata.tests.People", "People", "substringof('Sheen',Name)", null, null, null, null, null);
		
		assertFalse("No results obtained", sheens.isEmpty());
		assertTrue("Expected type was People", sheens.get(0) instanceof People);
		
		People p = (People) sheens.get(0);
		
		assertNotNull(p.getId());
		assertFalse(StringUtils.isEmpty(p.getName()));
	}
	
}

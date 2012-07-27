/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.odata;

import org.springframework.util.StringUtils;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public enum PropertyNamingFormat {

	UPPER_CAMEL_CASE {
	
		@Override
		public String toJava(String name) {
			return StringUtils.uncapitalize(name);
		}
		
		@Override
		public String toOData(String name) {
			return StringUtils.capitalize(name);
		}
		
	},
	
	LOWER_CAMEL_CASE {
		
		@Override
		public String toJava(String name) {
			return name;
		}
		
		@Override
		public String toOData(String name) {
			return name;
		}
	};
	
	public abstract String toJava(String name);
	
	public abstract String toOData(String name);
	
	
}

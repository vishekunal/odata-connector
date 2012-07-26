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

/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.odata.reflection;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.mule.modules.odata.annotation.Guid;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class FieldDescriptor {

	private Field field;
	private PropertyDescriptor property;
	private boolean isGuid;
	
	
	public FieldDescriptor(Field field, PropertyDescriptor property) {
		this.field = field;
		this.property = property;
		this.isGuid = this.field.getAnnotation(Guid.class) != null;
	}
	
	public Field getField() {
		return field;
	}
	public PropertyDescriptor getProperty() {
		return property;
	}
	
	public Object getValue(Object target) throws InvocationTargetException, IllegalAccessException {
		return this.property.getReadMethod().invoke(target, (Object[]) null);
	}
	
	public String getName() {
		return this.field.getName();
	}
	
	public boolean isGuid() {
		return isGuid;
	}
	
}

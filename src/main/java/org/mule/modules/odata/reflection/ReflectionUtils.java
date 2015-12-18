/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.odata.reflection;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class ReflectionUtils {

	
	public static Collection<FieldDescriptor> getFieldDescriptors(Object object) {
		Class<?> clazz = object.getClass();
		Map<String, PropertyDescriptor> properties = getProperties(clazz);
		Collection<FieldDescriptor> result = new ArrayList<FieldDescriptor>(properties.size());
		
		for (Field field : getFields(clazz)) {
			PropertyDescriptor property = properties.get(field.getName());
			if (property != null && property.getReadMethod() != null && property.getWriteMethod() != null) {
				result.add(new FieldDescriptor(field, property));
			}
		}
		
		return result;
	}
	
	public static <T> Map<String, PropertyDescriptor> getProperties(Class<T> clazz) {
		BeanInfo info = null;
		try {
			info = Introspector.getBeanInfo(clazz);
		} catch (IntrospectionException e) {
			throw new RuntimeException();
		}
		
		Map<String, PropertyDescriptor> descriptors = new HashMap<String, PropertyDescriptor>(info.getPropertyDescriptors().length);
		for (PropertyDescriptor property : info.getPropertyDescriptors()) {
			if (property.getReadMethod() != null && property.getWriteMethod() != null) {
				descriptors.put(property.getName(), property);
			}
		}
		
		return descriptors;
	}
	
	public static Collection<Field> getFields(Class<?> clazz) {
		Collection<Field> fields = new ArrayList<Field>();
		
		Class<?> targetClass = clazz;
		
		while (targetClass != Object.class) {
			fields.addAll(Arrays.asList(targetClass.getDeclaredFields()));
			targetClass = targetClass.getSuperclass();
		}
		
		return fields;
	}
	
}

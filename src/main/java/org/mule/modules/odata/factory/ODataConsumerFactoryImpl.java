/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.odata.factory;

import org.apache.commons.lang.StringUtils;
import org.mule.odata4j.consumer.ODataConsumer;
import org.mule.odata4j.consumer.behaviors.OClientBehavior;
import org.mule.odata4j.consumer.behaviors.OClientBehaviors;
import org.mule.odata4j.core.ODataVersion;
import org.mule.odata4j.format.FormatType;
import org.mule.odata4j.jersey.consumer.ODataJerseyConsumer;
import org.mule.odata4j.jersey.consumer.ODataJerseyConsumer.Builder;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class ODataConsumerFactoryImpl implements ODataConsumerFactory {

	/**
	 * @see org.mule.modules.odata.factory.ODataConsumerFactory#newConsumer(String, FormatType, String, String, ODataVersion)
	 */
	public ODataConsumer newConsumer(String baseServiceUri, FormatType formatType, String username, String password, ODataVersion version) {
		OClientBehavior auth = this.getAuthBehaviour(username, password);
		Builder builder = ODataJerseyConsumer.newBuilder(baseServiceUri, version).setFormatType(formatType);
		
		if (auth != null) {
			builder.setClientBehaviors(auth);
		}
		
		return builder.build();
	}
	
	private OClientBehavior getAuthBehaviour(String user, String password) {
		boolean hasUser = !StringUtils.isBlank(user);
		boolean hasPassword = !StringUtils.isBlank(password);
		
		if (hasUser && hasPassword) {
			return OClientBehaviors.basicAuth(user, password);
		} else if (hasUser || hasPassword) {
			throw new IllegalStateException("You need to provide both user and password for OData authentication");
		}
		
		return null;
	}
	
}

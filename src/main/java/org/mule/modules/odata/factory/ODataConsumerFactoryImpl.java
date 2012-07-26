package org.mule.modules.odata.factory;

import org.apache.commons.lang.StringUtils;
import org.odata4j.consumer.ODataConsumer;
import org.odata4j.consumer.behaviors.OClientBehavior;
import org.odata4j.consumer.behaviors.OClientBehaviors;
import org.odata4j.format.FormatType;
import org.odata4j.jersey.consumer.ODataJerseyConsumer;
import org.odata4j.jersey.consumer.ODataJerseyConsumer.Builder;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class ODataConsumerFactoryImpl implements ODataConsumerFactory {

	/**
	 * @see org.mule.modules.odata.factory.ODataConsumerFactory#newConsumer(java.lang.String, org.odata4j.format.FormatType, java.lang.String, java.lang.String)
	 */
	@Override
	public ODataConsumer newConsumer(String baseServiceUri, FormatType formatType, String username, String password) {
		Builder builder = ODataJerseyConsumer.newBuilder(baseServiceUri).setFormatType(formatType);
		OClientBehavior auth = this.getAuthBehaviour(username, password);
		
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

package org.mule.modules.odata.factory;

import org.odata4j.consumer.ODataConsumer;
import org.odata4j.format.FormatType;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public interface ODataConsumerFactory {
	
	public ODataConsumer newConsumer(String baseServiceUri, FormatType formatType, String username, String password);

}

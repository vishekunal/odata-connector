/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.odata.odata4j.extensions;

import java.util.List;

import org.mule.odata4j.format.FormatType;
import org.mule.odata4j.producer.resources.BatchBodyPart;
import org.mule.odata4j.producer.resources.BatchResult;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public interface OBatchRequest {

	public BatchResult execute(List<BatchBodyPart> parts, FormatType formatType);
	
}

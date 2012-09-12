/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.odata4j.jersey.consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.odata4j.consumer.ODataClientRequest;
import org.odata4j.core.Guid;
import org.odata4j.core.OBatchRequest;
import org.odata4j.core.ODataConstants;
import org.odata4j.core.ODataVersion;
import org.odata4j.edm.EdmDataServices;
import org.odata4j.format.Entry;
import org.odata4j.format.FormatParser;
import org.odata4j.format.FormatParserFactory;
import org.odata4j.format.FormatType;
import org.odata4j.format.Settings;
import org.odata4j.internal.InternalUtil;
import org.odata4j.producer.resources.BatchBodyPart;
import org.odata4j.producer.resources.ODataBatchProvider.HTTP_METHOD;

import com.sun.jersey.api.client.ClientResponse;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class ConsumerBatchRequest implements OBatchRequest {
	
	private final String baseUrl;
	private final ODataJerseyClient client;
	private EdmDataServices metadata;
	
	
	public ConsumerBatchRequest(ODataJerseyClient client, String baseUrl, EdmDataServices metadata) {
		this.baseUrl = baseUrl;
		this.client = client;
		this.metadata = metadata;
	}
	
	@Override
	public ClientResponse execute(List<BatchBodyPart> parts, FormatType formatType) {
		String batchId = Guid.randomGuid().toString();
		String changeSetId = Guid.randomGuid().toString();
		
		StringBuilder entity = new StringBuilder();
		int i = 0;
				
		for (BatchBodyPart part : parts) {
			entity.append("\n--batch_").append(batchId);
			
			if (part.getHttpMethod() != HTTP_METHOD.GET) {
				
				entity.append("\nContent-Type: multipart/mixed; boundary=changeset_").append(changeSetId)
						.append("\nContent-Length: ").append(part.getEntity().length())
						.append("\n\n--changeset(").append(changeSetId).append(")");
			}
			
			entity.append("\nContent-Type: application/http")
						.append("\nContent-Transfer-Encoding: binary\n\n")
						.append(part.getHttpMethod().name()).append(" ").append(part.getUri()).append(" HTTP/1.1")
						.append("\nContent-ID: ").append(++i)
						.append("\nContent-Type: ").append(formatType == FormatType.ATOM ? MediaType.APPLICATION_ATOM_XML : MediaType.APPLICATION_JSON)
						.append("\nContent-Lenght: ").append(part.getEntity().length())
						.append("\n").append(part.getEntity());
			}
		
		entity.append("\n\n--changeset(").append(changeSetId).append(")--")
				.append("\n--batch(").append(batchId).append(")--");
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "multipart/mixed; boundary=batch_" + batchId);
		
		System.out.println(entity.toString());
		
		ODataClientRequest request = new ODataClientRequest("POST", this.baseUrl + "$batch", headers, null, entity.toString());
		ClientResponse response = this.client.batch(request);
		
		ODataVersion version = InternalUtil.getDataServiceVersion(response.getHeaders().getFirst(ODataConstants.Headers.DATA_SERVICE_VERSION));

	    FormatParser<Entry> parser = FormatParserFactory.getParser(Entry.class, client.getFormatType(), new Settings(version, metadata, null, null, null));
	    
	    Entry entry = parser.parse(client.getFeedReader(response));
		entry.toString();
	    return response;
	}
	

}

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.MediaType;

import org.odata4j.consumer.ODataClientRequest;
import org.odata4j.core.Guid;
import org.odata4j.core.OBatchRequest;
import org.odata4j.format.FormatType;
import org.odata4j.producer.resources.BatchBodyPart;
import org.odata4j.producer.resources.BatchPartResponse;
import org.odata4j.producer.resources.BatchResult;
import org.odata4j.producer.resources.ODataBatchProvider.HTTP_METHOD;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class ConsumerBatchRequest implements OBatchRequest {
	
	private static final Pattern STATUS_CODE_PATTERN = Pattern.compile("HTTP/1.1 ([\\d]*) [\\w]*");
	private final String baseUrl;
	private final ODataJerseyClient client;
	
	
	public ConsumerBatchRequest(ODataJerseyClient client, String baseUrl) {
		this.baseUrl = baseUrl;
		this.client = client;
	}
	
	@Override
	public BatchResult execute(List<BatchBodyPart> parts, FormatType formatType) {
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
		
		
		ODataClientRequest request = new ODataClientRequest("POST", this.baseUrl + "$batch", headers, null, entity.toString());

		String response = this.client.batch(request).getEntity(String.class);
		
		return this.parseResponse(response, batchId, changeSetId);
	}
	
	
	private BatchResult parseResponse(String response, String batchId, String changeSetId) {
		String[] lines = response.split("\n");
		BatchResult result = new BatchResult();
		result.setBatchId(batchId);
		result.setChangesetId(changeSetId);
		
		int top = lines.length;
		
		for (int i = 0; i < top; i++) {
			String line = lines[i];
			if (lines[i].startsWith("HTTP/1.1")) {
				BatchPartResponse part = new BatchPartResponse();
				part.setStatus(this.getStatusCode(line));

				StringBuilder message = new StringBuilder();
				
				for (; i < top; i++) {
					line = lines[i];
					if (line.startsWith("--batch")) {
						break;
					}
					message.append(line).append("\n");
				}
				part.setMessage(message.toString());
				result.addPartResponse(part);
			}
		}
		
		return result;
	}
	
	private int getStatusCode(String line) {
		Matcher matcher = STATUS_CODE_PATTERN.matcher(line);
		if (matcher.find() && matcher.groupCount() > 1) {
			return Integer.valueOf(matcher.group(1));
		}
		
		throw new IllegalArgumentException(String.format("Could not extract status code from %s", line));
	}
	

}

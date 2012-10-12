/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.odata.odata4j.extensions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.odata4j.consumer.ODataClientRequest;
import org.odata4j.core.Guid;
import org.odata4j.format.FormatType;
import org.odata4j.jersey.consumer.ODataJerseyClient;
import org.odata4j.producer.resources.BatchBodyPart;
import org.odata4j.producer.resources.BatchPartResponse;
import org.odata4j.producer.resources.BatchResult;

import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.MultiPart;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class ConsumerBatchRequest implements OBatchRequest {
	
	private static final Logger logger = Logger.getLogger(ConsumerBatchRequest.class);
	private static final Pattern STATUS_CODE_PATTERN = Pattern.compile("HTTP/1.1 ([\\d]*) [\\w]*");
	private static final String NEW_LINE = "\r\n";
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
		
		MultiPart multiPart = new MultiPart();
		BodyPart changeSetBodyPart = new BodyPart();
		multiPart.bodyPart(changeSetBodyPart);
		
		changeSetBodyPart.type(this.mediaType("multipart", "mixed", "changeset_" + changeSetId));
		changeSetBodyPart.getHeaders().putSingle("Content-Transfer-Encoding", "binary");
		changeSetBodyPart.setEntity(StringUtils.EMPTY);
		
		for (BatchBodyPart batchPart : parts) {
			BodyPart bodyPart = new BodyPart();
			multiPart.bodyPart(bodyPart);
			
			StringBuilder entityBuilder = new StringBuilder()
				.append(batchPart.getHttpMethod().name()).append(" ").append(batchPart.getUri()).append(" HTTP/1.1")
				.append(NEW_LINE).append("Content-Type: ").append(formatType == FormatType.ATOM ? MediaType.APPLICATION_ATOM_XML : MediaType.APPLICATION_JSON)
				.append(NEW_LINE).append("Content-Lenght: ").append(batchPart.getEntity().length())
				.append(NEW_LINE).append(NEW_LINE).append(batchPart.getEntity());
			
			String entity = entityBuilder.toString();
			bodyPart.setEntity(entity);
			
			bodyPart.getHeaders().putSingle("Content-Lenght", String.valueOf(entity.length()));
			bodyPart.getHeaders().putSingle("Content-Transfer-Encoding", "binary");
			bodyPart.type(new MediaType("application", "http"));
		}
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "multipart/mixed; boundary=batch_" + batchId);
		
		ODataClientRequest request = new ODataClientRequest("POST", this.baseUrl + "$batch", headers, null, multiPart);
		
		try {
			String response = this.client.batch(request).getEntity(String.class);
			return this.parseResponse(response, batchId, changeSetId);
		} finally {
			try {
				multiPart.close();
			} catch (IOException e) {
				logger.warn("exception caught closing Multipart: " + e.getMessage());
			}
		}
	}
	
	private MediaType mediaType(String type, String subType, String boundary) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("boundary", boundary);

		return new MediaType(type, subType, parameters);
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
		if (matcher.find() && matcher.groupCount() > 0) {
			return Integer.valueOf(matcher.group(1));
		}
		
		throw new IllegalArgumentException(String.format("Could not extract status code from %s", line));
	}
	

}

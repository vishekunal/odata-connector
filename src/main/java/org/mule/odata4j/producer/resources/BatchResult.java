/**
 * (c) 2003-2015 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */

package org.mule.odata4j.producer.resources;

import java.util.ArrayList;
import java.util.List;

/**
 * This object groups the responses for each
 * operation in a batch.
 * 
 * Per OData's spec, the order of the
 * responses matches the order of the operations in
 * the batch request
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class BatchResult {
	
	private String batchId;
	private String changesetId;
	private List<BatchPartResponse> partResponses = new ArrayList<BatchPartResponse>();
	
	public BatchResult addPartResponse(BatchPartResponse part) {
		this.partResponses.add(part);
		return this;
	}
	
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public List<BatchPartResponse> getPartResponses() {
		return partResponses;
	}
	public void setPartResponses(List<BatchPartResponse> partResponses) {
		this.partResponses = partResponses;
	}
	public String getChangesetId() {
		return changesetId;
	}

	public void setChangesetId(String changesetId) {
		this.changesetId = changesetId;
	}

}

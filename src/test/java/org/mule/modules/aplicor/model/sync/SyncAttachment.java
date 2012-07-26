/**
 *
 * (c) 2003-2012 MuleSoft, Inc. This software is protected under international
 * copyright law. All use of this software is subject to MuleSoft's Master
 * Subscription Agreement (or other Terms of Service) separately entered
 * into between you and MuleSoft. If such an agreement is not in
 * place, you may not use the software.
 */

package org.mule.modules.aplicor.model.sync;

import java.util.Date;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class SyncAttachment extends BaseSyncObject {

	private String syncAttachmentId;
	private String displayName;
	private String filename;
	private String type;
	private double size = 0;
	private Date lastEditedDate;
	
	public String getSyncAttachmentId() {
		return syncAttachmentId;
	}
	public void setSyncAttachmentId(String syncAttachmentId) {
		this.syncAttachmentId = syncAttachmentId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	public Date getLastEditedDate() {
		return lastEditedDate;
	}
	public void setLastEditedDate(Date lastEditedDate) {
		this.lastEditedDate = lastEditedDate;
	}
}

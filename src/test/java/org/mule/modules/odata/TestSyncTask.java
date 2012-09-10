/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.odata;

import java.util.Date;

import org.odata4j.core.Guid;

public class TestSyncTask {
	
	private String syncTaskId;
	private String title;
	private Date lastUpdated;
	private String parent;
	private String notes;
	private String status;
	private Date endTime;
	private boolean deleted;
	private Guid externalId;
	private String googleId;

	public Guid getExternalId() {
		return externalId;
	}
	public void setExternalId(Guid externalId) {
		this.externalId = externalId;
	}
	public String getGoogleId() {
		return googleId;
	}
	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}
	
	public String getSyncTaskId() {
		return syncTaskId;
	}
	public void setSyncTaskId(String syncTaskId) {
		this.syncTaskId = syncTaskId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date completedDate) {
		this.endTime = completedDate;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}

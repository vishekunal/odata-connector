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
import java.util.List;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class SyncEvent extends BaseSyncObject {

	private String syncAppointmentId;
	private String subject;
	private String body;
	private Date startTime;
	private Date endTime;
	private Date addedDate;
	private List<SyncContact> syncContacts;
	private boolean deleted;
	private Date lastEditedDate;
	
	public String getSyncAppointmentId() {
		return syncAppointmentId;
	}
	public void setSyncAppointmentId(String syncAppointmentId) {
		this.syncAppointmentId = syncAppointmentId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}
	public List<SyncContact> getSyncContacts() {
		return syncContacts;
	}
	public void setSyncContacts(List<SyncContact> syncContacts) {
		this.syncContacts = syncContacts;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public Date getLastEditedDate() {
		return lastEditedDate;
	}
	public void setLastEditedDate(Date lastEditedDate) {
		this.lastEditedDate = lastEditedDate;
	}
}

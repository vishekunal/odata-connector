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
public class SyncRequest {
	
	/**
	 * This field is useless. We're forced ot have it due to limitations
	 * on Aplicor's end.
	 * If <code>null</code> then it means that this instance carries the information.
	 * If not <code>null</code> then it means that this instance is empty and the actualstate is in d
	 */
	private List<SyncRequest> d;
	
	private String syncRequestId;
	private String email;
	private String password;
	private String syncType;
	private String groupName;
	private Date lastEditedDate;
	private String emailLabel;
	private List<SyncContact> syncNewContacts;
	private List<SyncContact> syncUpdatedContacts;
	private List<SyncContact> syncDeletedContacts;
	
	private List<SyncEvent> syncNewAppointments;
	private List<SyncEvent> syncUpdatedAppointments;
	private List<SyncEvent> syncDeletedAppointments;
	
	private List<SyncTask> syncNewTasks;
	private List<SyncTask> syncUpdatedTasks;
	private List<SyncTask> syncDeletedTasks;
	
	public String getSyncRequestId() {
		return syncRequestId;
	}
	public void setSyncRequestId(String syncRequestId) {
		this.syncRequestId = syncRequestId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSyncType() {
		return syncType;
	}
	public void setSyncType(String syncType) {
		this.syncType = syncType;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<SyncEvent> getSyncNewAppointments() {
		return syncNewAppointments;
	}
	public void setSyncNewAppointments(List<SyncEvent> syncNewAppointments) {
		this.syncNewAppointments = syncNewAppointments;
	}
	public List<SyncEvent> getSyncUpdatedAppointments() {
		return syncUpdatedAppointments;
	}
	public void setSyncUpdatedAppointments(List<SyncEvent> syncUpdatedAppointments) {
		this.syncUpdatedAppointments = syncUpdatedAppointments;
	}
	public List<SyncEvent> getSyncDeletedAppointments() {
		return syncDeletedAppointments;
	}
	public void setSyncDeletedAppointments(List<SyncEvent> syncDeletedAppointments) {
		this.syncDeletedAppointments = syncDeletedAppointments;
	}
	public List<SyncTask> getSyncNewTasks() {
		return syncNewTasks;
	}
	public void setSyncNewTasks(List<SyncTask> syncNewTasks) {
		this.syncNewTasks = syncNewTasks;
	}
	public List<SyncTask> getSyncUpdatedTasks() {
		return syncUpdatedTasks;
	}
	public void setSyncUpdatedTasks(List<SyncTask> syncUpdatedTasks) {
		this.syncUpdatedTasks = syncUpdatedTasks;
	}
	public List<SyncTask> getSyncDeletedTasks() {
		return syncDeletedTasks;
	}
	public void setSyncDeletedTasks(List<SyncTask> syncDeletedTasks) {
		this.syncDeletedTasks = syncDeletedTasks;
	}
	public List<SyncContact> getSyncNewContacts() {
		return syncNewContacts;
	}
	public void setSyncNewContacts(List<SyncContact> syncNewContacts) {
		this.syncNewContacts = syncNewContacts;
	}
	public List<SyncContact> getSyncUpdatedContacts() {
		return syncUpdatedContacts;
	}
	public void setSyncUpdatedContacts(List<SyncContact> syncUpdatedContacts) {
		this.syncUpdatedContacts = syncUpdatedContacts;
	}
	public List<SyncContact> getSyncDeletedContacts() {
		return syncDeletedContacts;
	}
	public void setSyncDeletedContacts(List<SyncContact> syncDeletedContacts) {
		this.syncDeletedContacts = syncDeletedContacts;
	}
	public Date getLastEditedDate() {
		return lastEditedDate;
	}
	public void setLastEditedDate(Date lastEditedDate) {
		this.lastEditedDate = lastEditedDate;
	}
	public List<SyncRequest> getD() {
		return d;
	}
	public void setD(List<SyncRequest> d) {
		this.d = d;
	}
	public String getEmailLabel() {
		return emailLabel;
	}
	public void setEmailLabel(String emailLabel) {
		this.emailLabel = emailLabel;
	}
	
	
}

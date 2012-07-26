/**
 *
 * (c) 2003-2012 MuleSoft, Inc. This software is protected under international
 * copyright law. All use of this software is subject to MuleSoft's Master
 * Subscription Agreement (or other Terms of Service) separately entered
 * into between you and MuleSoft. If such an agreement is not in
 * place, you may not use the software.
 */

package org.mule.modules.aplicor.model.sync;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class SyncEmail extends BaseSyncObject {

	private List<SyncAttachment> syncAttachments;
	
	private String syncEmailId;
	private String from;
	private String to;
	private String cc;
	private String bcc;
	private String body;
	private String subject;
	private String contacts;
	private Date lastEditedDate;
	private String emailContext;
	
	public SyncEmail addAttachment(SyncAttachment attach) {
		if (this.syncAttachments == null) {
			this.syncAttachments = new ArrayList<SyncAttachment>();
		}
		this.syncAttachments.add(attach);
		
		return this;
	}
	
	public List<SyncAttachment> getSyncAttachments() {
		return syncAttachments;
	}
	public void setSyncAttachments(List<SyncAttachment> syncAttachments) {
		this.syncAttachments = syncAttachments;
	}
	public String getSyncEmailId() {
		return syncEmailId;
	}
	public void setSyncEmailId(String syncEmailId) {
		this.syncEmailId = syncEmailId;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cC) {
		this.cc = cC;
	}
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public Date getLastEditedDate() {
		return lastEditedDate;
	}
	public void setLastEditedDate(Date lastEditedDate) {
		this.lastEditedDate = lastEditedDate;
	}
	public String getEmailContext() {
		return emailContext;
	}
	public void setEmailContext(String emailContext) {
		this.emailContext = emailContext;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}

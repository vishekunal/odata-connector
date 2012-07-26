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

import org.apache.commons.lang.StringUtils;
import org.odata4j.core.Guid;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class SyncContact extends BaseSyncObject {

	private Guid syncContactId;
	private String firstName;
	private String lastName;
	private String middleName;
	private String title;
	private String salutation;
	private String emailAddress;
	private String businessPhone;
	private String homePhone;
	private String personalPhone;
	private String company;
	private Date lastEditedDate;
	private List<SyncAddress> address;
	
//	public String getFullname() {
//		return String.format("%s%s%s",
//				StringUtils.isBlank(this.firstName) ? StringUtils.EMPTY : this.firstName,
//				StringUtils.isBlank(this.middleName) ? StringUtils.EMPTY : (StringUtils.isBlank(this.firstName) ? this.middleName : (" " + this.middleName)),
//				StringUtils.isBlank(this.lastName) ? StringUtils.EMPTY : (!StringUtils.isEmpty(this.firstName) || !StringUtils.isBlank(this.middleName) ? " " + this.lastName : this.lastName));
//	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSalutation() {
		return salutation;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	public String getBusinessPhone() {
		return businessPhone;
	}
	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	public String getPersonalPhone() {
		return personalPhone;
	}
	public void setPersonalPhone(String personalPhone) {
		this.personalPhone = personalPhone;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Date getLastEditedDate() {
		return lastEditedDate;
	}
	public void setLastEditedDate(Date lastEditedDate) {
		this.lastEditedDate = lastEditedDate;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public List<SyncAddress> getAddress() {
		return address;
	}
	public void setAddress(List<SyncAddress> address) {
		this.address = address;
	}

	public Guid getSyncContactId() {
		return syncContactId;
	}

	public void setSyncContactId(Guid syncContactId) {
		this.syncContactId = syncContactId;
	}
	
}

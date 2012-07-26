/**
 *
 * (c) 2003-2012 MuleSoft, Inc. This software is protected under international
 * copyright law. All use of this software is subject to MuleSoft's Master
 * Subscription Agreement (or other Terms of Service) separately entered
 * into between you and MuleSoft. If such an agreement is not in
 * place, you may not use the software.
 */

package org.mule.modules.aplicor.model.sync;

import org.odata4j.core.Guid;

/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public abstract class BaseSyncObject {
	
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
}

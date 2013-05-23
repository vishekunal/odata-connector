package com.mulesoft.modules.odata.demo.model;

import org.mule.modules.odata.annotation.Guid;


/**
 * 
 * @author mariano.gonzalez@mulesoft.com
 *
 */
public class CityBuilding {

	@Guid
	private String entityId;
	private String id;
	private String partitionKey;
	private String rowKey;
	private String type;
	private String name;
	private String address;
	
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPartitionKey() {
		return partitionKey;
	}
	public void setPartitionKey(String partitionKey) {
		this.partitionKey = partitionKey;
	}
	public String getRowKey() {
		return rowKey;
	}
	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}

/**
 * 
 */
package com.softisland.config;

import lombok.Data;

/**
 * @author Administrator
 *
 */
public enum SysTypeEum {
	
	CALL_BACK(1,"回调地址");

	private Integer type;
	private String typeName;
	
	private SysTypeEum(Integer type,String typeName){
		this.type = type;
		this.typeName = typeName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
}

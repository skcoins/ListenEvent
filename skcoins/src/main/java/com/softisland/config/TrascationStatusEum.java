/**
 * 
 */
package com.softisland.config;

/**
 * @author Administrator
 *
 */
public enum TrascationStatusEum {

	DEFAULT_STATUS(0,"初始化状态"),
	SUCCESS_STATUS(1,"完成状态"),
	FAIL_STATUS(2,"失败状态");
	
	private Integer status;
	private String msg;
	
	private TrascationStatusEum(Integer status,String msg){
		this.status = status;
		this.msg = msg;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}

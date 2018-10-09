/**
 * 
 */
package com.softisland.config;

/**
 * @author Administrator
 *
 */
public enum ErrorCodeEum {
	
	ETH_BALANCE_NOT_ENOUGH(40001,"ETH余额不足"),
	ETH_GASPRICE_HIGH(40002,"当前块燃油费过高");

	private Integer code;
	private String msg;
	
	private ErrorCodeEum(Integer code,String msg){
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}

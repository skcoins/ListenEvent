/**
 * 
 */
package com.softisland.config;

/**
 * @author Administrator
 *
 */
public enum EventNameEum {

	/**
	 * redeem 充值积分
	 */
	REDEEM_EVENT("redeemEvent"),
	/**
	 * 积分换token
	 */
	WITHDRAW_EVENT("withdrawEvent"),
	/**
	 * 结算所有积分
	 */
	LEDGER_RECORD_EVENT("ledgerRecordEvent");
	
	
	private String name;
	
	private EventNameEum(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}

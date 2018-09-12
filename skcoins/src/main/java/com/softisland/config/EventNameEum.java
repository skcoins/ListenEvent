/**
 * 
 */
package com.softisland.config;

/**
 * @author Administrator
 *
 */
public enum EventNameEum {

	REDEEM_EVENT("redeemEvent"),
	WITHDRAW_EVENT("withdrawEvent"),
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

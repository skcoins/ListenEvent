/**
 * 
 */
package com.softisland.config;

/**
 * @author Administrator
 *
 */
public enum ContractEum {
	
	SET_ADMINISTRATOR("setAdministrator"),
	UNSET_ADMINISTRATOR("unsetAdministrator"),
	REPLACE_ADMINISTRATOR("replaceAdministrator"),
	UPDATE_LEDGER("updateLedger"),
	WITH_DRAW("withdraw");

	private String name;
	
	private ContractEum(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}

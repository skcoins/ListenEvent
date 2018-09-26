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
	WITH_DRAW("withdraw"),
	ON_TOKEN_PURCHASE("onTokenPurchase"),
	ON_TOKEN_SELL("onTokenSell"),
	SET_SKCADDRESS("setSkcAdderss")
	;
	

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

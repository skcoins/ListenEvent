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
	 * redeem token换积分
	 */
	REDEEM_EVENT("tokenToPointEvent"),
	/**
	 * 积分换token
	 */
	WITHDRAW_EVENT("pointToTokenEvent"),
	/**
	 * 结算所有积分
	 */
	LEDGER_RECORD_EVENT("ledgerRecordEvent"),
	/**
	 * ETH 直接换积分
	 */
	ETH_TO_POINT_EVENT("ethToPointEvent"),
	
	ON_TOKEN_PURCHASE_EVENT("onTokenPurchaseEvent"),
	
	ON_TOKEN_SELL_EVENT("onTokenSellEvent"),
	/**
	 * 自动分红
	 */
	ASSETS_DETAIL_EVENT("assetsDetailEvent"),
	/**
	 * 
	 */
	DIVID_EVENT("divideEvent"),
	/**
	 * 手动分红
	 */
	DIVIDEND_DETAIL_EVENT("dividendDetailEvent")
	;
	
	
	
	
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

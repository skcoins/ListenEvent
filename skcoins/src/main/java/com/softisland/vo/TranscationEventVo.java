/**
 * 
 */
package com.softisland.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TranscationEventVo {
	
	private Long businessId;
	
	private Integer status;

	private String transactionHash;
	
	private String tradePerson;
	
	private String nums;
	
	
	// 0 eth 1 token 2 积分
	private Integer currency;
	
	private String divChoice;
	
	private String tokensMinted;
	
	private String tokenPrice;
	
	private String referredBy;
	
	private String ethMinted;
	
	private String eventName;
	
	private String gasUsed;
	
	private String referrerToken;
	
	private String tokenHolder;
	
	private String platformToken;
}

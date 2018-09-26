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
}

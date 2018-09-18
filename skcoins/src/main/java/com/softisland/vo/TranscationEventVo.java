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

	private String transcaionHash;
	
	private String fromPerson;
	
	private String toPerson;
	
	private Long nums;
	
	private Integer currency;
	
	private Integer status;
	
	private String divChoice;
	
	private String tokensMinted;
	
	private String tokenPrice;
	
	private String referredBy;
	
}

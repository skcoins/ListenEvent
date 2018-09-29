/**
 * 
 */
package com.softisland.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 *
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointToTokenDto {
	
	private String adminAddress;
	
	private String adminPrikey;

	private Long businessId;
	
	private String tradePerson;
	
	private String amount;
}

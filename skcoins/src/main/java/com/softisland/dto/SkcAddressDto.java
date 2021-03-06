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
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SkcAddressDto {

	private String skcAddress;
	
	private String adminAddress;
	
	private String adminPrikey;
}

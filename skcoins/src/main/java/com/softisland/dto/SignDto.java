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
public class SignDto {

	private String address;
	
	private String signedHash;
	
	private String message;
	
}

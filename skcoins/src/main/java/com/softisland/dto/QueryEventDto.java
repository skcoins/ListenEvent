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
public class QueryEventDto {
	
	private Long businessId;

	private String tnHash;
	
	private String eventName;
	
}

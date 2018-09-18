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

	private String tnHash;
	
	// 0 eth换TOKEN , 1 token换积分  , 2其他
	private int type;
	
}

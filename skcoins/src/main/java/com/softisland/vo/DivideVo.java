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
public class DivideVo {

	private String customerAddress;
	private String referrerToken;
	private String tokenHolder;
}

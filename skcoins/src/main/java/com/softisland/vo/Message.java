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
public class Message {

	public static Integer SUCCESS = 1;
	public static Integer FAILED = 0;
	
	private Integer ret;
	
	private String code;
	
	private String msg;
	
	private Object data;
}

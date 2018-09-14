/**
 * 
 */
package com.softisland.dto;

import java.util.List;

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
public class UpdateAdminDto {

	private List<String> adminstratorList;
	//0 删除   1添加权限   2 给予新账号赋予权限
	private Integer type;
	
	private String newAdmin;
}

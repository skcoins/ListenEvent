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
public class LedgerDto {

	private String serialNumber;
	
	private Long dateTime;
	
	private List<DataInfo> dataList;
	
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class DataInfo{
		private String address;
		private String oldPoints;
		private String newPoints;
	}
}

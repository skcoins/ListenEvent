/**
 * 
 */
package com.softisland.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressArrayDto {

	private List<String> addressList;
	
}

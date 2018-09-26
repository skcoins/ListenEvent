/**
 * 
 */
package com.softisland.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

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
@ConfigurationProperties(prefix = "contract.skcoin")
public class SkCoinProperties {

	private String masterPrikey;
	
	private String contractAddress;
	
	private String adminPrikey; 
}

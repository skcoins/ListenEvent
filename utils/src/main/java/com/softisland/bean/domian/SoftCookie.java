package com.softisland.bean.domian;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Administrator
 *
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoftCookie {
    private String name;
    private String value;
    private String domain;
    private String path;
}

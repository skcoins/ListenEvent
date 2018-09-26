package com.softisland.bean.domian;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoftHttpResponse {
    /**
     * 返回的HTTP状态码
     */
    private int status;
    /**
     * 返回的内容
     */
    private String content;
    /**
     * 返回的headers
     */
    private Map<String,String> headers;
    /**
     * 返回的cookies
     */
    private Map<String,String> cookies;
    
}

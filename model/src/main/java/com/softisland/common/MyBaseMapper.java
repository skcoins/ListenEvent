/**
 * 
 */
package com.softisland.common;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author Administrator
 *
 */
public interface MyBaseMapper<T> extends BaseMapper<T>, MySqlMapper<T>, ConditionMapper<T> {

}

package com.softisland.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.softisland.common.MyBaseMapper;
import com.softisland.model.BonusEvent;

public interface BonusEventMapper extends MyBaseMapper<BonusEvent> {
	
	@Select("select * from `bonus_event` where status = #{status} and block_number + 12 <= #{nowBlockNumber}")
	@ResultMap("BaseResultMap")
	public List<BonusEvent> queryDefaultBonusEvent(@Param("status") Integer status,@Param("nowBlockNumber") Long nowBlockNumber);
}
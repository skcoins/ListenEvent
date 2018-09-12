package com.softisland.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.softisland.common.MyBaseMapper;
import com.softisland.model.TranscationEvent;

public interface TranscationEventMapper extends MyBaseMapper<TranscationEvent> {
	
	@Select("select block_number from `transcation_event` order by block_number desc limit 1")
	public Long getMaxBlockNumber();
	
	@Select("select * from `transcation_event` where status = 0 and block_number + 12 <= #{nowBlockNumber}")
	@ResultMap("BaseResultMap")
	public List<TranscationEvent> queryDefaultTranscationEvent(@Param("nowBlockNumber") Long nowBlockNumber);
}
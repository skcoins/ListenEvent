package com.softisland.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.softisland.common.MyBaseMapper;
import com.softisland.model.ExchangeCoins;

public interface ExchangeCoinsMapper extends MyBaseMapper<ExchangeCoins> {
	
	@Select("select block_number from `exchange_coins` order by block_number desc limit 1")
	public Long getMaxBlockNumber();
	
	
	@Select("select * from `exchange_coins` where status = 0 and block_number + 12 <= #{nowBlockNumber}")
	@ResultMap("BaseResultMap")
	public List<ExchangeCoins> queryDefaultExchangeCoins(@Param("nowBlockNumber") Long nowBlockNumber);
}
/**
 * 
 */
package com.softisland.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softisland.mapper.ExchangeCoinsMapper;
import com.softisland.model.ExchangeCoins;
import com.softisland.model.TranscationEvent;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 针对TOKEN 转换积分 以及积分转换TOKEN
 * @author Administrator
 *
 */
@Service
public class ExchangeCoinsService {

	@Autowired
	ExchangeCoinsMapper exchangeCoinsMapper;
	
	/**
	 * 
	 * @param exchangeCoins
	 */
	public void insertExchangeCoins(ExchangeCoins exchangeCoins){
		exchangeCoinsMapper.insert(exchangeCoins);
	}
	
	/**
	 * 得到最大的块高度
	 * @return
	 */
	public BigInteger getMaxBlockNumber(){
		
		BigInteger maxBlockNumberBig = null;
		
		Long maxBlockNumber = exchangeCoinsMapper.getMaxBlockNumber();
		if(maxBlockNumber != null){
			maxBlockNumberBig = new BigInteger(maxBlockNumber.toString());
		}
		
		return maxBlockNumberBig;
	}
	
	/**
	 * 得到未完成的时间交易
	 * @return
	 */
	public List<ExchangeCoins> queryDefaultTranscationEvent(Long nowBlockNumber){
		
		return exchangeCoinsMapper.queryDefaultExchangeCoins(nowBlockNumber);
	}
	
	/**
	 * 
	 * @param transcationEvent
	 * @return
	 */
	public int updateExchangeCoinsEvent(ExchangeCoins exchangeCoins,Integer status){
		Condition condition = new Condition(ExchangeCoins.class);
		Criteria criteria = condition.createCriteria();
		if(status != null){
			criteria.andNotEqualTo("status", status);
		}
		
		criteria.andEqualTo("id", exchangeCoins.getId());
		
		int ret = exchangeCoinsMapper.updateByConditionSelective(exchangeCoins, condition);
		
		
		return ret;
	}
	
	/**
	 * 
	 * @param tnHash
	 * @return
	 */
	public List<ExchangeCoins> queryExchangeCoinsByTnHash(String tnHash){
		return exchangeCoinsMapper.select(ExchangeCoins.builder()
				.transcationHash(tnHash)
				.build());
	}
	
	
}

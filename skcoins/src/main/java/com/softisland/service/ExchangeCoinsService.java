/**
 * 
 */
package com.softisland.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softisland.config.TrascationStatusEum;
import com.softisland.mapper.ExchangeCoinsMapper;
import com.softisland.mapper.LedgerEventMapper;
import com.softisland.model.ExchangeCoins;
import com.softisland.model.LedgerEvent;

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
	
	@Autowired
	LedgerEventMapper ledgerEventMapper;
	
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
		return exchangeCoinsMapper.queryDefaultExchangeCoins(TrascationStatusEum.DEFAULT_STATUS.getStatus(),nowBlockNumber);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<ExchangeCoins> queryNoCall(){
		Condition condition = new Condition(ExchangeCoins.class);
		Criteria criteria = condition.createCriteria();
		criteria.andNotEqualTo("status", TrascationStatusEum.DEFAULT_STATUS.getStatus());
		criteria.andEqualTo("isCall", 0);
		return exchangeCoinsMapper.selectByCondition(condition);
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
	
	/**
	 * 
	 * @param businessId
	 * @return
	 */
	public List<ExchangeCoins> queryExchangeCoinsByBusinessId(Long businessId){
		return exchangeCoinsMapper.select(ExchangeCoins.builder()
				.businessId(businessId)
				.build());
	}
	
	
	/**
	 * 
	 * @param ledgerEvent
	 */
	public int insertledgerEvent(LedgerEvent ledgerEvent){
		return ledgerEventMapper.insert(ledgerEvent);
	}
	
	/**
	 * 
	 * @param ledgerEvent
	 * @param status
	 * @return
	 */
	public int updateledgerEvent(LedgerEvent ledgerEvent,Integer status){
		
		Condition condition = new Condition(ExchangeCoins.class);
		Criteria criteria = condition.createCriteria();
		if(status != null){
			criteria.andNotEqualTo("status", status);
		}
		
		criteria.andEqualTo("id", ledgerEvent.getId());
		
		return ledgerEventMapper.updateByConditionSelective(ledgerEvent, condition);
	}
	
	/**
	 * 
	 * @param businessId
	 * @return
	 */
	public List<LedgerEvent> queryLedgerEvent(Long businessId){
		
		Condition condition = new Condition(LedgerEvent.class);
		Criteria criteria = condition.createCriteria();
		criteria.andEqualTo("businessId", businessId);
		
		return ledgerEventMapper.selectByCondition(condition);
	}
	
	/**
	 * 
	 * @param exchangeCoins
	 * @return
	 */
	public int updateExchangeCoins(ExchangeCoins exchangeCoins){
		return exchangeCoinsMapper.updateByPrimaryKeySelective(exchangeCoins);
	}
}

/**
 * 
 */
package com.softisland.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softisland.config.TrascationStatusEum;
import com.softisland.mapper.BonusEventMapper;
import com.softisland.mapper.TranscationEventMapper;
import com.softisland.model.BonusEvent;
import com.softisland.model.TranscationEvent;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * ETH 转为TOKEN 
 * @author Administrator
 *
 */
@Service
public class TranscationEventService {

	@Autowired
	TranscationEventMapper transcationEventMapper;
	
	@Autowired
	BonusEventMapper bonusEventMapper;
	
	/**
	 * 新增
	 * @param transcationEvent
	 */
	public void insertTranscationEvent(TranscationEvent transcationEvent){
		transcationEventMapper.insert(transcationEvent);
	}
	
	/**
	 * 得到最大的块高度
	 * @return
	 */
	public BigInteger getMaxBlockNumber(){
		
		BigInteger maxBlockNumberBig = null;
		
		Long maxBlockNumber = transcationEventMapper.getMaxBlockNumber();
		if(maxBlockNumber != null){
			maxBlockNumberBig = new BigInteger(maxBlockNumber.toString());
		}
		
		return maxBlockNumberBig;
	}
	
	/**
	 * 得到未完成的时间交易
	 * @return
	 */
	public List<TranscationEvent> queryDefaultTranscationEvent(Long nowBlockNumber){
		
		return transcationEventMapper.queryDefaultTranscationEvent(TrascationStatusEum.DEFAULT_STATUS.getStatus(),nowBlockNumber);
	}
	
	/**
	 * 得到未完成交易的
	 * @param nowBlockNumber
	 * @return
	 */
	public List<BonusEvent> queryDefaultBonusEvent(Long nowBlockNumber){
		return bonusEventMapper.queryDefaultBonusEvent(TrascationStatusEum.DEFAULT_STATUS.getStatus(),nowBlockNumber);
	}
	
	/**
	 * 更新TranscationEvent状态
	 * @param transcationEvent
	 * @param status
	 * @return
	 */
	public int updateTranscationEvent(TranscationEvent transcationEvent,Integer status){
		Condition condition = new Condition(TranscationEvent.class);
		Criteria criteria = condition.createCriteria();
		if(status != null){
			criteria.andNotEqualTo("status", status);
		}
		
		criteria.andEqualTo("id", transcationEvent.getId());
		
		int ret = transcationEventMapper.updateByConditionSelective(transcationEvent, condition);
		return ret;
	}
	
	/**
	 * 
	 * @param bonusEvent
	 * @param status
	 * @return
	 */
	public int updateBonusEvent(BonusEvent bonusEvent,Integer status){
		Condition condition = new Condition(TranscationEvent.class);
		Criteria criteria = condition.createCriteria();
		if(status != null){
			criteria.andNotEqualTo("status", status);
		}
		
		criteria.andEqualTo("id", bonusEvent.getId());
		
		int ret = bonusEventMapper.updateByConditionSelective(bonusEvent, condition);
		return ret;
	}
	
	/**
	 * 通过tnhash查询
	 * @param tnHash
	 * @return
	 */
	public List<TranscationEvent> queryTranscationEventByTnHash(String tnHash){
		List<TranscationEvent> list = transcationEventMapper.select(TranscationEvent.builder()
				.transcationHash(tnHash)
				.build());
		return list;
	}
	
	/**
	 * 
	 * @param businessId
	 * @param tnHash
	 * @return
	 */
	public List<TranscationEvent> queryTranscationEventByBusinessId(Long businessId,String tnHash){
		List<TranscationEvent> list = transcationEventMapper.select(TranscationEvent.builder().businessId(businessId).build());
		return list;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<TranscationEvent> queryNoCall(){
		Condition condition = new Condition(TranscationEvent.class);
		Criteria criteria = condition.createCriteria();
		criteria.andNotEqualTo("status", TrascationStatusEum.DEFAULT_STATUS.getStatus());
		criteria.andEqualTo("isCall", 0);
		
		return transcationEventMapper.selectByCondition(condition);
	}
	
	/**
	 * 
	 * @param transcationEvent
	 * @return
	 */
	public int updateTranscationEvent(TranscationEvent transcationEvent){
		return transcationEventMapper.updateByPrimaryKeySelective(transcationEvent);
	}
	
	/**
	 * 
	 * @param bonusEvent
	 */
	public void insertBonusEvent(BonusEvent bonusEvent){
		bonusEventMapper.insert(bonusEvent);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<BonusEvent> queryNoCallBonus(){
		Condition condition = new Condition(BonusEvent.class);
		Criteria criteria = condition.createCriteria();
		criteria.andNotEqualTo("status", TrascationStatusEum.DEFAULT_STATUS.getStatus());
		criteria.andEqualTo("isCall", 0);
		
		return bonusEventMapper.selectByCondition(condition);
	}
	
	public int updateBonusEvent(BonusEvent bonusEvent){
		return bonusEventMapper.updateByPrimaryKeySelective(bonusEvent);
	}
}

/**
 * 
 */
package com.softisland.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softisland.mapper.TranscationEventMapper;
import com.softisland.model.TranscationEvent;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author Administrator
 *
 */
@Service
public class TranscationEventService {

	@Autowired
	TranscationEventMapper transcationEventMapper;
	
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
		
		return transcationEventMapper.queryDefaultTranscationEvent(nowBlockNumber);
	}
	
	/**
	 * 
	 * @param transcationEvent
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
}

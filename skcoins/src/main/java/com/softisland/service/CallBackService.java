/**
 * 
 */
package com.softisland.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.softisland.bean.domian.SoftHttpResponse;
import com.softisland.bean.network.OKHttpClientUtil;
import com.softisland.mapper.SysConfigMapper;
import com.softisland.model.BonusEvent;
import com.softisland.model.ExchangeCoins;
import com.softisland.model.SysConfig;
import com.softisland.model.TranscationEvent;
import com.softisland.vo.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CallBackService {
	
	@Autowired
	SysConfigMapper sysConfigMapper;
	
	@Value("${front.callbackurl}")
	private String frontCallBackUrl;
	
	@Autowired
	ExchangeCoinsService exchangeCoinsService;
	
	@Autowired
	TranscationEventService transcationEventService;
	
	@Autowired
	ContractService contractService;
	
	
	/**
	 * 
	 */
	public void bankRollCallBack(){
		
		List<SysConfig> confList = sysConfigMapper.select(SysConfig.builder().type((short)1).status((short)1).build());
		
		if(confList == null  || confList.size() == 0){
			return;
		}
		
		SysConfig sysConfig = confList.get(0);
		
		List<ExchangeCoins> list = exchangeCoinsService.queryNoCall();
			
			list.forEach(v->{
				try {
					
					Message message = Message.builder()
					.ret(Message.SUCCESS)
					.code("200")
					.msg("成功")
					.data(contractService.getTranscationEventVo(v))
					.build();
					
					SoftHttpResponse response = OKHttpClientUtil.postJsonDataToUrl(sysConfig.getValue(), JSON.toJSONString(message));
					
					log.info("bankroll 回调内容({})",JSON.toJSONString(message));
					if(response!=null && response.getStatus() == 200){
						
						exchangeCoinsService.updateExchangeCoins(ExchangeCoins.builder()
								.id(v.getId())
								.isCall((short)1)
								.callDate(new Date())
								.build());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
	}
	
	/**
	 * 
	 */
	public void skcoinCallBack(){
		
		List<SysConfig> confList = sysConfigMapper.select(SysConfig.builder().type((short)1).status((short)1).build());
		
		if(confList == null || confList.size() == 0){
			return;
		}
		
		SysConfig sysConfig = confList.get(0);
		
		List<TranscationEvent> list = transcationEventService.queryNoCall();
		list.forEach(v->{
			try {
				Message message = Message.builder()
				.ret(Message.SUCCESS)
				.code("200")
				.msg("成功")
				.data(contractService.getTranscationEventVo(v)).build();
				
				log.info("skcoin 回调内容({})",JSON.toJSONString(message));
				
				SoftHttpResponse response = OKHttpClientUtil.postJsonDataToUrl(sysConfig.getValue(), JSON.toJSONString(message));
				if(response!=null && response.getStatus() == 200){
					
					transcationEventService.updateTranscationEvent(TranscationEvent.builder()
							.id(v.getId())
							.isCall((short)1)
							.callDate(new Date())
							.build());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * 
	 */
	public void bonusCallBack(){
		List<SysConfig> confList = sysConfigMapper.select(SysConfig.builder().type((short)1).status((short)1).build());
		
		if(confList == null || confList.size() == 0){
			return;
		}
		
		SysConfig sysConfig = confList.get(0);
		
		List<BonusEvent> list = transcationEventService.queryNoCallBonus();
		
		list.forEach(v -> {
			
			Message message = Message.builder()
					.ret(Message.SUCCESS)
					.code("200")
					.msg("成功")
					.data(contractService.getTranscationEventVo(v)).build();
			
			try {
				SoftHttpResponse response = OKHttpClientUtil.postJsonDataToUrl(sysConfig.getValue(), JSON.toJSONString(message));
				log.info("bonus分红 回调内容({})",JSON.toJSONString(message));
				if(response!=null && response.getStatus() == 200){
					
					transcationEventService.updateBonusEvent(BonusEvent.builder()
							.id(v.getId())
							.isCall((short)1)
							.callDate(new Date())
							.build());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
	}
//	public static void main(String[] args) {
//		System.out.println(Convert.fromWei("186357",Convert.Unit.ETHER).toPlainString());
//		
//		System.out.println(new BigDecimal("186357").divide(new BigDecimal("1000000000000000000")).setScale(18, BigDecimal.ROUND_HALF_UP).toPlainString());
//	}
}

/**
 * 
 */
package com.softisland.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.utils.Convert;

import com.alibaba.fastjson.JSON;
import com.softisland.bean.domian.SoftHttpResponse;
import com.softisland.bean.network.OKHttpClientUtil;
import com.softisland.mapper.SysConfigMapper;
import com.softisland.model.BonusEvent;
import com.softisland.model.ExchangeCoins;
import com.softisland.model.SysConfig;
import com.softisland.model.TranscationEvent;
import com.softisland.vo.Message;
import com.softisland.vo.TranscationEventVo;

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
	
	
	
	public void bankRollCallBack(){
		
		List<SysConfig> confList = sysConfigMapper.select(SysConfig.builder().type((short)1).status((short)1).build());
		
		if(confList == null  || confList.size() == 0){
			return;
		}
		
		SysConfig sysConfig = confList.get(0);
		
		List<ExchangeCoins> list = exchangeCoinsService.queryNoCall();
			
			list.forEach(v->{
				try {
					SoftHttpResponse response = OKHttpClientUtil.postJsonDataToUrl(sysConfig.getValue(), JSON.toJSONString(Message.builder()
					.ret(Message.SUCCESS)
					.code("200")
					.msg("成功")
					.data(TranscationEventVo.builder()
							.status(v.getStatus().intValue())
							.businessId(v.getBusinessId())
							.eventName(v.getEventName())
							.transactionHash(v.getTranscationHash())
							.tradePerson(v.getTradePerson())
							.gasUsed(v.getGas())
							.currency(v.getCurrency().intValue())
							.nums(v.getNums())
							.build())
					.build()));
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
				.data(TranscationEventVo.builder()
						.status(v.getStatus().intValue())
						.businessId(v.getBusinessId())
						.eventName(v.getEventName())
						.currency(v.getCurrency().intValue())
						.divChoice(v.getDivChoice())
						.ethMinted(v.getEthMinted()!=null ? Convert.fromWei(v.getEthMinted(), Convert.Unit.ETHER).toPlainString() : null)
						.eventName(v.getEventName())
						.nums(v.getCurrency().equals((short)0) ? Convert.fromWei(v.getNums(),Convert.Unit.ETHER).toPlainString() : v.getNums())
						.referredBy(v.getReferredBy())
						.status(v.getStatus().intValue())
						.tokenPrice(v.getTokenPrice()!=null ? Convert.fromWei(v.getTokenPrice(), Convert.Unit.ETHER).toPlainString() : null)
						.tokensMinted(v.getTokensMinted()!=null ? Convert.fromWei(v.getTokensMinted(), Convert.Unit.ETHER).toPlainString() : null)
						.tradePerson(v.getTradePerson())
						.transactionHash(v.getTranscationHash())
						.gasUsed(v.getGas())
						.build()).build();
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
	
	public void bonusCallBack(){
		List<SysConfig> confList = sysConfigMapper.select(SysConfig.builder().type((short)1).status((short)1).build());
		
		if(confList == null || confList.size() == 0){
			return;
		}
		
		SysConfig sysConfig = confList.get(0);
		
		List<BonusEvent> list = transcationEventService.queryNoCallBonus();
		
		list.forEach(v -> {
			
			TranscationEventVo vo = TranscationEventVo.builder()
			.eventName(v.getEventName())
			.gasUsed(v.getGas())
			.status(v.getStatus().intValue())
			.tradePerson(v.getTradePerson())
			.referredBy(v.getReferrer())
			.referrerToken(v.getReferrerToken())
			.tokenHolder(v.getTokenHolder())
			.platformToken(v.getPlatformToken())
			.build();
			
			Message message = Message.builder()
					.ret(Message.SUCCESS)
					.code("200")
					.msg("成功")
					.data(vo).build();
			
			try {
				SoftHttpResponse response = OKHttpClientUtil.postJsonDataToUrl(sysConfig.getValue(), JSON.toJSONString(message));
				
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

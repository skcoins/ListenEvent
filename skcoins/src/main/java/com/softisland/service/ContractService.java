/**
 * 
 */
package com.softisland.service;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.alibaba.fastjson.JSON;
import com.softisland.config.ContractEum;
import com.softisland.config.EventNameEum;
import com.softisland.config.TrascationStatusEum;
import com.softisland.contract.Bankroll_sol_BankRoll;
import com.softisland.contract.Bankroll_sol_BankRoll.RedeemEventEventResponse;
import com.softisland.contract.Bankroll_sol_BankRoll.WithdrawEventEventResponse;
import com.softisland.dto.LedgerDto;
import com.softisland.dto.QueryEventDto;
import com.softisland.dto.UpdateAdminDto;
import com.softisland.mapper.ContractOperationMapper;
import com.softisland.model.ContractOperation;
import com.softisland.model.ExchangeCoins;
import com.softisland.model.TranscationEvent;
import com.softisland.vo.TranscationEventVo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 *
 */
@Slf4j
@Service
public class ContractService {

	@Autowired
	Web3j web3j;
	
	@Autowired
	Bankroll_sol_BankRoll bankroll_sol_BankRoll;
	
	@Autowired
	ContractOperationMapper contractOperationMapper;
	
	@Value("${contract.token-address}")
	String tokenContractAddress;
	
	@Value("${contract.token-send-ower-prikey}")
	String sendOwnerPrikey;
	
	@Autowired
	TranscationEventService transcationEventService;
	
	@Autowired
	ExchangeCoinsService exchangeCoinsService;
	
	/**
	 * 管理员操作
	 * @param adminstratorList
	 */
	public void updateBankRoolAdministrator(UpdateAdminDto updateAdminDto){
//		Credentials credentials = Credentials.create(sendOwnerPrikey);
//		
//		Bankroll_sol_BankRoll bank = Bankroll_sol_BankRoll.load(tokenContractAddress, web3j, credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT);
		try {
			
			TransactionReceipt transactionReceipt = null;
			switch (updateAdminDto.getType()) {
			case 0:
				log.info("禁用管理员,管理员列表({})",JSON.toJSONString(updateAdminDto.getAdminstratorList()));
				
				transactionReceipt = bankroll_sol_BankRoll.unsetAdministrator(updateAdminDto.getAdminstratorList()).send();
				
				if(transactionReceipt !=null && transactionReceipt.isStatusOK()){
					
					contractOperationMapper.insert(ContractOperation.builder()
							.name(ContractEum.UNSET_ADMINISTRATOR.getName())
							.blockHash(transactionReceipt.getBlockHash())
							.blockNumber(transactionReceipt.getBlockNumber().longValue())
							.createDate(new Date())
							.data(JSON.toJSONString(updateAdminDto.getAdminstratorList()))
							.operationPerson(transactionReceipt.getFrom())
							.toPerson(transactionReceipt.getTo())
							.transcationHash(transactionReceipt.getTransactionHash())
							.gas(transactionReceipt.getGasUsed().longValue())
							.status((short)1)
							.build());
				}
				break;
			case 1:
				
				log.info("启用管理员,管理员列表({})",JSON.toJSONString(updateAdminDto.getAdminstratorList()));
				
				transactionReceipt = bankroll_sol_BankRoll.setAdministrator(updateAdminDto.getAdminstratorList()).send();
				
				if(transactionReceipt !=null && transactionReceipt.isStatusOK()){
					log.info("真实消耗GAS({})",transactionReceipt.getGasUsed());
					contractOperationMapper.insert(ContractOperation.builder()
							.name(ContractEum.SET_ADMINISTRATOR.getName())
							.blockHash(transactionReceipt.getBlockHash())
							.blockNumber(transactionReceipt.getBlockNumber().longValue())
							.createDate(new Date())
							.data(JSON.toJSONString(updateAdminDto.getAdminstratorList()))
							.operationPerson(transactionReceipt.getFrom())
							.toPerson(transactionReceipt.getTo())
							.transcationHash(transactionReceipt.getTransactionHash())
							.gas(transactionReceipt.getGasUsed().longValue())
							.status((short)1)
							.build());
				}
				break;
			case 2:
				
				String oldOwner = updateAdminDto.getAdminstratorList().get(0);
				
				log.info("替换管理员,老管理员为:({}),新管理员为({})",oldOwner,updateAdminDto.getNewAdmin());
				
				transactionReceipt = bankroll_sol_BankRoll.replaceAdministrator(oldOwner, updateAdminDto.getNewAdmin()).send();
				
				if(transactionReceipt !=null && transactionReceipt.isStatusOK()){
					
					contractOperationMapper.insert(ContractOperation.builder()
							.name(ContractEum.REPLACE_ADMINISTRATOR.getName())
							.blockHash(transactionReceipt.getBlockHash())
							.blockNumber(transactionReceipt.getBlockNumber().longValue())
							.createDate(new Date())
							.data(JSON.toJSONString(updateAdminDto.getAdminstratorList()))
							.operationPerson(transactionReceipt.getFrom())
							.toPerson(transactionReceipt.getTo())
							.transcationHash(transactionReceipt.getTransactionHash())
							.gas(transactionReceipt.getGasUsed().longValue())
							.status((short)1)
							.build());
				}
				break;
			default:
				log.info("管理员操作,非法操作({})",JSON.toJSONString(updateAdminDto));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 修改用户余额
	 * @param ledgerDto
	 */
	public void updateLedger(LedgerDto ledgerDto){
//		Credentials credentials = Credentials.create("ceda59b2b97439952bb3c481123bc197ef2591b83ccf4686ee220d1cdc55519c");
//		
//		Bankroll_sol_BankRoll bank = Bankroll_sol_BankRoll.load(tokenContractAddress, web3j, credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT);
		
		List<String> addressList = new ArrayList<String>();
		
		List<BigInteger> oldPointList = new ArrayList<BigInteger>();
		
		List<BigInteger> newPointList = new ArrayList<BigInteger>();
		
		ledgerDto.getDataList().forEach(v->{
			addressList.add(v.getAddress());
			oldPointList.add(new BigInteger(v.getOldPoints()));
			newPointList.add(new BigInteger(v.getNewPoints()));
		});
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			TransactionReceipt transactionReceipt = bankroll_sol_BankRoll.updateLedger(new BigInteger(ledgerDto.getSerialNumber()), addressList, oldPointList, newPointList, format.format(new Date(ledgerDto.getDateTime()*1000))).send();
			if(transactionReceipt !=null && transactionReceipt.isStatusOK()){
				contractOperationMapper.insert(ContractOperation.builder()
						.name(ContractEum.UPDATE_LEDGER.getName())
						.blockHash(transactionReceipt.getBlockHash())
						.blockNumber(transactionReceipt.getBlockNumber().longValue())
						.createDate(new Date())
						.data(JSON.toJSONString(ledgerDto))
						.operationPerson(transactionReceipt.getFrom())
						.toPerson(transactionReceipt.getTo())
						.transcationHash(transactionReceipt.getTransactionHash())
						.gas(transactionReceipt.getGasUsed().longValue())
						.status((short)1)
						.build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询积分
	 * @param address
	 * @return
	 */
	public Long queryPoint(String address){
		Long point = 0l;
//		Credentials credentials = Credentials.create("ceda59b2b97439952bb3c481123bc197ef2591b83ccf4686ee220d1cdc55519c");
//		
//		Bankroll_sol_BankRoll bank = Bankroll_sol_BankRoll.load(tokenContractAddress, web3j, credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT);
		
		try {
			BigInteger ret = bankroll_sol_BankRoll.point(address).send();
			point = ret.longValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return point;
	}
	
//	public void ethRedeem(){
//		Credentials credentials = Credentials.create("ceda59b2b97439952bb3c481123bc197ef2591b83ccf4686ee220d1cdc55519c");
//		Bankroll_sol_BankRoll bank = Bankroll_sol_BankRoll.load(tokenContractAddress, web3j, credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT);
//		
//		try {
//			bank.redeem("0x5e7607d8cE07b689cb709fe4998f79f7eF8eE841", new BigInteger("10")).send();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	public void withdraw(){
//		Credentials credentials = Credentials.create("ceda59b2b97439952bb3c481123bc197ef2591b83ccf4686ee220d1cdc55519c");
//		Bankroll_sol_BankRoll bank = Bankroll_sol_BankRoll.load(tokenContractAddress, web3j, credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT);
//		
//		try {
//			bank.withdraw("0x5e7607d8cE07b689cb709fe4998f79f7eF8eE841", new BigInteger("10")).send();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	
	public TranscationEventVo queryTranscationEventDtoByParams(QueryEventDto queryEventDto){
		TranscationEventVo transcationEventVo = null;
		
		
		switch (queryEventDto.getType()) {
		//ETH转TOKEN
		case 0:
			List<TranscationEvent> transcationList = transcationEventService.queryTranscationEventByTnHash(queryEventDto.getTnHash());
			
			if(transcationList == null || transcationList.size() == 0){
				try {
					EthGetTransactionReceipt receipt = web3j.ethGetTransactionReceipt(queryEventDto.getTnHash()).send();
					
					TransactionReceipt transcationReceipt = receipt.getResult();
					
					if(transcationReceipt == null){
						transcationEventVo = TranscationEventVo.builder()
						.transcaionHash(queryEventDto.getTnHash())
						.status(TrascationStatusEum.DEFAULT_STATUS.getStatus())
						.build();
						
						return transcationEventVo;
					}
					//调取只能合约 得到日志
					
					transcationEventVo = TranscationEventVo.builder().build();
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			break;

		default:
			
			List<ExchangeCoins> exchangesCoinsList = exchangeCoinsService.queryExchangeCoinsByTnHash(queryEventDto.getTnHash());
			
			if(exchangesCoinsList == null || exchangesCoinsList.size() == 0){
				try {
					EthGetTransactionReceipt receipt = web3j.ethGetTransactionReceipt(queryEventDto.getTnHash()).send();
					TransactionReceipt transcationReceipt = receipt.getResult();
					
					//未能查询到交易
					if(transcationReceipt == null){
						transcationEventVo = TranscationEventVo.builder()
						.transcaionHash(queryEventDto.getTnHash())
						.status(TrascationStatusEum.DEFAULT_STATUS.getStatus())
						.build();
						return transcationEventVo;
					}
					
					//交易失败
					if(!transcationReceipt.isStatusOK()){
						transcationEventVo = TranscationEventVo.builder()
								.transcaionHash(queryEventDto.getTnHash())
								.status(TrascationStatusEum.FAIL_STATUS.getStatus())
								.build();
						return transcationEventVo;
					}  
					
					//TOKEN转积分
					if(queryEventDto.getType() == 1){
						List<RedeemEventEventResponse> responseList = bankroll_sol_BankRoll.getRedeemEventEvents(transcationReceipt);
						
						//有交易 但没有日志 认定为失败
						if(responseList == null || responseList.size() == 0){
							transcationEventVo = TranscationEventVo.builder()
									.transcaionHash(queryEventDto.getTnHash())
									.status(TrascationStatusEum.FAIL_STATUS.getStatus())
									.build();
							return transcationEventVo;
						}
						
						RedeemEventEventResponse v  = responseList.get(0);
						
						transcationEventVo = TranscationEventVo.builder()
								.currency(1)
								.status(TrascationStatusEum.SUCCESS_STATUS.getStatus())
								.transcaionHash(queryEventDto.getTnHash())
								.fromPerson(v.sender)
								.nums(v.amount.longValue())
								.build();
						
						//成功记录写入数据库 
						exchangeCoinsService.insertExchangeCoins(ExchangeCoins.builder()
								.blockHash(v.log.getBlockHash())
								.blockNumber(v.log.getBlockNumber().longValue())
								.callDate(new Date())
								.confirmBlockNum(0l)
								.createDate(new Date())
								.currency((short)1)
								.eventName(EventNameEum.REDEEM_EVENT.getName())
								.fromPerson(v.sender)
								.gas(transcationReceipt.getGasUsed().longValue())
								.isCall((short)1)
								.nums(v.amount.longValue())
								.status(TrascationStatusEum.SUCCESS_STATUS.getStatus().shortValue())
								.toPerson(v.log.getAddress())
								.transcationHash(v.log.getTransactionHash())
								.updateDate(new Date())
								.build());
					}
					//积分转TOKEN
					if(queryEventDto.getType() == 2){
						List<WithdrawEventEventResponse> responseList = bankroll_sol_BankRoll.getWithdrawEventEvents(transcationReceipt);
						
						//有交易 但没有日志 认定为失败
						if(responseList == null || responseList.size() == 0){
							transcationEventVo = TranscationEventVo.builder()
									.transcaionHash(queryEventDto.getTnHash())
									.status(TrascationStatusEum.FAIL_STATUS.getStatus())
									.build();
							return transcationEventVo;
						}
						
						WithdrawEventEventResponse v  = responseList.get(0);
						
						transcationEventVo = TranscationEventVo.builder()
								.currency(1)
								.status(TrascationStatusEum.SUCCESS_STATUS.getStatus())
								.transcaionHash(queryEventDto.getTnHash())
								.fromPerson(v.sender)
								.nums(v.amount.longValue())
								.build();
						
						//成功记录写入数据库 
						exchangeCoinsService.insertExchangeCoins(ExchangeCoins.builder()
								.blockHash(v.log.getBlockHash())
								.blockNumber(v.log.getBlockNumber().longValue())
								.callDate(new Date())
								.confirmBlockNum(0l)
								.createDate(new Date())
								.currency((short)1)
								.eventName(EventNameEum.WITHDRAW_EVENT.getName())
								.fromPerson(v.sender)
								.gas(transcationReceipt.getGasUsed().longValue())
								.isCall((short)1)
								.nums(v.amount.longValue())
								.status(TrascationStatusEum.SUCCESS_STATUS.getStatus().shortValue())
								.toPerson(v.log.getAddress())
								.transcationHash(v.log.getTransactionHash())
								.updateDate(new Date())
								.build());
					}
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} else {
				
				ExchangeCoins exchangeCoins = exchangesCoinsList.get(0);
				
				transcationEventVo = TranscationEventVo.builder()
						.currency(exchangeCoins.getCurrency().intValue())
						.status(exchangeCoins.getStatus().intValue())
						.transcaionHash(exchangeCoins.getTranscationHash())
						.fromPerson(exchangeCoins.getFromPerson())
						.nums(exchangeCoins.getNums())
						.build();
			}
			break;
		}
		
		
		
		return transcationEventVo;
	}
}

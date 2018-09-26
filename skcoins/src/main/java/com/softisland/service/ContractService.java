/**
 * 
 */
package com.softisland.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.utils.Convert;

import com.alibaba.fastjson.JSON;
import com.softisland.config.ContractEum;
import com.softisland.config.EventNameEum;
import com.softisland.config.TrascationStatusEum;
import com.softisland.contract.Bankroll_sol_BankRoll;
import com.softisland.contract.Bankroll_sol_BankRoll.PointToTokenEventEventResponse;
import com.softisland.contract.Bankroll_sol_BankRoll.TokenToPointEventEventResponse;
import com.softisland.contract.Skcoin_sol_Skcoin;
import com.softisland.contract.Skcoin_sol_Skcoin.DividendDetailEventResponse;
import com.softisland.contract.Skcoin_sol_Skcoin.OnTokenPurchaseEventResponse;
import com.softisland.contract.Skcoin_sol_Skcoin.OnTokenSellEventResponse;
import com.softisland.dto.AddressArrayDto;
import com.softisland.dto.DivideDto;
import com.softisland.dto.LedgerDto;
import com.softisland.dto.QueryEventDto;
import com.softisland.dto.SkcAddressDto;
import com.softisland.dto.UpdateAdminDto;
import com.softisland.mapper.ContractOperationMapper;
import com.softisland.model.BonusEvent;
import com.softisland.model.ContractOperation;
import com.softisland.model.ExchangeCoins;
import com.softisland.model.LedgerEvent;
import com.softisland.model.TranscationEvent;
import com.softisland.vo.DivideVo;
import com.softisland.vo.EthBalanceVo;
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
	Skcoin_sol_Skcoin skcoin_sol_Skcoin;
	
	@Autowired
	ContractOperationMapper contractOperationMapper;
	
	@Autowired
	TranscationEventService transcationEventService;
	
	@Autowired
	ExchangeCoinsService exchangeCoinsService;
	
	/**
	 * 管理员操作
	 * @param adminstratorList
	 */
	public void updateBankRoolAdministrator(UpdateAdminDto updateAdminDto){
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
							.status(TrascationStatusEum.SUCCESS_STATUS.getStatus().shortValue())
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
							.status(TrascationStatusEum.SUCCESS_STATUS.getStatus().shortValue())
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
							.status(TrascationStatusEum.SUCCESS_STATUS.getStatus().shortValue())
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
		
		Credentials credentials = Credentials.create(ledgerDto.getAdminPrikey());
		
		List<String> addressList = new ArrayList<String>();
		
		List<BigInteger> oldPointList = new ArrayList<BigInteger>();
		
		List<BigInteger> newPointList = new ArrayList<BigInteger>();
		
		ledgerDto.getDataList().forEach(v->{
			addressList.add(v.getAddress());
			oldPointList.add(new BigInteger(v.getOldPoints()));
			newPointList.add(new BigInteger(v.getNewPoints()));
		});
		
		//计算ehtgas
		Function function = new Function(
				Bankroll_sol_BankRoll.FUNC_UPDATELEDGER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(ledgerDto.getBusinessId()), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.Utils.typeMap(addressList, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.Utils.typeMap(oldPointList, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.Utils.typeMap(newPointList, org.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
		
		BigInteger gasPrice = Contract.GAS_PRICE;
		BigInteger gasLimit = Contract.GAS_LIMIT;
		
		EthEstimateGas ethEstimateGas = null;
		
		try {
			ethEstimateGas = web3j.ethEstimateGas(Transaction.createEthCallTransaction(ledgerDto.getAdminAddress(), bankroll_sol_BankRoll.getContractAddress(), FunctionEncoder.encode(function))).send();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(ethEstimateGas != null){
			gasPrice = ethEstimateGas.getAmountUsed();
			BigDecimal gasUp = BigDecimal.valueOf(gasPrice.longValue()).multiply(new BigDecimal("0.1"));
			
			gasLimit = gasPrice.add(gasUp.toBigInteger()) ;
		}
		
		Bankroll_sol_BankRoll bank = Bankroll_sol_BankRoll.load(bankroll_sol_BankRoll.getContractAddress(), web3j, credentials, gasPrice, gasLimit);
		
		//写入监听事件
		exchangeCoinsService.insertledgerEvent(LedgerEvent.builder()
				.businessId(ledgerDto.getBusinessId())
				.eventName(EventNameEum.LEDGER_RECORD_EVENT.getName())
				.createDate(new Date())
				.status(TrascationStatusEum.DEFAULT_STATUS.getStatus().shortValue())
				.build());
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			TransactionReceipt transactionReceipt = bank.updateLedger(BigInteger.valueOf(ledgerDto.getBusinessId()), addressList, oldPointList, newPointList).send();
			if(transactionReceipt !=null && transactionReceipt.isStatusOK()){
				contractOperationMapper.insert(ContractOperation.builder()
						.businessId(ledgerDto.getBusinessId())
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
	
	/**
	 * 查询eth换token  或 token 换eth
	 * @param queryEventDto
	 * @return
	 */
	public TranscationEventVo querySkcoinEvent(QueryEventDto queryEventDto){
		
		TranscationEventVo transcationEventVo = null;
		
		TranscationEvent transcationEvent = null;
		
		List<TranscationEvent> transcationList = transcationEventService.queryTranscationEventByTnHash(queryEventDto.getTnHash());
		if(transcationList == null || transcationList.size() == 0){
			try {
				EthGetTransactionReceipt receipt = web3j.ethGetTransactionReceipt(queryEventDto.getTnHash()).send();
				TransactionReceipt transcationReceipt = receipt.getResult();
				
				if(transcationReceipt == null || !transcationReceipt.isStatusOK()){
					transcationEventVo = TranscationEventVo.builder()
						.transactionHash(queryEventDto.getTnHash())
						.status(TrascationStatusEum.FAIL_STATUS.getStatus())
					.build();
				} else {
					if(queryEventDto.getEventName().equals(EventNameEum.ON_TOKEN_PURCHASE_EVENT.getName())){
						List<OnTokenPurchaseEventResponse> buyTokenList = skcoin_sol_Skcoin.getOnTokenPurchaseEvents(transcationReceipt);
						
						OnTokenPurchaseEventResponse v = buyTokenList.get(0);
						
						transcationEvent = TranscationEvent.builder()
							.blockHash(v.log.getBlockHash())
							.blockNumber(v.log.getBlockNumber().longValue())
							.createDate(new Date())
							.currency((short)0)
							.eventName(EventNameEum.ON_TOKEN_PURCHASE_EVENT.getName())
							.divChoice(v.divChoice.toString())
							.nums(v.incomingEthereum.toString())
							.referredBy(v.referrer)
							.status(TrascationStatusEum.SUCCESS_STATUS.getStatus().shortValue())
							.tokenPrice(v.tokenPrice.toString())
							.tokensMinted(v.tokensMinted.toString())
							.tradePerson(v.customerAddress)
							.transcationHash(v.log.getTransactionHash())
							.isCall((short)0)
						.build();
						//
						transcationEventService.insertTranscationEvent(transcationEvent);
					} else if (queryEventDto.getEventName().equals(EventNameEum.ON_TOKEN_SELL_EVENT.getName())){
						List<OnTokenSellEventResponse> sellTokenList = skcoin_sol_Skcoin.getOnTokenSellEvents(transcationReceipt);
						
						OnTokenSellEventResponse v = sellTokenList.get(0);
						
						transcationEvent = TranscationEvent.builder()
							.blockHash(v.log.getBlockHash())
							.blockNumber(v.log.getBlockNumber().longValue())
							.createDate(new Date())
							.currency((short)1)
							.eventName(EventNameEum.ON_TOKEN_SELL_EVENT.getName())
							.nums(v.tokensBurned.toString())
							.status(TrascationStatusEum.SUCCESS_STATUS.getStatus().shortValue())
							.tradePerson(v.customerAddress)
							.transcationHash(v.log.getTransactionHash())
							.ethMinted(v.ethereumEarned.toString())
							.isCall((short)0)
							.divChoice(v.divRate.toString())
						.build();
						//
						transcationEventService.insertTranscationEvent(transcationEvent);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else {
			transcationEvent = transcationList.get(0);
		}
		
		if(transcationEvent != null){
			transcationEventVo = getTranscationEventVo(transcationEvent);
		}
		
		
		return transcationEventVo;
	}
	
	/**
	 * 
	 * @param queryEventDto
	 * @return
	 */
	public TranscationEventVo queryBankRollEvents(QueryEventDto queryEventDto){
		
		ExchangeCoins exchangeCoins = null;
		
		TranscationEventVo transcationEventVo = null;
		
		List<ExchangeCoins> exchangesCoinsList = exchangeCoinsService.queryExchangeCoinsByBusinessId(queryEventDto.getBusinessId());
		
		if(exchangesCoinsList == null || exchangesCoinsList.size() == 0){
			try {
				EthGetTransactionReceipt receipt = web3j.ethGetTransactionReceipt(queryEventDto.getTnHash()).send();
				TransactionReceipt transcationReceipt = receipt.getResult();
				
				//未能查询到交易
				if(transcationReceipt == null || !transcationReceipt.isStatusOK()){
					transcationEventVo = TranscationEventVo.builder()
					.transactionHash(queryEventDto.getTnHash())
					.status(TrascationStatusEum.FAIL_STATUS.getStatus())
					.build();
					return transcationEventVo;
				}
				
				//TOKEN转积分
				if(queryEventDto.getEventName().equals(EventNameEum.REDEEM_EVENT.getName())){
					List<TokenToPointEventEventResponse> responseList = bankroll_sol_BankRoll.getTokenToPointEventEvents(transcationReceipt);
					
					//有交易 但没有日志 认定为失败
					if(responseList == null || responseList.size() == 0){
						transcationEventVo = TranscationEventVo.builder()
								.transactionHash(queryEventDto.getTnHash())
								.status(TrascationStatusEum.FAIL_STATUS.getStatus())
								.build();
						return transcationEventVo;
					}
					
					TokenToPointEventEventResponse v  = responseList.get(0);
					exchangeCoins = ExchangeCoins.builder()
						.blockHash(v.log.getBlockHash())
						.blockNumber(v.log.getBlockNumber().longValue())
						.callDate(new Date())
						.confirmBlockNum(0l)
						.createDate(new Date())
						.currency((short)1)
						.eventName(EventNameEum.REDEEM_EVENT.getName())
						.tradePerson(v._recharger)
						.gas(transcationReceipt.getGasUsed().toString())
						.isCall((short)1)
						.nums(v._amount.toString())
						.status(TrascationStatusEum.SUCCESS_STATUS.getStatus().shortValue())
						.transcationHash(v.log.getTransactionHash())
						.updateDate(new Date())
					.build();
					
					//成功记录写入数据库 
					exchangeCoinsService.insertExchangeCoins(exchangeCoins);
					
				}else if(queryEventDto.getEventName().equals(EventNameEum.WITHDRAW_EVENT.getName())){
					List<PointToTokenEventEventResponse> responseList = bankroll_sol_BankRoll.getPointToTokenEventEvents(transcationReceipt);
					
					//有交易 但没有日志 认定为失败
					if(responseList == null || responseList.size() == 0){
						transcationEventVo = TranscationEventVo.builder()
								.transactionHash(queryEventDto.getTnHash())
								.status(TrascationStatusEum.FAIL_STATUS.getStatus())
								.build();
						return transcationEventVo;
					}
					
					PointToTokenEventEventResponse v  = responseList.get(0);
					
					
					exchangeCoins = ExchangeCoins.builder()
						.businessId(v._id.longValue())
						.blockHash(v.log.getBlockHash())
						.blockNumber(v.log.getBlockNumber().longValue())
						.callDate(new Date())
						.confirmBlockNum(0l)
						.createDate(new Date())
						.currency((short)1)
						.eventName(EventNameEum.WITHDRAW_EVENT.getName())
						.tradePerson(v.sender)
						.gas(transcationReceipt.getGasUsed().toString())
						.isCall((short)1)
						.nums(v.amount.toString())
						.status(TrascationStatusEum.SUCCESS_STATUS.getStatus().shortValue())
						.transcationHash(v.log.getTransactionHash())
						.updateDate(new Date())
					.build();
					//成功记录写入数据库 
					exchangeCoinsService.insertExchangeCoins(exchangeCoins);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			exchangeCoins = exchangesCoinsList.get(0);
		}
		
		if(exchangeCoins != null){
			transcationEventVo = getTranscationEventVo(exchangeCoins);
		}
		
		return transcationEventVo;
	}
	
	
	/**
	 * 通过ExchangeCoins组装TranscationEventVo
	 * @param v
	 * @return
	 */
	public TranscationEventVo getTranscationEventVo(ExchangeCoins v){
		return TranscationEventVo.builder()
			.status(v.getStatus().intValue())
			.businessId(v.getBusinessId())
			.eventName(v.getEventName())
			.transactionHash(v.getTranscationHash())
			.tradePerson(v.getTradePerson())
			.gasUsed(v.getGas())
			.currency(v.getCurrency().intValue())
			.nums(v.getNums())
			.build();
	}
	
	/**
	 * 通过TranscationEvent 组装 TranscationEventVo
	 * @param v
	 * @return
	 */
	public TranscationEventVo getTranscationEventVo(TranscationEvent v){
		return TranscationEventVo.builder()
			.status(v.getStatus().intValue())
			.businessId(v.getBusinessId())
			.eventName(v.getEventName())
			.currency(v.getCurrency().intValue())
			.divChoice(v.getDivChoice())
			.ethMinted(v.getEthMinted() != null ? Convert.fromWei(v.getEthMinted(), Convert.Unit.ETHER).toPlainString() : null)
			.eventName(v.getEventName())
			.nums(v.getCurrency().equals((short)0) ? Convert.fromWei(v.getNums(),Convert.Unit.ETHER).toPlainString() : v.getNums())
			.referredBy(v.getReferredBy())
			.status(v.getStatus().intValue())
			.tokenPrice(v.getTokenPrice() != null ? Convert.fromWei(v.getTokenPrice(), Convert.Unit.ETHER).toPlainString() : null)
			.tokensMinted(v.getTokensMinted() != null ? Convert.fromWei(v.getTokensMinted(), Convert.Unit.ETHER).toPlainString() : null)
			.tradePerson(v.getTradePerson())
			.transactionHash(v.getTranscationHash())
			.gasUsed(v.getGas())
			.build();
	}
	
	/**
	 * 通过BonusEvent 组装 TranscationEventVo
	 * @param v
	 * @return
	 */
	public TranscationEventVo getTranscationEventVo(BonusEvent v){
		return TranscationEventVo.builder()
				.eventName(v.getEventName())
				.gasUsed(v.getGas())
				.status(v.getStatus().intValue())
				.tradePerson(v.getTradePerson())
				.referredBy(v.getReferrer())
				.referrerToken(v.getReferrerToken())
				.tokenHolder(v.getTokenHolder())
				.platformToken(v.getPlatformToken())
				.build();
	}
	
	/**
	 * 交易查询
	 * @param queryEventDto
	 * @return
	 */
	public TranscationEventVo queryTranscationEventDtoByParams(QueryEventDto queryEventDto){
		TranscationEventVo transcationEventVo = null;
		
		if(queryEventDto.getEventName().equals(EventNameEum.ON_TOKEN_SELL_EVENT.getName())
				|| queryEventDto.getEventName().equals(EventNameEum.ON_TOKEN_PURCHASE_EVENT.getName())){ 
			
			return querySkcoinEvent(queryEventDto);
			
		}else if (queryEventDto.getEventName().equals(EventNameEum.REDEEM_EVENT.getName()) 
				|| queryEventDto.getEventName().equals(EventNameEum.WITHDRAW_EVENT.getName())){
			
			return queryBankRollEvents(queryEventDto);
			
		}
		
		return transcationEventVo;
	}
	
	
	public void setSkcAdderss(SkcAddressDto skcAddressDto){
		try {
			
			//预计得到gas费用
			Function function = new Function(
					Bankroll_sol_BankRoll.FUNC_SETSKCADDERSS, 
	                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(skcAddressDto.getSkcAddress())), 
	                Collections.<TypeReference<?>>emptyList());
			
			BigInteger gasPrice = Contract.GAS_PRICE;
			BigInteger gasLimit = Contract.GAS_LIMIT;
			
			EthEstimateGas ethEstimateGas = null;
			
			try {
				ethEstimateGas = web3j.ethEstimateGas(Transaction.createEthCallTransaction(skcAddressDto.getAdminAddress(), bankroll_sol_BankRoll.getContractAddress(), FunctionEncoder.encode(function))).send();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(ethEstimateGas != null){
				gasPrice = ethEstimateGas.getAmountUsed();
				BigDecimal gasUp = BigDecimal.valueOf(gasPrice.longValue()).multiply(new BigDecimal("0.1"));
				
				gasLimit = gasPrice.add(gasUp.toBigInteger()) ;
			}
			
			Credentials credentials = Credentials.create(skcAddressDto.getAdminPrikey());
			Bankroll_sol_BankRoll bank = Bankroll_sol_BankRoll.load(bankroll_sol_BankRoll.getContractAddress(), web3j, credentials, gasPrice, gasLimit);
			
			TransactionReceipt transactionReceipt = bank.setSkcAdderss(skcAddressDto.getSkcAddress()).send();
			if(transactionReceipt !=null && transactionReceipt.isStatusOK()){
				
				contractOperationMapper.insert(ContractOperation.builder()
						.name(ContractEum.SET_SKCADDRESS.getName())
						.blockHash(transactionReceipt.getBlockHash())
						.blockNumber(transactionReceipt.getBlockNumber().longValue())
						.createDate(new Date())
						.data(skcAddressDto.getSkcAddress())
						.operationPerson(transactionReceipt.getFrom())
						.toPerson(transactionReceipt.getTo())
						.transcationHash(transactionReceipt.getTransactionHash())
						.gas(transactionReceipt.getGasUsed().longValue())
						.status((short)1)
						.build());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 手动分红
	 * @param divideDto
	 */
	public List<DivideVo> divide(DivideDto divideDto){
		List<DivideVo> divideVoList = new ArrayList<>();
		
		TransactionReceipt transactionReceipt = null;
		try {

			Function function = new Function(
					Skcoin_sol_Skcoin.FUNC_DIVIDE, 
	                Arrays.<Type>asList(), 
	                Collections.<TypeReference<?>>emptyList());
			
			BigInteger gasPrice = Contract.GAS_PRICE;
			BigInteger gasLimit = Contract.GAS_LIMIT;
			
			EthEstimateGas ethEstimateGas = null;
			
			try {
				ethEstimateGas = web3j.ethEstimateGas(Transaction.createEthCallTransaction(divideDto.getAdminAddress(), bankroll_sol_BankRoll.getContractAddress(), FunctionEncoder.encode(function))).send();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(ethEstimateGas != null){
				gasPrice = ethEstimateGas.getAmountUsed();
				BigDecimal gasUp = BigDecimal.valueOf(gasPrice.longValue()).multiply(new BigDecimal("0.1"));
				
				gasLimit = gasPrice.add(gasUp.toBigInteger()) ;
			}
			
			
			Credentials credentials = Credentials.create(divideDto.getAdminPrikey());
			Skcoin_sol_Skcoin skcoin = Skcoin_sol_Skcoin.load(skcoin_sol_Skcoin.getContractAddress(), web3j, credentials, gasPrice, gasLimit);
			
			transactionReceipt = skcoin.divide().send();
			
			if(transactionReceipt !=null && transactionReceipt.isStatusOK()){
				
				contractOperationMapper.insert(ContractOperation.builder()
						.name(ContractEum.SET_SKCADDRESS.getName())
						.blockHash(transactionReceipt.getBlockHash())
						.blockNumber(transactionReceipt.getBlockNumber().longValue())
						.createDate(new Date())
						.operationPerson(transactionReceipt.getFrom())
						.toPerson(transactionReceipt.getTo())
						.transcationHash(transactionReceipt.getTransactionHash())
						.gas(transactionReceipt.getGasUsed().longValue())
						.status((short)1)
						.build());
				
				List<DividendDetailEventResponse> list = skcoin.getDividendDetailEvents(transactionReceipt);
				
				list.forEach(v->{
					divideVoList.add(DivideVo.builder()
							.customerAddress(v.customerAddress)
							.referrerToken(v.referrerToken.toString())
							.tokenHolder(v.tokenHolder.toString())
							.build());
				});
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return divideVoList;
	}
	
	/**
	 * 获取余额
	 * @param address
	 * @return
	 */
	public String getEthBalance(String address){
		String balanceStr = null;
		try {
			
			EthGetBalance balance = web3j.ethGetBalance(address, DefaultBlockParameter.valueOf("latest")).send();
			balanceStr =  Convert.fromWei(balance.getBalance().toString(),Convert.Unit.ETHER).toPlainString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return balanceStr;
	}
	
	/**
	 * 
	 * @param addressArrayDto
	 * @return
	 */
	public List<EthBalanceVo> getEthBalances(AddressArrayDto addressArrayDto){
		
		List<EthBalanceVo> ethBalanceVoList = new ArrayList<EthBalanceVo>();
		
		List<String> addressList = addressArrayDto.getAddressList();
		for(String a : addressList){
			try {
				EthGetBalance balance =  web3j.ethGetBalance(a, DefaultBlockParameter.valueOf("latest")).send();
				
				String balanceStr =  Convert.fromWei(balance.getBalance().toString(),Convert.Unit.ETHER).toPlainString();
				
				ethBalanceVoList.add(EthBalanceVo.builder()
						.address(a)
						.balance(balanceStr)
						.build());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return ethBalanceVoList;
	}
	
	/**
	 * 得到发行量
	 * @return
	 */
	public String getTotalSupply(){
		BigInteger total = null;
		String totalSupply = null;
		try {
			total = skcoin_sol_Skcoin.totalSupply().send();
			
			totalSupply = Convert.fromWei(total.toString(),Convert.Unit.ETHER).toPlainString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalSupply;
	}
	
	/**
	 * 得到合约持有eth
	 * @return
	 */
	public String getTotalEthBalance(){
		BigInteger total = null;
		String totalBalance = null;
		try {
			total = skcoin_sol_Skcoin.totalEtherBalance().send();
			
			totalBalance = Convert.fromWei(total.toString(),Convert.Unit.ETHER).toPlainString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalBalance;
	}
	
	/**
	 * 得到目标地址TOKEN余额
	 * @param address
	 * @return
	 */
	public String getTokenBalance(String address){
		BigInteger total = null;
		String totalBalance = null;
		try {
			total = skcoin_sol_Skcoin.balanceOf(address).send();
			
			totalBalance = Convert.fromWei(total.toString(),Convert.Unit.ETHER).toPlainString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalBalance;
	}
	
}

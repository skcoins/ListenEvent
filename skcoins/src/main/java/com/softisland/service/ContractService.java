/**
 * 
 */
package com.softisland.service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;

import com.alibaba.fastjson.JSON;
import com.softisland.config.ContractEum;
import com.softisland.contract.Bankroll_sol_BankRoll;
import com.softisland.dto.LedgerDto;
import com.softisland.dto.UpdateAdminDto;
import com.softisland.mapper.ContractOperationMapper;
import com.softisland.model.ContractOperation;

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
	ContractOperationMapper contractOperationMapper;
	
	@Value("#{contract.address}")
	String contractAddress;
	
	@Value("#{contract.send-owner-prikey}")
	String sendOwnerPrikey;
	
	/**
	 * 管理员操作
	 * @param adminstratorList
	 */
	public void updateBankRoolAdministrator(UpdateAdminDto updateAdminDto){
		Credentials credentials = Credentials.create(sendOwnerPrikey);
		
		Bankroll_sol_BankRoll bank = Bankroll_sol_BankRoll.load(contractAddress, web3j, credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT);
		
		try {
			TransactionReceipt transactionReceipt = null;
			switch (updateAdminDto.getType()) {
			case 0:
				log.info("禁用管理员,管理员列表({})",JSON.toJSONString(updateAdminDto.getAdminstratorList()));
				
				transactionReceipt = bank.unsetAdministrator(updateAdminDto.getAdminstratorList()).send();
				
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
				
				transactionReceipt = bank.setAdministrator(updateAdminDto.getAdminstratorList()).send();
				
				if(transactionReceipt !=null && transactionReceipt.isStatusOK()){
					
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
				
				transactionReceipt = bank.replaceAdministrator(oldOwner, updateAdminDto.getNewAdmin()).send();
				
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
		Credentials credentials = Credentials.create("ceda59b2b97439952bb3c481123bc197ef2591b83ccf4686ee220d1cdc55519c");
		
		Bankroll_sol_BankRoll bank = Bankroll_sol_BankRoll.load(contractAddress, web3j, credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT);
		
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
			TransactionReceipt transactionReceipt = bank.updateLedger(new BigInteger(ledgerDto.getSerialNumber()), addressList, oldPointList, newPointList, format.format(new Date(ledgerDto.getDateTime()*1000))).send();
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
		Credentials credentials = Credentials.create("ceda59b2b97439952bb3c481123bc197ef2591b83ccf4686ee220d1cdc55519c");
		
		Bankroll_sol_BankRoll bank = Bankroll_sol_BankRoll.load(contractAddress, web3j, credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT);
		
		try {
			BigInteger ret = bank.point(address).send();
			point = ret.longValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return point;
	}
}

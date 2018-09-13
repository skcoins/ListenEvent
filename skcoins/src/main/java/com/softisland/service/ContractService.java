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
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;

import com.alibaba.fastjson.JSON;
import com.softisland.config.ContractEum;
import com.softisland.contract.Bankroll_sol_BankRoll;
import com.softisland.dto.LedgerDto;
import com.softisland.mapper.ContractOperationMapper;
import com.softisland.model.ContractOperation;

/**
 * @author Administrator
 *
 */
@Service
public class ContractService {

	@Autowired
	Web3j web3j;
	
	@Autowired
	Web3j web3jInfura;
	
	@Autowired
	ContractOperationMapper contractOperationMapper;
	
	
	public void updateBankRoolAdministrator(List<String> adminstratorList){
		Credentials credentials = Credentials.create("e72e616234938238d1275aa6b4554d218dbf9cc44c92754e8bcc7deb1496e7e2");
		
		Bankroll_sol_BankRoll bank = Bankroll_sol_BankRoll.load("0x4e2E80dA333760Bcb9BEB1DD9f1c216Ac51A5D73", web3j, credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT);
		
		try {
			
			TransactionReceipt transactionReceipt = bank.setAdministrator(adminstratorList).send();
			
			if(transactionReceipt !=null && transactionReceipt.isStatusOK()){
				
				contractOperationMapper.insert(ContractOperation.builder()
						.name(ContractEum.SET_ADMINISTRATOR.getName())
						.blockHash(transactionReceipt.getBlockHash())
						.blockNumber(transactionReceipt.getBlockNumber().longValue())
						.createDate(new Date())
						.data(JSON.toJSONString(adminstratorList))
						.operationPerson(transactionReceipt.getFrom())
						.toPerson(transactionReceipt.getTo())
						.transcationHash(transactionReceipt.getTransactionHash())
						.gas(transactionReceipt.getGasUsed().longValue())
						.build());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void updateLedger(LedgerDto ledgerDto){
		Credentials credentials = Credentials.create("ceda59b2b97439952bb3c481123bc197ef2591b83ccf4686ee220d1cdc55519c");
		
		Bankroll_sol_BankRoll bank = Bankroll_sol_BankRoll.load("0x4e2E80dA333760Bcb9BEB1DD9f1c216Ac51A5D73", web3j, credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT);
		
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
						.build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}

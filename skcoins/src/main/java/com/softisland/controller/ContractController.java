/**
 * 
 */
package com.softisland.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softisland.dto.DivideDto;
import com.softisland.dto.LedgerDto;
import com.softisland.dto.PointToTokenDto;
import com.softisland.dto.QueryEventDto;
import com.softisland.dto.UpdateAdminDto;
import com.softisland.exception.SkcoinException;
import com.softisland.service.ContractService;
import com.softisland.vo.Message;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/contract")
public class ContractController {

	@Autowired
	ContractService contractService;
	
	@ResponseBody
	@RequestMapping("/queryTransactionEvent")
	public Message queryTrasactionEvent(@RequestBody QueryEventDto queryEventDto){
		
		Message message = Message.builder()
				.ret(Message.SUCCESS)
				.code("200")
				.msg("成功")
				.build();
		
		try {
			message.setData(contractService.queryTranscationEventDtoByParams(queryEventDto));
		} catch (Exception e) {
			message.setRet(Message.FAILED);
			message.setCode("500");
			message.setMsg("服务器错误，请稍后再试");
			e.printStackTrace();
		}
		
		return message;
	}
	
	@ResponseBody
	@RequestMapping("/updateBankRoolAdministrator")
	public Message updateBankRoolAdministrator(@RequestBody UpdateAdminDto updateAdminDto){
		
		Message message = Message.builder()
				.ret(Message.SUCCESS)
				.code("200")
				.msg("成功")
				.build();
		
		try {
			contractService.updateBankRoolAdministrator(updateAdminDto);
		} catch (Exception e) {
			message.setRet(Message.FAILED);
			message.setCode("500");
			message.setMsg("服务器错误，请稍后再试");
			e.printStackTrace();
		}
		
		return message;
	}
	
	@ResponseBody
	@RequestMapping("/updateLedger")
	public Message updateLedger(@RequestBody LedgerDto ledgerDto){
		
		Message message = Message.builder()
				.ret(Message.SUCCESS)
				.code("200")
				.msg("成功")
				.build();
		
		try {
			contractService.updateLedger(ledgerDto);
		} catch (SkcoinException e) {
			message.setRet(Message.SUCCESS);
			message.setCode(e.getMessage().substring(0,e.getMessage().indexOf("_")));
			message.setMsg(e.getMessage().substring(e.getMessage().indexOf("_")+1,e.getMessage().length()));
			e.printStackTrace();
		} catch (Exception e) {
			message.setRet(Message.FAILED);
			message.setCode("500");
			message.setMsg("服务器错误，请稍后再试");
			e.printStackTrace();
		}
		
		return message;
	}
	
	@ResponseBody
	@RequestMapping("/divide")
	public Message divide(@RequestBody DivideDto divideDto){
		Message message = Message.builder()
				.ret(Message.SUCCESS)
				.code("200")
				.msg("成功")
				.build();
		
		try {
			message.setData(contractService.divide(divideDto));
		} catch (SkcoinException e) {
			message.setRet(Message.SUCCESS);
			message.setCode(e.getMessage().substring(0,e.getMessage().indexOf("_")));
			message.setMsg(e.getMessage().substring(e.getMessage().indexOf("_")+1,e.getMessage().length()));
			e.printStackTrace();
		} catch (Exception e) {
			message.setRet(Message.FAILED);
			message.setCode("500");
			message.setMsg("服务器错误，请稍后再试");
			e.printStackTrace();
		}
		
		return message;
	}
	
	@ResponseBody
	@RequestMapping("/getEthBalance")
	public Message getEthBalance(@RequestParam(value = "address" , required = true)String address){
		Message message = Message.builder()
				.ret(Message.SUCCESS)
				.code("200")
				.msg("成功")
				.build();
		try {
			message.setData(contractService.getEthBalance(address));
		} catch (Exception e) {
			message.setRet(Message.FAILED);
			message.setCode("500");
			message.setMsg("服务器错误，请稍后再试");
			e.printStackTrace();
		}
		
		return message;
	}
	
	@ResponseBody
	@RequestMapping("/getTotalSupply")
	public Message getTotalSupply(){
		Message message = Message.builder()
				.ret(Message.SUCCESS)
				.code("200")
				.msg("成功")
				.build();
		try {
			message.setData(contractService.getTotalSupply());
		} catch (Exception e) {
			message.setRet(Message.FAILED);
			message.setCode("500");
			message.setMsg("服务器错误，请稍后再试");
			e.printStackTrace();
		}
		
		return message;
	}
	
	@ResponseBody
	@RequestMapping("/getTotalEthBalance")
	public Message getTotalEthBalance(){
		Message message = Message.builder()
				.ret(Message.SUCCESS)
				.code("200")
				.msg("成功")
				.build();
		try {
			message.setData(contractService.getTotalEthBalance());
		} catch (Exception e) {
			message.setRet(Message.FAILED);
			message.setCode("500");
			message.setMsg("服务器错误，请稍后再试");
			e.printStackTrace();
		}
		
		return message;
	}
	
	@ResponseBody
	@RequestMapping("/getTokenBalance")
	public Message getTokenBalance(@RequestParam(value = "address" , required = true)String address){
		Message message = Message.builder()
				.ret(Message.SUCCESS)
				.code("200")
				.msg("成功")
				.build();
		try {
			message.setData(contractService.getTokenBalance(address));
		} catch (Exception e) {
			message.setRet(Message.FAILED);
			message.setCode("500");
			message.setMsg("服务器错误，请稍后再试");
			e.printStackTrace();
		}
		
		return message;
	}
	
	@ResponseBody
	@RequestMapping("/pointToToken")
	public Message pointToToken(@RequestBody PointToTokenDto pointToTokenDto){
		Message message = Message.builder()
				.ret(Message.SUCCESS)
				.code("200")
				.msg("成功")
				.build();
		try {
			contractService.pointToToken(pointToTokenDto);
		} catch (SkcoinException e) {
			message.setRet(Message.SUCCESS);
			message.setCode(e.getMessage().substring(0,e.getMessage().indexOf("_")));
			message.setMsg(e.getMessage().substring(e.getMessage().indexOf("_")+1,e.getMessage().length()));
			e.printStackTrace();
		} catch (Exception e) {
			message.setRet(Message.FAILED);
			message.setCode("500");
			message.setMsg("服务器错误，请稍后再试");
			e.printStackTrace();
		}
		
		return message;
	}
	
	@ResponseBody
	@RequestMapping("/setTestTotalSupply")
	public Message setTestTotalSupply(@RequestParam(value="totalSupply",required = true)String totalSupply){
		Message message = Message.builder()
				.ret(Message.SUCCESS)
				.code("200")
				.msg("成功")
				.build();
		try {
			message.setData(contractService.setTestTotalSupply(totalSupply));
		}   catch (Exception e) {
			message.setRet(Message.FAILED);
			message.setCode("500");
			message.setMsg("服务器错误，请稍后再试");
			e.printStackTrace();
		}
		
		return message;
	}
}

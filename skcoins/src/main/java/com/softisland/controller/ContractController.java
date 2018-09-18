/**
 * 
 */
package com.softisland.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softisland.dto.QueryEventDto;
import com.softisland.dto.UpdateAdminDto;
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
	@RequestMapping("/queryTrascationEvent")
	public Message queryTrascationEvent(QueryEventDto queryEventDto){
		
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
	public Message updateBankRoolAdministrator(UpdateAdminDto updateAdminDto){
		
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
}

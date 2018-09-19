package com.softisland.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web3j.protocol.Web3j;

import com.softisland.dto.SignDto;
import com.softisland.util.CryptoUtils;
import com.softisland.vo.Message;

/**
 * 登录签名
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/metaMask")
public class SignController {

	
	/**
	 * 验证签名
	 * @param signDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sign")
	public Message sign(SignDto signDto){

		Message message = Message.builder()
				.ret(Message.SUCCESS)
				.code("200")
				.msg("成功")
				.build();
		
		try {
			String address = CryptoUtils.validate(signDto.getSignedHash(), signDto.getMessage());
			if(address.equals(signDto.getAddress())){
				message.setData(true);
			}else{
				message.setData(false);
			}
			
		} catch (Exception e) {
			message.setRet(Message.FAILED);
			message.setCode("500");
			message.setMsg("服务器错误，请稍后再试");
			e.printStackTrace();
		}
		return message;
	}
}

package com.dy.platform.pay.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dy.platform.pay.dto.PayOrderDTO;
import com.dy.platform.pay.service.AliPayInfoService;
import com.egzosn.pay.common.bean.PayOrder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/aliPay")
@Api("阿里支付管理")
@Slf4j
public class AliPayController {

	@Autowired
	private AliPayInfoService aliPayInfoService;

	@ApiOperation("返回创建的订单信息")
	@PostMapping("payOrder")
	public ResponseEntity<Map<String, Object>> payOrder(PayOrderDTO payOrderDTO) {
		return ResponseEntity.ok(aliPayInfoService.payOrder(payOrderDTO));
	}
	
	@ApiOperation("跳转支付页二维码支付")
	@PostMapping(value = "aliToPay")
	public String aliToPay(@RequestBody PayOrder order) throws IOException {
		return aliPayInfoService.aliToPay(order);
	}
	
	@ApiOperation("支付回调接口")
	@PostMapping("payNotify")
	public String payNotify(HttpServletRequest request) {
		try {
			return aliPayInfoService.payNotify(request);
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}
	
	@GetMapping("test")
	public String test() {
		return "test";
	}
}

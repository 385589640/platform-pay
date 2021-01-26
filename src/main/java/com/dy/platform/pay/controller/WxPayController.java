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
import com.dy.platform.pay.service.WxPayInfoService;
import com.egzosn.pay.common.bean.PayOrder;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/pay")
@Slf4j
public class WxPayController {

	@Autowired
	private WxPayInfoService wxPayInfoService;

	/**
	 * 获取支付预订单信息
	 *
	 * @return 支付预订单信息
	 */
	@PostMapping("payOrder")
	public ResponseEntity<Map<String, Object>> payOrder(PayOrderDTO payOrderDTO) {
		return ResponseEntity.ok(wxPayInfoService.payOrder(payOrderDTO));
	}

	/**
	 * 获取二维码图像 二维码支付
	 * 
	 * @param price 金额
	 * @return 二维码图像
	 * @throws IOException IOException
	 */
	@PostMapping(value = "toQrPay.jpg", produces = "image/jpeg;charset=UTF-8")
	public byte[] toWxQrPay(@RequestBody PayOrder order) throws IOException {
		return wxPayInfoService.toWxQrPay(order);
	}
	
	/**
	 * 获取二维码连接 二维码支付
	 * 
	 * @throws IOException IOException
	 */
	@PostMapping(value = "getWxQrPay")
	public String getWxQrPay(@RequestBody PayOrder order) throws IOException {
		return wxPayInfoService.getWxQrPay(order);
	}
	
	@PostMapping("payNotify")
	public String payNotify(HttpServletRequest request) {
		try {
			return wxPayInfoService.payNotify(request);
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

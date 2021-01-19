package com.dy.platform.pay.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dy.platform.pay.dto.PayOrderDTO;
import com.dy.platform.pay.service.WxPayInfoService;
import com.egzosn.pay.common.bean.PayOrder;
import com.egzosn.pay.wx.bean.WxTransactionType;

@RestController
@RequestMapping("/pay")
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
	@RequestMapping(value = "toQrPay.jpg", produces = "image/jpeg;charset=UTF-8")
	public byte[] toWxQrPay(BigDecimal price) throws IOException {
		// 获取对应的支付账户操作工具（可根据账户id）
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(wxPayInfoService.genQrPay(new PayOrder("订单title", "摘要", null == price ? BigDecimal.valueOf(0.01) : price,
				System.currentTimeMillis() + "", WxTransactionType.NATIVE)), "JPEG", baos);
		return baos.toByteArray();
	}
}

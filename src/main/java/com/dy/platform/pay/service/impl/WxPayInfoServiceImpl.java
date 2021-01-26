package com.dy.platform.pay.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.dy.platform.pay.dto.PayOrderDTO;
import com.dy.platform.pay.service.WxPayInfoService;
import com.egzosn.pay.common.bean.PayOrder;
import com.egzosn.pay.wx.api.WxPayService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WxPayInfoServiceImpl implements WxPayInfoService {

//	@Autowired
//	private PayServiceManager payServiceManager;

	@Autowired
	private WxPayService wxPayService;

	@Override
	public Map<String, Object> payOrder(PayOrderDTO payOrderDTO) {
		return wxPayService.orderInfo(payOrderDTO);
	}

	@Override
	public byte[] toWxQrPay(PayOrder order) throws IOException {
		// 获取对应的支付账户操作工具（可根据账户id）
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
//		PayOrder order = new PayOrder("订单title", "摘要", null == price ? BigDecimal.valueOf(0.01) : price,
//				System.currentTimeMillis() + "", WxTransactionType.NATIVE);
		BufferedImage bufferedImage = wxPayService.genQrPay(order);
		ImageIO.write(bufferedImage,"JPEG", baos);
//		String wxUrl = wxPayService.getQrPay(order);
		return baos.toByteArray();
	}

	@Override
	public String getWxQrPay(PayOrder order) {
//		PayOrder order = new PayOrder("订单title", "摘要", null == price ? BigDecimal.valueOf(0.01) : price,
//				System.currentTimeMillis() + "", WxTransactionType.NATIVE);
		return wxPayService.getQrPay(order);
	}

	@Override
	public String payNotify(HttpServletRequest request) throws IOException {
		if(null != request.getParameterMap())
			log.info(JSON.toJSONString("payNotify------request.getParameterMap()------" + request.getParameterMap()));
		String message = wxPayService.payBack(request.getParameterMap(), request.getInputStream()).toMessage();
		log.info("payNotify------" + message);
		return message;
	}

}

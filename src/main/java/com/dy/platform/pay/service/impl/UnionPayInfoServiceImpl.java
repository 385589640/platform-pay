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
import com.dy.platform.pay.service.UnionPayInfoService;
import com.egzosn.pay.common.bean.PayOrder;
import com.egzosn.pay.common.bean.RefundOrder;
import com.egzosn.pay.union.api.UnionPayService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UnionPayInfoServiceImpl implements UnionPayInfoService {

//	@Autowired
//	private PayServiceManager payServiceManager;

	@Autowired
	private UnionPayService unionPayService;

	@Override
	public Map<String, Object> payOrder(PayOrderDTO payOrderDTO) {
		return unionPayService.orderInfo(payOrderDTO);
	}
	
	@Override
	public Map<String, Object> payQuery(String transactionId, String outTradeNo) {
		return unionPayService.query(transactionId, outTradeNo);
	}
	
	@Override
	public Map<String, Object> appPay(PayOrder order) {
		return unionPayService.app(order);
	}

	@Override
	public byte[] toUnionQrPay(PayOrder order) throws IOException {
		// 获取对应的支付账户操作工具（可根据账户id）
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
//		PayOrder order = new PayOrder("订单title", "摘要", null == price ? BigDecimal.valueOf(0.01) : price,
//				System.currentTimeMillis() + "", WxTransactionType.NATIVE);
		BufferedImage bufferedImage = unionPayService.genQrPay(order);
		ImageIO.write(bufferedImage,"JPEG", baos);
		return baos.toByteArray();
	}

	@Override
	public String getUnionQrPay(PayOrder order) {
//		PayOrder order = new PayOrder("订单title", "摘要", null == price ? BigDecimal.valueOf(0.01) : price,
//				System.currentTimeMillis() + "", WxTransactionType.NATIVE);
		return unionPayService.getQrPay(order);
	}

	@Override
	public String payNotify(HttpServletRequest request) throws IOException {
		if(null != request.getParameterMap())
			log.info("payNotify------request.getParameterMap()------" + JSON.toJSONString(request.getParameterMap()));
		String message = unionPayService.payBack(request.getParameterMap(), request.getInputStream()).toMessage();
		log.info("payNotify------" + message);
		return message;
	}

	@Override
	public Map<String, Object> payRefund(RefundOrder refundOrder) {
		return unionPayService.refund(refundOrder);
	}

	@Override
	public Map<String, Object> unionRefundQueryInfo(RefundOrder order) {
		return unionPayService.refundquery(order);
	}

	@Override
	public String refundNotify(HttpServletRequest request) {
		
		return null;
	}

}

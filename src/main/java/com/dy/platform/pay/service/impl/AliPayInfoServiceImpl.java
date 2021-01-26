package com.dy.platform.pay.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.dy.platform.pay.dto.PayOrderDTO;
import com.dy.platform.pay.service.AliPayInfoService;
import com.egzosn.pay.ali.api.AliPayConfigStorage;
import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.ali.bean.AliTransactionType;
import com.egzosn.pay.common.bean.PayOrder;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AliPayInfoServiceImpl implements AliPayInfoService {

//	@Autowired
//	private PayServiceManager payServiceManager;

	@Autowired
	private AliPayService aliPayService;

	@Override
	public Map<String, Object> payOrder(PayOrderDTO payOrderDTO) {
		return aliPayService.orderInfo(payOrderDTO);
	}

	@Override
	public byte[] toAliQrPay(PayOrder order) throws IOException {
		// 获取对应的支付账户操作工具（可根据账户id）
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
//		PayOrder order = new PayOrder("订单title", "摘要", null == price ? BigDecimal.valueOf(0.01) : price,
//				System.currentTimeMillis() + "", WxTransactionType.NATIVE);
		BufferedImage bufferedImage = aliPayService.genQrPay(order);
		ImageIO.write(bufferedImage,"JPEG", baos);
//		String wxUrl = wxPayService.getQrPay(order);
		return baos.toByteArray();
	}

	@Override
	public String aliToPay(PayOrder order) {
		order.setTransactionType(AliTransactionType.PAGE);
        String returnUrl = order.getWapUrl();
        if(!StringUtils.isEmpty(returnUrl)){
            AliPayConfigStorage payConfigStorage = aliPayService.getPayConfigStorage();
            payConfigStorage.setReturnUrl(returnUrl);
        }
        return aliPayService.toPay(order);
    }

	@Override
	public String payNotify(HttpServletRequest request) throws IOException {
		if(null != request.getParameterMap())
			log.info("payNotify------request.getParameterMap()------" + JSON.toJSONString(request.getParameterMap()));
		String message = aliPayService.payBack(request.getParameterMap(), request.getInputStream()).toMessage();
		log.info("payNotify------" + message);
		return message;
	}

}

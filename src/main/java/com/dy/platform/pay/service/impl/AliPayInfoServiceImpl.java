package com.dy.platform.pay.service.impl;

import java.io.IOException;
import java.util.Map;

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
import com.egzosn.pay.common.bean.RefundOrder;

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
	public Map<String, Object> payQuery(String tradeNo, String outTradeNo) {
		return aliPayService.query(tradeNo, outTradeNo);
	}
	
	@Override
	public Map<String, Object> appPay(PayOrder order) {
		order.setTransactionType(AliTransactionType.APP);
		return aliPayService.app(order);
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

	@Override
	public Map<String, Object> payRefund(RefundOrder refundOrder) {
		return aliPayService.refund(refundOrder);
	}

	@Override
	public Map<String, Object> aliRefundQueryInfo(RefundOrder order) {
		return aliPayService.refundquery(order);
	}

	@Override
	public Map<String, Object> tradeClose(String tradeNo, String outTradeNo) {
		return aliPayService.close(tradeNo, outTradeNo);
	}

}

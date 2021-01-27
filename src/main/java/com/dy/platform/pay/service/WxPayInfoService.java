package com.dy.platform.pay.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dy.platform.pay.dto.PayOrderDTO;
import com.egzosn.pay.common.bean.PayOrder;
import com.egzosn.pay.common.bean.RefundOrder;

public interface WxPayInfoService {

	Map<String, Object> payOrder(PayOrderDTO payOrderDTO);

	byte[] toWxQrPay(PayOrder order) throws IOException;

	String getWxQrPay(PayOrder order);

	String payNotify(HttpServletRequest request) throws IOException;

	Map<String, Object> appPay(PayOrder order);

	Map<String, Object> payQuery(String transactionId, String outTradeNo);

	Map<String, Object> payRefund(RefundOrder refundOrder);

	Map<String, Object> wxRefundQueryInfo(RefundOrder order);

	String refundNotify(HttpServletRequest request);

}

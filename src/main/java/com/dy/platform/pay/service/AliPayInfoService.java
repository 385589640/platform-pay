package com.dy.platform.pay.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dy.platform.pay.dto.PayOrderDTO;
import com.egzosn.pay.common.bean.PayOrder;
import com.egzosn.pay.common.bean.RefundOrder;

public interface AliPayInfoService {

	Map<String, Object> payOrder(PayOrderDTO payOrderDTO);

	String aliToPay(PayOrder order);

	String payNotify(HttpServletRequest request) throws IOException;

	Map<String, Object> appPay(PayOrder order);

	Map<String, Object> payQuery(String tradeNo, String outTradeNo);

	Map<String, Object> payRefund(RefundOrder refundOrder);

	Map<String, Object> aliRefundQueryInfo(RefundOrder order);

	Map<String, Object> tradeClose(String tradeNo, String outTradeNo);

}

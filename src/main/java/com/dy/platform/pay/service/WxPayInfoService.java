package com.dy.platform.pay.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dy.platform.pay.dto.PayOrderDTO;
import com.egzosn.pay.common.bean.PayOrder;

public interface WxPayInfoService {

	Map<String, Object> payOrder(PayOrderDTO payOrderDTO);

	byte[] toWxQrPay(PayOrder order) throws IOException;

	String getWxQrPay(PayOrder order);

	String payNotify(HttpServletRequest request) throws IOException;

}

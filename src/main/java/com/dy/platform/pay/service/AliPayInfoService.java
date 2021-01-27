package com.dy.platform.pay.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dy.platform.pay.dto.PayOrderDTO;
import com.egzosn.pay.common.bean.PayOrder;

public interface AliPayInfoService {

	Map<String, Object> payOrder(PayOrderDTO payOrderDTO);

	String aliToPay(PayOrder order);

	String payNotify(HttpServletRequest request) throws IOException;

}

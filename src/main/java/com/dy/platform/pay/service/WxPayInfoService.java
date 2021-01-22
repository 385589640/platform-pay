package com.dy.platform.pay.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import com.dy.platform.pay.dto.PayOrderDTO;

public interface WxPayInfoService {

	Map<String, Object> payOrder(PayOrderDTO payOrderDTO);

	byte[] toWxQrPay(BigDecimal price) throws IOException;

}

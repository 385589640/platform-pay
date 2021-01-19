package com.dy.platform.pay.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.dy.platform.pay.dto.PayOrderDTO;
import com.dy.platform.pay.service.WxPayInfoService;
import com.egzosn.pay.wx.api.WxPayService;

public class WxPayInfoServiceImpl implements WxPayInfoService {
	
//	@Autowired
//	private PayServiceManager payServiceManager;

	@Autowired
	private WxPayService wxPayService;

	@Override
	public Map<String, Object> payOrder(PayOrderDTO payOrderDTO) {
		return wxPayService.orderInfo(payOrderDTO);
	}
	
}

package com.dy.platform.pay.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.PayOutMessage;
import com.egzosn.pay.common.exception.PayErrorException;
import com.egzosn.pay.wx.bean.WxPayMessage;

/**
 * 微信支付回调处理器
 * 
 */
@Component
public class WxPayMessageHandler extends BasePayMessageHandler<WxPayMessage, PayService> {
	Logger logger = LoggerFactory.getLogger(WxPayMessageHandler.class);

	@Override
	public PayOutMessage handle(WxPayMessage payMessage, Map<String, Object> context, PayService payService)
			throws PayErrorException {
		logger.info("---------------------->wx-回调成功-start");
		if (null != payMessage)
			logger.info("payMessage------" + JSON.toJSONString(payMessage));
		if (null != payMessage.getPayMessage())
			logger.info("payMessage.getPayMessage()------" + JSON.toJSONString(payMessage.getPayMessage()));
		if (null != context)
			logger.info("context------" + JSON.toJSONString(context));
		// 交易状态
		if ("SUCCESS".equals(payMessage.getPayMessage().get("result_code"))) {
			///// 这里进行成功的处理
			logger.info("---------------------->wx-回调成功-SUCCESS");
			return payService.getPayOutMessage("SUCCESS", "OK");
		}
		logger.info("---------------------->wx-回调成功-FAIL");
		return payService.getPayOutMessage("FAIL", "失败");
	}
}

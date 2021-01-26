package com.dy.platform.pay.handler;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.ali.bean.AliPayMessage;
import com.egzosn.pay.common.api.PayMessageHandler;
import com.egzosn.pay.common.bean.PayOutMessage;
import com.egzosn.pay.common.exception.PayErrorException;

import lombok.extern.slf4j.Slf4j;

/**
 * 支付宝支付回调处理器
 *
 */
@Component
@Slf4j
public class AliPayMessageHandler implements PayMessageHandler<AliPayMessage, AliPayService> {

	/**
	 ** 处理支付回调消息的处理器接口
	 *
	 * @param payMessage 支付消息
	 * @param context    上下文，如果handler或interceptor之间有信息要传递，可以用这个
	 * @param payService 支付服务
	 * @return xml, text格式的消息，如果在异步规则里处理的话，可以返回null
	 * @throws PayErrorException 支付错误异常
	 */
	@Override
	public PayOutMessage handle(AliPayMessage payMessage, Map<String, Object> context, AliPayService payService)
			throws PayErrorException {
		log.info("---------------------->ali-回调成功-start");
		Object payId = payService.getPayConfigStorage().getAttach();
		Map<String, Object> message = payMessage.getPayMessage();
		if (message != null) {
			// 交易状态
			String trade_status = (String) message.get("trade_status");
			String out_trade_no = (String) message.get("out_trade_no");
			String trade_no = (String) message.get("trade_no");
			String total_amount = (String) message.get("total_amount");
			String gmt_refund_pay = (String) message.get("gmt_refund");
			String refund_fee = (String) message.get("refund_fee");
			String passbackParams = (String) message.get("passback_params");
			String receipt_amount = (String) message.get("receipt_amount");
			// 付款时间
			String gmtPayment = (String) message.get("gmtPayment");
			log.info("payMessage.getPayMessage()------" + JSON.toJSONString(message));
			// 交易完成
			if (StringUtils.isEmpty(refund_fee)) {
				if ("TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status)) {
					log.info("---------------------->ali-回调成功-SUCCESS");
					return payService.getPayOutMessage("success", "成功");
				}
			}
		}
		log.info("---------------------->ali-回调成功-FAIL");
		return payService.getPayOutMessage("fail", "失败");
	}
}

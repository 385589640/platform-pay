package com.dy.platform.pay.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.dy.platform.pay.dto.PayOrderDTO;
import com.dy.platform.pay.service.WxPayInfoService;
import com.egzosn.pay.common.bean.PayOrder;
import com.egzosn.pay.common.bean.RefundOrder;
import com.egzosn.pay.wx.api.WxPayService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WxPayInfoServiceImpl implements WxPayInfoService {

//	@Autowired
//	private PayServiceManager payServiceManager;

	@Autowired
	private WxPayService wxPayService;

	@Override
	public Map<String, Object> payOrder(PayOrderDTO payOrderDTO) {
		return wxPayService.orderInfo(payOrderDTO);
	}
	
	@Override
	public Map<String, Object> payQuery(String transactionId, String outTradeNo) {
		return wxPayService.query(transactionId, outTradeNo);
	}
	
	@Override
	public Map<String, Object> appPay(PayOrder order) {
		return wxPayService.app(order);
	}

	@Override
	public byte[] toWxQrPay(PayOrder order) throws IOException {
		// 获取对应的支付账户操作工具（可根据账户id）
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
//		PayOrder order = new PayOrder("订单title", "摘要", null == price ? BigDecimal.valueOf(0.01) : price,
//				System.currentTimeMillis() + "", WxTransactionType.NATIVE);
		BufferedImage bufferedImage = wxPayService.genQrPay(order);
		ImageIO.write(bufferedImage,"JPEG", baos);
		return baos.toByteArray();
	}

	@Override
	public String getWxQrPay(PayOrder order) {
//		PayOrder order = new PayOrder("订单title", "摘要", null == price ? BigDecimal.valueOf(0.01) : price,
//				System.currentTimeMillis() + "", WxTransactionType.NATIVE);
		return wxPayService.getQrPay(order);
	}

	@Override
	public String payNotify(HttpServletRequest request) throws IOException {
		if(null != request.getParameterMap())
			log.info("payNotify------request.getParameterMap()------" + JSON.toJSONString(request.getParameterMap()));
		String message = wxPayService.payBack(request.getParameterMap(), request.getInputStream()).toMessage();
		log.info("payNotify------" + message);
		return message;
	}
	
	@Override
	public String payNotifyV2(HttpServletRequest request) throws IOException {
		//获取支付方返回的对应参数
        Map<String, Object> params = wxPayService.getParameter2Map(request.getParameterMap(), request.getInputStream());
        if (null == params){
            return wxPayService.getPayOutMessage("fail","失败").toMessage();
        }

        //校验
        if (wxPayService.verify(params)){
            //这里处理业务逻辑
            return  wxPayService.getPayOutMessage("success", "成功").toMessage();
        }

        return wxPayService.getPayOutMessage("fail","失败").toMessage();
	}

	@Override
	public Map<String, Object> payRefund(RefundOrder refundOrder) {
		return wxPayService.refund(refundOrder);
	}

	@Override
	public Map<String, Object> wxRefundQueryInfo(RefundOrder order) {
		return wxPayService.refundquery(order);
	}

	@Override
	public String refundNotify(HttpServletRequest request) {
		if(null != request.getParameterMap())
			log.info("rePayNotify------request.getParameterMap()------" + JSON.toJSONString(request.getParameterMap()));
		log.info("退款回调通知------------------------------------------------------------------------------------");
		return null;
	}

}

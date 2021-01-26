package com.dy.platform.pay.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dy.platform.pay.dto.PayOrderDTO;
import com.dy.platform.pay.service.WxPayInfoService;
import com.egzosn.pay.common.bean.PayOrder;
import com.egzosn.pay.wx.api.WxPayService;

@Service
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
	public byte[] toWxQrPay(PayOrder order) throws IOException {
		// 获取对应的支付账户操作工具（可根据账户id）
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
//		PayOrder order = new PayOrder("订单title", "摘要", null == price ? BigDecimal.valueOf(0.01) : price,
//				System.currentTimeMillis() + "", WxTransactionType.NATIVE);
		BufferedImage bufferedImage = wxPayService.genQrPay(order);
		ImageIO.write(bufferedImage,"JPEG", baos);
//		String wxUrl = wxPayService.getQrPay(order);
		return baos.toByteArray();
	}

	@Override
	public String getWxQrPay(PayOrder order) {
//		PayOrder order = new PayOrder("订单title", "摘要", null == price ? BigDecimal.valueOf(0.01) : price,
//				System.currentTimeMillis() + "", WxTransactionType.NATIVE);
		return wxPayService.getQrPay(order);
	}

}

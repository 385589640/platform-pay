package com.dy.platform.pay.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dy.platform.pay.dto.PayOrderDTO;
import com.dy.platform.pay.service.WxPayInfoService;
import com.egzosn.pay.common.bean.PayOrder;
import com.egzosn.pay.common.bean.RefundOrder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/wxPay")
@Api(tags = "微信支付管理")
@Slf4j
public class WxPayController {

	@Autowired
	private WxPayInfoService wxPayInfoService;


	@ApiOperation("返回创建的订单信息")
	@PostMapping("payOrder")
	public ResponseEntity<Map<String, Object>> payOrder(@RequestBody PayOrderDTO payOrderDTO) {
		return ResponseEntity.ok(wxPayInfoService.payOrder(payOrderDTO));
	}
	
	/**
	 * 
	 * @param transactionId 微信支付平台订单号
	 * @param outTradeNo 商户单号
	 * @return
	 */
	@ApiOperation("支付交易查询")
	@PostMapping("payQuery")
	public Map<String, Object> payQuery(@RequestParam("transactionId") String transactionId, 
			@RequestParam("outTradeNo") String outTradeNo){
		return wxPayInfoService.payQuery(transactionId, outTradeNo);
	}
	
	@ApiOperation("app支付")
	@PostMapping("appPay")
	public Map<String, Object> appPay(@RequestBody PayOrder order){
		return wxPayInfoService.appPay(order);
	}

	@ApiOperation("获取二维码图像支付")
	@PostMapping(value = "toQrPay.jpg", produces = "image/jpeg;charset=UTF-8")
	public byte[] toWxQrPay(@RequestBody PayOrder order) throws IOException {
		return wxPayInfoService.toWxQrPay(order);
	}
	
	@ApiOperation("获取二维码连接支付")
	@PostMapping(value = "getWxQrPay")
	public String getWxQrPay(@RequestBody PayOrder order) throws IOException {
		return wxPayInfoService.getWxQrPay(order);
	}
	
	@ApiOperation("支付回调接口")
	@PostMapping("payNotify")
	public String payNotify(HttpServletRequest request) {
		try {
			return wxPayInfoService.payNotify(request);
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}
	
	@ApiOperation(value = "微信支付退款申请", notes = "微信支付退款申请")
    @PostMapping(value = "/payRefund")
    public Map<String, Object> payRefund(@RequestBody RefundOrder refundOrder) {
        return wxPayInfoService.payRefund(refundOrder);
    }

    @ApiOperation(value = "微信支付退款查询", notes = "微信支付退款查询")
    @PostMapping(value = "/refundQuery")
    public Map<String, Object> refundQuery(RefundOrder order) {
        return wxPayInfoService.wxRefundQueryInfo(order);
    }
    
    @ApiOperation(value = "微信支付退款通知", notes = "微信支付退款通知")
    @PostMapping(value = "/refundNotify")
    public String refundNotify(HttpServletRequest request) {
        return wxPayInfoService.refundNotify(request);
    }
	
	@GetMapping("test")
	public String test() {
		return "test";
	}
}

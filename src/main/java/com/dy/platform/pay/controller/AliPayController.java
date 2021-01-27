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
import com.dy.platform.pay.service.AliPayInfoService;
import com.egzosn.pay.common.bean.PayOrder;
import com.egzosn.pay.common.bean.RefundOrder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/aliPay")
@Api("阿里支付管理")
@Slf4j
public class AliPayController {

	@Autowired
	private AliPayInfoService aliPayInfoService;

	@ApiOperation("返回创建的订单信息")
	@PostMapping("payOrder")
	public ResponseEntity<Map<String, Object>> payOrder(PayOrderDTO payOrderDTO) {
		return ResponseEntity.ok(aliPayInfoService.payOrder(payOrderDTO));
	}
	
	/**
	 * 
	 * @param transactionId 支付平台订单号
	 * @param outTradeNo 商户单号
	 * @return
	 */
	@ApiOperation("支付交易查询")
	@PostMapping("payQuery")
	public Map<String, Object> payQuery(@RequestParam("tradeNo") String tradeNo, 
			@RequestParam("outTradeNo") String outTradeNo){
		return aliPayInfoService.payQuery(tradeNo, outTradeNo);
	}
	
	@ApiOperation("app支付")
	@PostMapping("appPay")
	public Map<String, Object> appPay(@RequestBody PayOrder order){
		return aliPayInfoService.appPay(order);
	}
	
	@ApiOperation("跳转支付页二维码支付")
	@PostMapping(value = "aliToPay")
	public String aliToPay(@RequestBody PayOrder order) throws IOException {
		return aliPayInfoService.aliToPay(order);
	}
	
	@ApiOperation("支付回调接口")
	@PostMapping("aliPayNotify")
	public String payNotify(HttpServletRequest request) {
		try {
			return aliPayInfoService.payNotify(request);
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}
	
	@ApiOperation(value = "阿里支付退款申请", notes = "阿里支付退款申请")
    @PostMapping(value = "/payRefund")
    public Map<String, Object> payRefund(@RequestBody RefundOrder refundOrder) {
        return aliPayInfoService.payRefund(refundOrder);
    }

    @ApiOperation(value = "阿里支付退款查询", notes = "阿里支付退款查询")
    @PostMapping(value = "/refundQuery")
    public Map<String, Object> refundQuery(RefundOrder order) {
        return aliPayInfoService.aliRefundQueryInfo(order);
    }

    /**
     * 
     * @param tradeNo 支付平台订单号
     * @param outTradeNo 商户单号
     * @return
     */
	@ApiOperation(value = "app支付关闭订单", notes = "app支付关闭订单")
	@PostMapping(value = "/tradeClose")
	public Map<String, Object> tradeClose(@RequestParam("outTradeNo") String tradeNo, @RequestParam("tradeNo") String outTradeNo) {
		return aliPayInfoService.tradeClose(tradeNo,outTradeNo);
	}
	
	@GetMapping("test")
	public String test() {
		return "test";
	}
}

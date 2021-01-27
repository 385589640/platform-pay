package com.dy.platform.pay.handler;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.egzosn.pay.common.api.PayMessageHandler;
import com.egzosn.pay.common.bean.PayOutMessage;
import com.egzosn.pay.common.exception.PayErrorException;
import com.egzosn.pay.union.api.UnionPayService;
import com.egzosn.pay.union.bean.SDKConstants;
import com.egzosn.pay.union.bean.UnionPayMessage;

@Component
public class UnionPayMessageHandler implements PayMessageHandler<UnionPayMessage, UnionPayService> {

    @Override
    public PayOutMessage handle(UnionPayMessage payMessage, Map<String, Object> context, UnionPayService payService) throws PayErrorException {
        //交易状态
        if (SDKConstants.OK_RESP_CODE.equals(payMessage.getPayMessage().get(SDKConstants.param_respCode))) {
            Map<String, Object> message = payMessage.getPayMessage();
            //交易状态
            String trade_status = (String) message.get("SUCCESS");
            String out_trade_no =(String) message.get("orderId");
            String trade_no =(String) message.get("queryId");
            String total_amount =(String) message.get("settleAmt");
            return payService.successPayOutMessage(payMessage);
        }

        return payService.getPayOutMessage("fail", "失败");
    }
}

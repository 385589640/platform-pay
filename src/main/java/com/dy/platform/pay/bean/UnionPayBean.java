package com.dy.platform.pay.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *
 * @author Javen
 */
@Data
@Component
@PropertySource("classpath:/production/unionpay.properties")
@ConfigurationProperties(prefix = "unionpay")
public class UnionPayBean {
    private String merId;
    private String keyPrivate;
    private String notifyUrl;
    private String inputCharset;
    private String msgSrcId;
    private String url;
    private String mid;
    private String tid;
    private String instMid;
    private String msgSrc;
    private String key;
    private String msgType_getQRCode;
    private String msgType_refund;
    private String msgType_query;
    private String msgType_queryLastQRCode;
    private String msgType_queryQRCodeInfo;
    private String msgType_closeQRCode;
    private String returnUrl;
}
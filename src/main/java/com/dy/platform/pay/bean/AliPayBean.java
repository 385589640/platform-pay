package com.dy.platform.pay.bean;

import com.egzosn.pay.common.bean.MsgType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * <p>支付宝配置 Bean</p>
 *
 * @author Javen
 */
@Data
@Component
@PropertySource("classpath:/production/alipay.properties")
@ConfigurationProperties(prefix = "alipay")
public class AliPayBean {
    /**
     * 商户应用id
     */
    private String appId;
    /**
     * 商户收款账号
     */
    private String seller;
    /**
     * 商户签约拿到的pid,partner_id的简称，合作伙伴身份等同于 partner
     */
    private String pid;
    /**
     * 应用私钥，rsa_private pkcs8格式 生成签名时使用
     */
    private String keyPrivate;
    /**
     * 应用私钥证书，rsa_private pkcs8格式 生成签名时使用
     */
    private String keyPrivateCertPwd;
    /**
     * 支付平台公钥(签名校验使用)
     */
    private String keyPublic;
    /**
     * 异步回调地址
     */
    private String notifyUrl;
    /**
     * 同步回调地址，支付完成后展示的页面
     */
    private String returnUrl;
    /**
     * 签名加密类型
     */
    private String signType;
    /**
     * 字符类型
     */
    private String inputCharset;


    /**
     * 支付类型 aliPay 支付宝， wxPay微信..等等，扩展支付模块定义唯一。
     */
    private String payType;

    /**
     * 消息来源类型
     */
    private MsgType msgType;


    /**
     * 访问令牌 每次请求其他方法都要传入的值
     */
    private String accessToken;
    /**
     * access token 到期时间时间戳
     */
    private long expiresTime;
    /**
     * 授权码锁
     */
    private Lock accessTokenLock = new ReentrantLock();
    /**
     * 是否为沙箱环境，默认为正式环境
     */
    private boolean isTest = false;

    /**
     * 是否为证书签名
     */
    private boolean isCertSign = false;
}
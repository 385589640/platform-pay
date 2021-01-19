package com.dy.platform.pay.bean;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.egzosn.pay.common.bean.MsgType;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * <p>
 * 微信配置 Bean
 * </p>
 *
 * @author Javen
 */
@Getter
@Setter
@Component
@PropertySource("classpath:/production/wxpay.properties")
@ConfigurationProperties(prefix = "wxpay")
public class WxPayBean {
	/**
	 * 微信分配的公众账号ID
	 */
	private String appid;
	/**
	 * 微信分配的子商户公众账号ID
	 */
	private String subAppid;
	/**
	 * 微信支付分配的商户号 合作者id
	 */
	private String mchId;
	/**
	 * 微信支付分配的子商户号，开发者模式下必填 合作者id
	 */
	private String subMchId;

	private String secretKey;

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
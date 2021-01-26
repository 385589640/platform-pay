package com.dy.platform.pay.config;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dy.platform.pay.bean.WxPayBean;
import com.dy.platform.pay.handler.WxPayMessageHandler;
import com.egzosn.pay.common.bean.CertStoreType;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.egzosn.pay.wx.api.WxPayConfigStorage;
import com.egzosn.pay.wx.api.WxPayService;

@Configuration
public class WxPayConfig {

	@Autowired
	private WxPayBean wxPayBean;

	@Resource
	private AutowireCapableBeanFactory spring;
	
	@Value("${wxpay.keyStore}")
	private String keyStore;

//    private static String KEYSTORE = "/1601009655_20200821_cert/apiclient_cert.p12";
//	private static String KEYSTORE = "F:\\cert/apiclient_cert.p12";
	private static String STORE_PASSWORD = "1601009655";

	@PostConstruct
	@Bean
	public WxPayService wxPayInit() throws Exception {
		WxPayConfigStorage wxPayConfigStorage = new WxPayConfigStorage();
		wxPayConfigStorage.setAppid(wxPayBean.getAppid());// 应用id
		wxPayConfigStorage.setMchId(wxPayBean.getMchId());// 合作者id（商户号）
//		wxPayConfigStorage.setKeyPublic(wxPayBean.getKeyPublic());//转账公钥，转账时必填
		wxPayConfigStorage.setSecretKey(wxPayBean.getSecretKey());// 密钥
		wxPayConfigStorage.setKeyPrivate(wxPayBean.getKeyPrivate());// 应用私钥，rsa_private pkcs8格式 生成签名时使用
		wxPayConfigStorage.setNotifyUrl(wxPayBean.getNotifyUrl());// 异步回调地址
//		wxPayConfigStorage.setReturnUrl(wxPayBean.getReturnUrl());//同步回调地址
		wxPayConfigStorage.setSignType(wxPayBean.getSignType());// 签名方式
		wxPayConfigStorage.setInputCharset(wxPayBean.getInputCharset());
//		wxPayConfigStorage.setTest(true);//是否为测试账号，沙箱环境
		WxPayService wxPayService = new WxPayService(wxPayConfigStorage);

		wxPayService.setRequestTemplateConfigStorage(httpConfigStorageInit());

		// 增加支付回调消息拦截器
//		wxPayService.addPayMessageInterceptor(new WxPayMessageInterceptor());
		// 设置回调消息处理
		wxPayService.setPayMessageHandler(spring.getBean(WxPayMessageHandler.class));

		return wxPayService;
	}

	public HttpConfigStorage httpConfigStorageInit() {
		HttpConfigStorage httpConfigStorage = new HttpConfigStorage();
		/* 网路代理配置 根据需求进行设置 **/
		// http代理地址
//		httpConfigStorage.setHttpProxyHost("192.168.1.69");
		// 代理端口
//		httpConfigStorage.setHttpProxyPort(3308);
		// 代理用户名
//		httpConfigStorage.setAuthUsername("user");
		// 代理密码
//		httpConfigStorage.setAuthPassword("password");
		/* /网路代理配置 根据需求进行设置 **/

		// 退款使用
		/* 网络请求ssl证书 根据需求进行设置 **/
		// 设置ssl证书路径
		// TODO 这里也支持输入流的入参。
		// httpConfigStorage.setKeystore(this.getClass()..getResourceAsStream("/证书文件"));
		// 设置ssl证书路径 跟着setCertStoreType 进行对应
		httpConfigStorage.setKeystore(keyStore);// 证书文件流，证书字符串信息或证书绝对地址
		// 设置ssl证书对应的密码
		httpConfigStorage.setStorePassword(STORE_PASSWORD);// 证书对应的密码
		// 设置ssl证书对应的存储方式
		httpConfigStorage.setCertStoreType(CertStoreType.PATH);
		/* /网络请求ssl证书 **/
		/* /网络请求连接池 **/
		// 最大连接数
		httpConfigStorage.setMaxTotal(20);
		// 默认的每个路由的最大连接数
		httpConfigStorage.setDefaultMaxPerRoute(10);
		return httpConfigStorage;
	}
}

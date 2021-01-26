package com.dy.platform.pay.config;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dy.platform.pay.bean.AliPayBean;
import com.dy.platform.pay.handler.AliPayMessageHandler;
import com.egzosn.pay.ali.api.AliPayConfigStorage;
import com.egzosn.pay.ali.api.AliPayService;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.egzosn.pay.common.util.sign.SignUtils;

@Configuration
public class AliPayConfig {

	@Autowired
	private AliPayBean aliPayBean;

	@Resource
	private AutowireCapableBeanFactory spring;

	@PostConstruct
	@Bean
	public AliPayService aliPayInit() throws Exception {
        //请求连接池配置
        HttpConfigStorage httpConfigStorage = new HttpConfigStorage();
        //支付宝初始化配置
        AliPayConfigStorage aliPayConfigStorage = new AliPayConfigStorage();
        aliPayConfigStorage.setAppid(aliPayBean.getAppId());
        aliPayConfigStorage.setKeyPublic(aliPayBean.getKeyPublic());
        aliPayConfigStorage.setKeyPrivate(aliPayBean.getKeyPrivate());
        aliPayConfigStorage.setNotifyUrl(aliPayBean.getNotifyUrl());
        aliPayConfigStorage.setSignType(SignUtils.RSA2.name());
        aliPayConfigStorage.setInputCharset(aliPayBean.getInputCharset());
        //是否为测试账号，沙箱环境
        aliPayConfigStorage.setTest(false);
        AliPayService aliPayService = new AliPayService(aliPayConfigStorage, httpConfigStorage);
        //设置回调消息处理
        aliPayService.setPayMessageHandler(spring.getBean(AliPayMessageHandler.class));
        return aliPayService;
    }
}

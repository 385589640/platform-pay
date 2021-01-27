package com.dy.platform.pay.config;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dy.platform.pay.bean.UnionPayBean;
import com.dy.platform.pay.handler.UnionPayMessageHandler;
import com.egzosn.pay.common.bean.CertStoreType;
import com.egzosn.pay.common.util.sign.SignUtils;
import com.egzosn.pay.union.api.UnionPayConfigStorage;
import com.egzosn.pay.union.api.UnionPayService;

@Configuration
public class UnionPayConfig {

	@Autowired
	private UnionPayBean unionPayBean;

	@Resource
	private AutowireCapableBeanFactory spring;

	@PostConstruct
	@Bean
	public UnionPayService unionPayInit() throws Exception {
        UnionPayConfigStorage unionPayConfigStorage = new UnionPayConfigStorage();
        unionPayConfigStorage.setMerId(unionPayBean.getMerId());
        //是否为证书签名
        unionPayConfigStorage.setCertSign(true);
        //unionPayConfigStorage.setKeyPrivate(unionPayBean.getKeyPrivate());
        unionPayConfigStorage.setNotifyUrl(unionPayBean.getNotifyUrl());
        unionPayConfigStorage.setReturnUrl(unionPayBean.getReturnUrl());
        //中级证书路径
        unionPayConfigStorage.setAcpMiddleCert("/data/cert/acp_prod_middle.cer");
        //根证书路径
        unionPayConfigStorage.setAcpRootCert("/data/cert/acp_prod_root.cer");
        //私钥证书路径
        unionPayConfigStorage.setKeyPrivateCert("/data/cert/zmsjylsy.pfx");
        //私钥证书对应的密码
        unionPayConfigStorage.setKeyPrivateCertPwd("000000");
        //设置证书对应的存储方式，这里默认为文件地址
        unionPayConfigStorage.setCertStoreType(CertStoreType.PATH);
        //加密方式
        unionPayConfigStorage.setSignType(SignUtils.RSA2.name());
        //单一支付可不填
        unionPayConfigStorage.setPayType("unionPay");
        unionPayConfigStorage.setInputCharset("utf-8");
        //是否为测试账号，沙箱环境
        unionPayConfigStorage.setTest(false);
        UnionPayService unionPayService = new UnionPayService(unionPayConfigStorage);
        unionPayService.setPayMessageHandler(spring.getBean(UnionPayMessageHandler.class));
        return unionPayService;
    }
}

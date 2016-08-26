package com.cache.demo;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.cache.demo.impl.CacheDemoImpl;
import com.cache.demo.vo.CacheData;

public class Main {

    public static void main(String[] args) {
        ApplicationContext factory = new FileSystemXmlApplicationContext(
                "src/main/resources/applicationContext-tx-test.xml");
        CacheDemoImpl demo = (CacheDemoImpl) factory.getBean("cacheDemoImpl");
        //   demo.getCacheById(1L);
        List<CacheData> result = demo.getCacheData(2);
        for (CacheData data : result) {
            System.out.println(data.getName());
            System.out.println(data.getId());
        }
        System.out.println(JSON.toJSONString(result));
    }

}

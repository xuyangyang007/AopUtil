package com.cache.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
        List<Integer> idList = Arrays.asList(1, 2, 3);
        Map<Integer, CacheData> result = demo.batchGetData(idList);
        System.out.println(JSON.toJSONString(result));
    }

}

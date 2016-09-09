package com.cache.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.cache.demo.impl.CacheDemoImpl;
import com.cache.demo.vo.CacheData;
import com.cache.demo.vo.CacheKey;

public class Main {

    public static void main(String[] args) {
        ApplicationContext factory = new FileSystemXmlApplicationContext(
                "src/main/resources/applicationContext-tx-test.xml");
        CacheDemoImpl demo = (CacheDemoImpl) factory.getBean("cacheDemoImpl");
//        CacheKey key = new CacheKey();
//        key.setDesc("test");
//        key.setId(1);
//        CacheData cacheData = demo.getCacheById(key);
//        System.out.println(cacheData.getId());
 
        List<CacheKey> keyList = new ArrayList<CacheKey>();
        for (int i=1;i<=3;i++) {
            CacheKey temp = new CacheKey();
            temp.setDesc("test" + i);
            temp.setId(i);
            keyList.add(temp);
        }
        Map<CacheKey, CacheData> result = demo.batchGetData(keyList);
        for(Entry<CacheKey, CacheData> entry : result.entrySet()) {
            System.out.println(entry.getKey().getId());
            System.out.println(entry.getValue().getName());
        }
        System.out.println(JSON.toJSONString(result));
    }

}

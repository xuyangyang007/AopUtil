# AopUtil
用户可以用特定的注解实现缓存操作。方便用户编程。<br/>
缓存操作注解如下：<br/>

* @CacheCleaner 缓存清除（函数级别）

>>参数：<br/>
cacheKeyPrefix 缓存前缀

* @CacheLoader 缓存加载（函数级别）

>>参数：<br/>
cacheKeyPrefix 缓存前缀<br/>
timeout 缓存超时时间

* @MultiCacheLoader 批量缓存加载（函数级别）

>>参数：
cacheKeyPrefix 缓存前缀<br/>
timeout 缓存超时时间

* @CacheParam 缓存key的组成（函数参数级别）
* @CacheNamespace 缓存key的组成(类级别)

*说明：缓存key组成：nameSpace + cacheKeyPrefix + CacheParam*

	@CacheNamespace(nameSpace="cache-demo") // 缓存类前缀，可以为空
	@Service
	public class CacheDemoImpl {
    
	    @CacheCleaner(cacheKeyPrefix="detail") // 进入方法前清除缓存
	    public void deleteCacheById(@CacheParam Long id) {
	        
	    }
	    
	    @CacheLoader(cacheKeyPrefix="detail", timeout=3000) // 加载缓存
	    public CacheData getCacheById(@CacheParam CacheKey id) { // CacheParam表示注解缓存后缀
	        System.out.println(id.toString());
	        CacheData data = new CacheData();
	        data.setId(id.getId());
	        data.setDescription(id.getDesc());
	        return data;
	    }
	    
	    @CacheLoader(cacheKeyPrefix="detail", timeout=3000) // 加载缓存
	    public Object updateCache(@CacheParam Long id) { // CacheParam表示注解缓存后缀
	        return new Object();
	    }
	
	    @CacheLoader(cacheKeyPrefix="detailList", timeout=3000) // 获取缓存
	    public List<CacheData> getCacheData(@CacheParam Integer id) { 
	        CacheData data1 = new CacheData();
	        data1.setDescription("11");
	        data1.setId(1);
	        data1.setName("11");
	        CacheData data2 = new CacheData();
	        data2.setDescription("22");
	        data2.setId(2);
	        data2.setName("22");
	        List<CacheData> list = Arrays.asList(data1, data2);
	        return list;
	    }
	
	    @MultiCacheLoader(cacheKeyPrefix="batchGetData", timeout=3000) // 批量获取缓存
	    public Map<CacheKey, CacheData> batchGetData(@CacheParam List<CacheKey> idList) {
	        Map<CacheKey, CacheData> map = new HashMap<CacheKey, CacheData>();
	        for (CacheKey id : idList) {
	            CacheData data1 = new CacheData();
	            data1.setDescription(id + "desc");
	            data1.setId(id.getId());
	            data1.setName("name" + id);
	            map.put(id, data1);
	        }
	        return map;
	    }

	}

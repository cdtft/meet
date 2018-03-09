package com.cdut.data;

import com.cdut.entity.common.JsonResult;
import com.cdut.service.page.PageService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 首页信息缓存
 * Created by king on 2017/11/25.
 */
@Component
public class HomeInfoDataProvider {

    private static final Logger logger = LoggerFactory.getLogger(HomeInfoDataProvider.class);

    @Autowired
    private PageService pageService;

    /**
     * 获取首页缓存信息
     * @param key
     * @return
     */
    public JsonResult loadHomePageInfoFromCache(Long key) {

        return homeInfoCache.getUnchecked(key);
    }

    private LoadingCache<Long, JsonResult> homeInfoCache = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .build(new CacheLoader<Long, JsonResult>() {
                @Override
                public JsonResult load(Long key) throws Exception {
                    logger.debug("加载首页缓存");
                    return pageService.findOnePageInfo();
                }
            });
}

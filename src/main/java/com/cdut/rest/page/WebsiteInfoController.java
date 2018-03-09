package com.cdut.rest.page;

import com.cdut.data.HomeInfoDataProvider;
import com.cdut.entity.common.JsonResult;
import com.cdut.enums.ResultStatus;
import com.cdut.rest.BaseRestController;
import com.cdut.service.page.PageService;
import com.cdut.vo.PageInfoRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by king on 2017/10/25.
 */
@RestController
public class WebsiteInfoController extends BaseRestController {

    private static final Long HOME_PAGE_CACHE_KEY = 0L;

    @Autowired
    private PageService pageService;

    @Autowired
    private HomeInfoDataProvider homeInfoDataProvider;

    @RequestMapping(value = "v1/api/websiteInfo", method = RequestMethod.GET)
    public JsonResult getUPageInfo() {
        try {
            //return pageService.findOnePageInfo();
            return homeInfoDataProvider.loadHomePageInfoFromCache(HOME_PAGE_CACHE_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("页面请求出错 v1/api/pageInfo");
            return new JsonResult("页面配置信息出错", ResultStatus.SERVICE_ERROR);
        }
    }
    @RequestMapping(value = "v1/api/websiteInfo", method = RequestMethod.PUT)
    public JsonResult updateTitle(@RequestBody PageInfoRespVO vo) {
        try {
            return pageService.updatePageTitle(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult("更新失败", ResultStatus.SERVICE_ERROR);
        }
    }
}

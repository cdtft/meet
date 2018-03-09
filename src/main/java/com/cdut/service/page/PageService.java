package com.cdut.service.page;

import com.cdut.entity.common.JsonResult;
import com.cdut.entity.page.PageInfoPO;
import com.cdut.vo.PageArticleRespVO;
import com.cdut.vo.PageInfoRespVO;

import java.util.List;

/**
 * Created by king on 2017/10/25.
 */
public interface PageService {

    /**
     * 查找页面信息,只会存在一个是对象
     * @return
     * @throws Exception
     */
    JsonResult findOnePageInfo() throws Exception;

    JsonResult updatePageTitle(PageInfoRespVO vo) throws Exception;
}

package com.cdut.service.article;

import com.cdut.entity.common.JsonResult;
import com.cdut.vo.ArticlesReqVO;
import com.cdut.vo.PageArticleReqVO;

/**
 * Created by king on 2017/10/31.
 */
public interface NoticeService {

    JsonResult addNotice(ArticlesReqVO vo);

    JsonResult deleteNotice(Long id);

    JsonResult updateNotice(ArticlesReqVO vo);

    JsonResult getNotice(PageArticleReqVO vo);

    JsonResult findById(Long id);
}

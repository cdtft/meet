package com.cdut.service.article;

import com.cdut.entity.common.JsonResult;
import com.cdut.vo.ArticlesReqVO;
import com.cdut.vo.PageArticleReqVO;

/**
 * Created by king on 2017/10/31.
 */
public interface LearnService {

    JsonResult addLearn(ArticlesReqVO vo);

    JsonResult deleteLearn(Long id);

    JsonResult updateLearn(ArticlesReqVO vo);

    JsonResult getLearn(PageArticleReqVO vo);

    JsonResult findById(Long id);
}

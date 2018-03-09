package com.cdut.service.article;

import com.cdut.entity.common.JsonResult;
import com.cdut.vo.ArticleReqVO;

/**
 * Created by king on 2017/10/29.
 */
public interface ArticleService {

    JsonResult setMenuArticle(ArticleReqVO vo);

    JsonResult getArticleContent(Long id);
}

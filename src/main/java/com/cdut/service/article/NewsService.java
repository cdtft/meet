package com.cdut.service.article;


import com.cdut.entity.common.JsonResult;
import com.cdut.vo.ArticlesReqVO;
import com.cdut.vo.PageArticleReqVO;

/**
 * Created by king on 2017/10/31.
 */
public interface NewsService {

    JsonResult addNews(ArticlesReqVO vo);

    JsonResult deleteNews(Long id);

    JsonResult updateNews(ArticlesReqVO vo);

    JsonResult getNews(PageArticleReqVO vo);

    JsonResult findById(Long id);
}

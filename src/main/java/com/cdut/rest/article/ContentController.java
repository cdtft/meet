package com.cdut.rest.article;

import com.cdut.annotation.Authorization;
import com.cdut.annotation.RoleRequired;
import com.cdut.entity.common.JsonResult;
import com.cdut.enums.ArticleEnum;
import com.cdut.enums.ResultStatus;
import com.cdut.rest.BaseRestController;
import com.cdut.service.article.ArticleService;
import com.cdut.service.article.LearnService;
import com.cdut.service.article.NewsService;
import com.cdut.service.article.NoticeService;
import com.cdut.vo.ArticleReqVO;
import com.cdut.vo.ArticlesReqVO;
import com.cdut.vo.ArticlesRespVO;
import com.cdut.vo.PageArticleReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by king on 2017/10/29.
 */
@RestController
public class ContentController extends BaseRestController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private LearnService learnService;

    @Autowired
    private NoticeService noticeService;

    /**
     * 上传文章并指定menu id
     * @param vo
     * @return
     */
    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/content", method = RequestMethod.POST)
    public JsonResult setMenuArticle(@RequestBody ArticleReqVO vo) {
        return articleService.setMenuArticle(vo);
    }

    /**
     * 根据id查询article
     * @param id
     * @return
     */
    @RequestMapping(value = "v1/api/content/{id}", method = RequestMethod.GET)
    public JsonResult getArticleContent(@PathVariable("id") Long id) {
        return articleService.getArticleContent(id);
    }

    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/articles", method = RequestMethod.POST)
    public JsonResult addArticles(@RequestBody ArticlesReqVO vo) {
        if (ArticleEnum.NOTICE.getType().equals(vo.getType())) {
            logger.info("添加首页文章 [{}], [{}]", vo.getTitle(), vo.getType());
            return noticeService.addNotice(vo);
        }
        if (ArticleEnum.NEWS.getType().equals(vo.getType())) {
            logger.info("添加首页文章 [{}], [{}]", vo.getTitle(), vo.getType());
            return newsService.addNews(vo);
        }
        if (ArticleEnum.LEARN.getType().equals(vo.getType())) {
            logger.info("添加首页文章 [{}], [{}]", vo.getTitle(), vo.getType());
            return learnService.addLearn(vo);
        }
        logger.info("没有匹配的类型 [{}], [{}]", vo.getTitle(), vo.getType());
        return new JsonResult("文章类型有误", ResultStatus.INVALID_REQUEST);
    }

    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/{type}/articles/{id}", method = RequestMethod.DELETE)
    public JsonResult deleteArticles(@PathVariable("type") String type, @PathVariable("id") Long id) {
        if (ArticleEnum.NOTICE.getType().equals(type)) {
            logger.info("删除首页文章type:[{}], id:[{}]", type, id);
            return noticeService.deleteNotice(id);
        }
        if (ArticleEnum.NEWS.getType().equals(type)) {
            logger.info("删除首页文章 type:[{}], id:[{}]", type, id);
            return newsService.deleteNews(id);
        }
        if (ArticleEnum.LEARN.getType().equals(type)) {
            logger.info("删除首页文章 type:[{}], id:[{}]", type, id);
            return learnService.deleteLearn(id);
        }
        logger.info("没有匹配的类型 type:[{}], id:[{}]", type, id);
        return new JsonResult("文章类型有误", ResultStatus.INVALID_REQUEST);
    }

    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/articles", method = RequestMethod.PUT)
    public JsonResult updateArticles(@RequestBody ArticlesReqVO vo) {
        if (ArticleEnum.NOTICE.getType().equals(vo.getType())) {
            logger.info("更新首页文章 [{}], [{}]", vo.getTitle(), vo.getType());
            return noticeService.updateNotice(vo);
        }
        if (ArticleEnum.NEWS.getType().equals(vo.getType())) {
            logger.info("更新首页文章 [{}], [{}]", vo.getTitle(), vo.getType());
            return newsService.updateNews(vo);
        }
        if (ArticleEnum.LEARN.getType().equals(vo.getType())) {
            logger.info("更新首页文章 [{}], [{}]", vo.getTitle(), vo.getType());
            return learnService.updateLearn(vo);
        }
        logger.info("没有匹配的类型 [{}], [{}]", vo.getTitle(), vo.getType());
        return new JsonResult("文章类型有误", ResultStatus.INVALID_REQUEST);
    }

    @RequestMapping(value = "v1/api/articles", method = RequestMethod.GET)
    public JsonResult getArticlesPageByType(PageArticleReqVO vo) {
        /*PageArticleReqVO vo = new PageArticleReqVO();
        vo.setPageSize(2);
        vo.setPage(1);
        vo.setType("notice");*/
        if (ArticleEnum.NOTICE.getType().equals(vo.getType())) {
            logger.info("获取首页文章 [{}]", vo.getType());
            return noticeService.getNotice(vo);
        }
        if (ArticleEnum.NEWS.getType().equals(vo.getType())) {
            logger.info("获取首页文章 [{}]", vo.getType());
            return newsService.getNews(vo);
        }
        if (ArticleEnum.LEARN.getType().equals(vo.getType())) {
            logger.info("获取首页文章 [{}]", vo.getType());
            return learnService.getLearn(vo);
        }
        logger.info("没有匹配的类型 [{}]", vo.getType());
        return new JsonResult("文章类型有误", ResultStatus.INVALID_REQUEST);
    }

    @RequestMapping(value = "v1/api/{type}/articles/{id}", method = RequestMethod.GET)
    public JsonResult findArticleById(@PathVariable("type") String type, @PathVariable("id") Long id) {
        if (ArticleEnum.NOTICE.getType().equals(type)) {
            logger.info("获取首页文章 [{}]", type);
            return noticeService.findById(id);
        }
        if (ArticleEnum.NEWS.getType().equals(type)) {
            logger.info("获取首页文章 [{}]", type);
            return newsService.findById(id);
        }
        if (ArticleEnum.LEARN.getType().equals(type)) {
            logger.info("获取首页文章 [{}]", type);
            return learnService.findById(id);
        }
        logger.info("没有匹配的类型 [{}]", type);
        return new JsonResult("文章类型有误", ResultStatus.INVALID_REQUEST);
    }

}

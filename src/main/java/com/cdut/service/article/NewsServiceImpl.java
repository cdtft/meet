package com.cdut.service.article;

import com.cdut.dao.mysql.article.NewsRepository;
import com.cdut.entity.article.NewsPO;
import com.cdut.entity.article.NoticePO;
import com.cdut.entity.common.JsonResult;
import com.cdut.enums.ArticleEnum;
import com.cdut.enums.ResultStatus;
import com.cdut.service.BaseService;
import com.cdut.transform.NewsPO2ArticlesRespVO;
import com.cdut.vo.ArticlesReqVO;
import com.cdut.vo.ArticlesRespVO;
import com.cdut.vo.PageArticleReqVO;
import com.cdut.vo.PageArticleRespVO;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by king on 2017/10/31.
 */
@Service
public class NewsServiceImpl extends BaseService implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Override
    @Transactional
    public JsonResult addNews(ArticlesReqVO vo) {
        NewsPO po = new NewsPO();
        po.setAuthor(vo.getAuthor());
        po.setContent(vo.getContent());
        po.setTitle(vo.getTitle());
        NewsPO returnPO = newsRepository.save(po);
        ArticlesRespVO result = new ArticlesRespVO();
        result.setId(returnPO.getId());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(returnPO.getCreateTimestamp());
        result.setCreateTime(createTime);
        result.setTitle(returnPO.getTitle());
        result.setAuthor(returnPO.getAuthor());
        result.setContent(returnPO.getContent());
        result.setType(vo.getType());
        return new JsonResult(result, "OK", ResultStatus.CREATED);
    }

    @Override
    @Transactional
    public JsonResult deleteNews(Long id) {
        newsRepository.delete(id);
        return new JsonResult("文章删除成功", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult updateNews(ArticlesReqVO vo) {
        NewsPO po = newsRepository.findOne(vo.getId());
        po.setTitle(vo.getTitle());
        po.setContent(vo.getContent());
        po.setAuthor(vo.getAuthor());
        NewsPO returnPO = newsRepository.save(po);
        ArticlesRespVO result = new ArticlesRespVO();
        result.setId(returnPO.getId());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(returnPO.getCreateTimestamp());
        result.setCreateTime(createTime);
        result.setType(vo.getType());
        result.setTitle(returnPO.getTitle());
        result.setContent(returnPO.getContent());
        result.setAuthor(returnPO.getAuthor());
        return new JsonResult(result, "OK", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult getNews(PageArticleReqVO vo) {
        Page<NewsPO> newsPOPage = newsRepository.findAll(new PageRequest(vo.getPage() - 1,
                vo.getPageSize(), Sort.Direction.DESC, "createTimestamp"));
        List<NewsPO> newsPOList = newsPOPage.getContent();
        if (CollectionUtils.isEmpty(newsPOList)) {
            return new JsonResult("没有notice", ResultStatus.SUCCESS);
        }
        Long total = newsPOPage.getTotalElements();
        List<ArticlesRespVO> articlesRespVOList = Lists.transform(newsPOList, new NewsPO2ArticlesRespVO());
        PageArticleRespVO pageArticleRespVO = new PageArticleRespVO();
        pageArticleRespVO.setPage(vo.getPage());
        pageArticleRespVO.setPageSize(vo.getPageSize());
        pageArticleRespVO.setTotal(total);
        pageArticleRespVO.setArticles(articlesRespVOList);
        return new JsonResult(pageArticleRespVO, "OK", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult findById(Long id) {
        NewsPO returnPO = newsRepository.findOne(id);
        ArticlesRespVO result = new ArticlesRespVO();
        result.setId(returnPO.getId());
        result.setType(ArticleEnum.NEWS.getType());
        result.setTitle(returnPO.getTitle());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(returnPO.getCreateTimestamp());
        result.setCreateTime(createTime);
        result.setContent(returnPO.getContent());
        result.setAuthor(returnPO.getAuthor());
        return new JsonResult(result, "OK", ResultStatus.SUCCESS);
    }
}

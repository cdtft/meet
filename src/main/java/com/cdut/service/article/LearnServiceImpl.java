package com.cdut.service.article;

import com.cdut.dao.mysql.article.LearnRepository;
import com.cdut.entity.article.LearnPO;
import com.cdut.entity.article.NoticePO;
import com.cdut.entity.common.JsonResult;
import com.cdut.enums.ArticleEnum;
import com.cdut.enums.ResultStatus;
import com.cdut.service.BaseService;
import com.cdut.transform.LearnPO2ArticlesRespVO;
import com.cdut.transform.NoticePO2ArticlesRespVO;
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
public class LearnServiceImpl extends BaseService implements LearnService {

    @Autowired
    private LearnRepository learnRepository;

    @Override
    @Transactional
    public JsonResult addLearn(ArticlesReqVO vo) {
        LearnPO po = new LearnPO();
        po.setAuthor(vo.getAuthor());
        po.setContent(vo.getContent());
        po.setTitle(vo.getTitle());
        LearnPO returnPO = learnRepository.save(po);
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
    public JsonResult deleteLearn(Long id) {
        learnRepository.delete(id);
        return new JsonResult("文章删除成功", ResultStatus.SUCCESS);
    }

    @Override
    @Transactional
    public JsonResult updateLearn(ArticlesReqVO vo) {
        LearnPO po = learnRepository.findOne(vo.getId());
        po.setTitle(vo.getTitle());
        po.setContent(vo.getContent());
        po.setAuthor(vo.getAuthor());
        LearnPO returnPO = learnRepository.save(po);
        ArticlesRespVO result = new ArticlesRespVO();
        result.setId(returnPO.getId());
        result.setType(vo.getType());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(returnPO.getCreateTimestamp());
        result.setCreateTime(createTime);
        result.setTitle(returnPO.getTitle());
        result.setContent(returnPO.getContent());
        result.setAuthor(returnPO.getAuthor());
        return new JsonResult(result, "OK", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult getLearn(PageArticleReqVO vo) {
        Page<LearnPO> learnPOPage = learnRepository.findAll(new PageRequest(vo.getPage() - 1,
                vo.getPageSize(), Sort.Direction.DESC, "createTimestamp"));
        List<LearnPO> learnPOList = learnPOPage.getContent();
        if (CollectionUtils.isEmpty(learnPOList)) {
            return new JsonResult("没有notice", ResultStatus.SUCCESS);
        }
        Long total = learnPOPage.getTotalElements();
        List<ArticlesRespVO> articlesRespVOList = Lists.transform(learnPOList, new LearnPO2ArticlesRespVO());
        PageArticleRespVO pageArticleRespVO = new PageArticleRespVO();
        pageArticleRespVO.setPage(vo.getPage());
        pageArticleRespVO.setPageSize(vo.getPageSize());
        pageArticleRespVO.setTotal(total);
        pageArticleRespVO.setArticles(articlesRespVOList);
        return new JsonResult(pageArticleRespVO, "OK", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult findById(Long id) {
        LearnPO returnPO = learnRepository.findOne(id);
        ArticlesRespVO result = new ArticlesRespVO();
        result.setId(returnPO.getId());
        result.setType(ArticleEnum.LEARN.getType());
        result.setTitle(returnPO.getTitle());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(returnPO.getCreateTimestamp());
        result.setCreateTime(createTime);
        result.setContent(returnPO.getContent());
        result.setAuthor(returnPO.getAuthor());
        return new JsonResult(result, "OK", ResultStatus.SUCCESS);
    }
}

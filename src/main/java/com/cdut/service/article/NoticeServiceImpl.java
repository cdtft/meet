package com.cdut.service.article;

import com.cdut.dao.mysql.article.NoticeRepository;
import com.cdut.entity.article.LearnPO;
import com.cdut.entity.article.NoticePO;
import com.cdut.entity.common.JsonResult;
import com.cdut.enums.ArticleEnum;
import com.cdut.enums.ResultStatus;
import com.cdut.service.BaseService;
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
public class NoticeServiceImpl extends BaseService implements NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    @Override
    @Transactional
    public JsonResult addNotice(ArticlesReqVO vo) {
        NoticePO po = new NoticePO();
        po.setAuthor(vo.getAuthor());
        po.setContent(vo.getContent());
        po.setTitle(vo.getTitle());
        NoticePO returnPO = noticeRepository.save(po);
        ArticlesRespVO result = new ArticlesRespVO();
        result.setId(returnPO.getId());
        result.setTitle(returnPO.getTitle());
        result.setAuthor(returnPO.getAuthor());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(returnPO.getCreateTimestamp());
        result.setCreateTime(createTime);
        result.setContent(returnPO.getContent());
        result.setType(vo.getType());
        return new JsonResult(result, "OK", ResultStatus.CREATED);
    }

    @Override
    public JsonResult deleteNotice(Long id) {
        noticeRepository.delete(id);
        return new JsonResult("文章删除成功", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult updateNotice(ArticlesReqVO vo) {
        NoticePO po = noticeRepository.findOne(vo.getId());
        po.setTitle(vo.getTitle());
        po.setContent(vo.getContent());
        po.setAuthor(vo.getAuthor());
        NoticePO returnPO = noticeRepository.save(po);
        ArticlesRespVO result = new ArticlesRespVO();
        result.setId(returnPO.getId());
        result.setType(vo.getType());
        result.setTitle(returnPO.getTitle());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(returnPO.getCreateTimestamp());
        result.setCreateTime(createTime);
        result.setContent(returnPO.getContent());
        result.setAuthor(returnPO.getAuthor());
        return new JsonResult(result, "OK", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult getNotice(PageArticleReqVO vo) {
        Page<NoticePO> noticePOPage = noticeRepository.findAll(new PageRequest(vo.getPage() - 1,
                vo.getPageSize(), Sort.Direction.DESC, "createTimestamp"));
        List<NoticePO> noticePOList = noticePOPage.getContent();
        if (CollectionUtils.isEmpty(noticePOList)) {
            return new JsonResult("没有notice", ResultStatus.SUCCESS);
        }
        Long total = noticePOPage.getTotalElements();
        List<ArticlesRespVO> articlesRespVOList = Lists.transform(noticePOList, new NoticePO2ArticlesRespVO());
        PageArticleRespVO pageArticleRespVO = new PageArticleRespVO();
        pageArticleRespVO.setPage(vo.getPage());
        pageArticleRespVO.setPageSize(vo.getPageSize());
        pageArticleRespVO.setTotal(total);
        pageArticleRespVO.setArticles(articlesRespVOList);
        return new JsonResult(pageArticleRespVO, "OK", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult findById(Long id) {
        NoticePO returnPO = noticeRepository.findOne(id);
        ArticlesRespVO result = new ArticlesRespVO();
        result.setId(returnPO.getId());
        result.setType(ArticleEnum.NOTICE.getType());
        result.setTitle(returnPO.getTitle());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(returnPO.getCreateTimestamp());
        result.setCreateTime(createTime);
        result.setContent(returnPO.getContent());
        result.setAuthor(returnPO.getAuthor());
        return new JsonResult(result, "OK", ResultStatus.SUCCESS);
    }
}

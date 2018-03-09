package com.cdut.service.article;

import com.cdut.dao.mysql.article.ArticleRepository;
import com.cdut.dao.mysql.menu.MenuRepository;
import com.cdut.entity.article.ArticlePO;
import com.cdut.entity.common.JsonResult;
import com.cdut.entity.menu.MenuPO;
import com.cdut.enums.ResultStatus;
import com.cdut.vo.ArticleReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by king on 2017/10/29.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public JsonResult setMenuArticle(ArticleReqVO vo) {
        if (vo.getArticleId() == null) {
            ArticlePO po = new ArticlePO();
            po.setAuthor("admin");
            po.setContent(vo.getContent());
            ArticlePO returnPO = articleRepository.save(po);
            MenuPO menuPO = menuRepository.findOne(vo.getMenuId());
            menuPO.setArticleId(returnPO.getId());
            menuRepository.save(menuPO);
            return new JsonResult(returnPO.getId(), "添加成功", ResultStatus.SUCCESS);
        }
        ArticlePO po = articleRepository.findOne(vo.getArticleId());
        po.setContent(vo.getContent());
        ArticlePO returnPO = articleRepository.save(po);
        return new JsonResult(returnPO.getId(), "更新成功", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult getArticleContent(Long id) {
        ArticlePO po = articleRepository.findOne(id);
        return new JsonResult(po.getContent(), "OK", ResultStatus.SUCCESS);
    }
}
package com.cdut.transform;

import com.cdut.entity.article.LearnPO;
import com.cdut.enums.ArticleEnum;
import com.cdut.vo.ArticlesRespVO;
import com.google.common.base.Function;

import javax.annotation.Nullable;
import java.text.SimpleDateFormat;

/**
 * Created by king on 2017/10/31.
 */
public class LearnPO2ArticlesRespVO implements Function<LearnPO, ArticlesRespVO> {

    @Nullable
    @Override
    public ArticlesRespVO apply(@Nullable LearnPO po) {
        if (po != null) {
            ArticlesRespVO vo = new ArticlesRespVO();
            vo.setId(po.getId());
            vo.setTitle(po.getTitle());
            vo.setAuthor(po.getAuthor());
            //vo.setContent(po.getContent());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            vo.setCreateTime(format.format(po.getCreateTimestamp()));
            vo.setType(ArticleEnum.LEARN.getType());
            return vo;
        }
        return null;
    }
}

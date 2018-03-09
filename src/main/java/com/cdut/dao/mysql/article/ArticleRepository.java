package com.cdut.dao.mysql.article;

import com.cdut.dao.common.repository.CommonJpaRepository;
import com.cdut.entity.article.ArticlePO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by king on 2017/10/29.
 */
public interface ArticleRepository extends CommonJpaRepository<ArticlePO, Long>, JpaSpecificationExecutor<ArticlePO> {

}

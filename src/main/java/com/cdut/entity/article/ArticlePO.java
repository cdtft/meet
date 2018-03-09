package com.cdut.entity.article;

import com.cdut.entity.common.BaseArticleEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 上传文章po类, 侧边栏所指向
 * Created by king on 2017/10/25.
 */
@Entity
@Table(name = "article_upload")
public class ArticlePO extends BaseArticleEntity {

    private static final long serialVersionUID = 3917869608452301959L;
}

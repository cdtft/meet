package com.cdut.entity.article;

import com.cdut.entity.common.BaseArticleEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 首页新闻类
 * Created by king on 2017/10/25.
 */
@Entity
@Table(name = "article_news")
public class NewsPO extends BaseArticleEntity {

    private static final long serialVersionUID = 9116894824840700478L;
}

package com.cdut.entity.article;

import com.cdut.entity.common.BaseArticleEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 首页学术po
 * Created by king on 2017/10/25.
 */
@Entity
@Table(name = "article_learn")
public class LearnPO extends BaseArticleEntity {
    private static final long serialVersionUID = -275833415194591397L;
}

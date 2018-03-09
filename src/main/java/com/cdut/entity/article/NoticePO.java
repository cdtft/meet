package com.cdut.entity.article;

import com.cdut.entity.common.BaseArticleEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 首页公告PO
 * Created by king on 2017/10/25.
 */
@Entity
@Table(name = "article_notice")
public class NoticePO extends BaseArticleEntity {
    private static final long serialVersionUID = -8348390825512240693L;
}

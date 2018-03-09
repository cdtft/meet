package com.cdut.vo;

import java.util.List;

/**
 * Created by king on 2017/10/31.
 */
public class PageArticleRespVO {

    private Long total;

    private List<ArticlesRespVO> articles;

    private Integer page;

    private Integer pageSize;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<ArticlesRespVO> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesRespVO> articles) {
        this.articles = articles;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}

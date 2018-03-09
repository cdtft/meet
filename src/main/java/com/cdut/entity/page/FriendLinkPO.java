package com.cdut.entity.page;

import com.cdut.entity.common.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 友情链接
 * Created by king on 2017/11/5.
 */
@Entity
@Table(name = "page_friend_link")
public class FriendLinkPO extends BaseEntity {

    private static final long serialVersionUID = 5633539698232123030L;

    private String url;

    @Column(length = 40)
    private String text;

    private Integer indexNum;

    @ManyToOne(fetch = FetchType.LAZY)
    private PageInfoPO pageInfoPO;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public PageInfoPO getPageInfoPO() {
        return pageInfoPO;
    }

    public void setPageInfoPO(PageInfoPO pageInfoPO) {
        this.pageInfoPO = pageInfoPO;
    }

    public Integer getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(Integer indexNum) {
        this.indexNum = indexNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("url", url)
                .append("text", text)
                .append("pageInfoPO", pageInfoPO)
                .toString();
    }
}

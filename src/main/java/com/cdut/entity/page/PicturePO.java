package com.cdut.entity.page;

import com.cdut.entity.common.BaseEntity;
import com.cdut.enums.PictureTypeEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by king on 2017/10/25.
 */
@Entity
@Table(name = "page_picture")
public class PicturePO extends BaseEntity {

    private static final long serialVersionUID = 542564384346652189L;

    @Column(length = 40)
    private String name;

    private String url;

    @Column(length = 40)
    private String title;

    @Enumerated(EnumType.STRING)
    private PictureTypeEnum pictureTypeEnum;

    @ManyToOne(fetch = FetchType.LAZY)
    private PageInfoPO pageInfoPO;

    private Integer indexNum;

    /**
     * 跳转链接
     */
    private String forwardUrl;

    public String getForwardUrl() {
        return forwardUrl;
    }

    public void setForwardUrl(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PictureTypeEnum getPictureTypeEnum() {
        return pictureTypeEnum;
    }

    public void setPictureTypeEnum(PictureTypeEnum pictureTypeEnum) {
        this.pictureTypeEnum = pictureTypeEnum;
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
}

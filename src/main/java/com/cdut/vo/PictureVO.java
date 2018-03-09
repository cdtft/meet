package com.cdut.vo;

import com.cdut.enums.PictureTypeEnum;

/**
 * Created by king on 2017/10/29.
 */
public class PictureVO {

    private Long id;

    private String name;

    private String url;

    private String title;

    private String forwardUrl;

    private PictureTypeEnum pictureTypeEnum;

    private Integer indexNum;

    public String getForwardUrl() {
        return forwardUrl;
    }

    public void setForwardUrl(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }

    public Integer getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(Integer indexNum) {
        this.indexNum = indexNum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PictureTypeEnum getPictureTypeEnum() {
        return pictureTypeEnum;
    }

    public void setPictureTypeEnum(PictureTypeEnum pictureTypeEnum) {
        this.pictureTypeEnum = pictureTypeEnum;
    }
}

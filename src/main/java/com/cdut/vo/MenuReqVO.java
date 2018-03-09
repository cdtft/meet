package com.cdut.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by king on 2017/10/28.
 */
public class MenuReqVO {

    private String name;

    private Long id;

    private Integer indexNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                .append("name", name)
                .append("id", id)
                .append("indexNum", indexNum)
                .toString();
    }
}

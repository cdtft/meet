package com.cdut.vo;

import java.util.List;

/**
 * Created by king on 2017/10/26.
 */
public class UnitRespVO {

    private String name;

    private Integer indexNum;

    private Long id;

    private List<UnitItemRespVO> unitItemRespVOList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(Integer indexNum) {
        this.indexNum = indexNum;
    }

    public List<UnitItemRespVO> getUnitItemRespVOList() {
        return unitItemRespVOList;
    }

    public void setUnitItemRespVOList(List<UnitItemRespVO> unitItemRespVOList) {
        this.unitItemRespVOList = unitItemRespVOList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

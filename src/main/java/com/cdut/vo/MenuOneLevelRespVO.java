package com.cdut.vo;

/**
 * Created by king on 2017/10/27.
 */
public class MenuOneLevelRespVO {

    private String name;

    private Number id;

    private Number indexNm;

    public MenuOneLevelRespVO() {

    }

    public MenuOneLevelRespVO(Number id, String name, Number indexNm) {
        this.name = name;
        this.id = id;
        this.indexNm = indexNm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public Number getIndexNm() {
        return indexNm;
    }

    public void setIndexNm(Number indexNm) {
        this.indexNm = indexNm;
    }
}

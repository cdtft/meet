package com.cdut.enums;

/**
 * Created by king on 2017/10/31.
 */
public enum ArticleEnum {

    NEWS("news"),
    LEARN("learn"),
    NOTICE("notice");

    private String type;

    ArticleEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

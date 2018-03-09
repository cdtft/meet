package com.cdut.enums;

/**
 * Created by king on 2017/10/25.
 */
public enum UnitTypeEnum {

    HOSTUNIT("主办单位"),
    SPONSORINGUNIT("承办单位"),
    SUPPORTUNIT("支持单位"),
    ASSISTUNIT("协办单位");

    UnitTypeEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

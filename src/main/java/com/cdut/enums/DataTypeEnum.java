package com.cdut.enums;

/**
 * Created by king on 2017/10/25.
 */
public enum DataTypeEnum {

    SINGLESELECT("单选框"),

    CHECKBOX("多选框"),

    INPUT("输入框");

    DataTypeEnum(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

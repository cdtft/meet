package com.cdut.enums;

/**
 * Created by king on 2017/10/25.
 */
public enum PictureTypeEnum {

    QRCODE("二维码"),
    CAROUSEL("轮播图"),
    LOGO("标识"),
    BACKGROUND("背景图");

    PictureTypeEnum(String value) {
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

package com.cdut.enums;

/**
 * 公共状态枚举类
 * Created by king on 2017/10/24.
 */
public enum CommonStatusEnum {

    /**
     * 可用
     */
    ENABLE(1),

    /**
     * 不可用
     */
    DISABLE(0);

    CommonStatusEnum(int value) {
        this.value = value;
    }

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

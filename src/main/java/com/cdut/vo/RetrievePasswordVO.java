package com.cdut.vo;

/**
 * Created by king on 2017/11/6.
 */
public class RetrievePasswordVO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String cnName;

    /**
     * 邮件地址
     */
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

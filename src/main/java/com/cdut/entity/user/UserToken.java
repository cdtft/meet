package com.cdut.entity.user;

import java.io.Serializable;

/**
 * 用户token模型，不会持久化到数据库
 */
public class UserToken implements Serializable {

    private static final long serialVersionUID = 7426332267123601077L;

    /**
     * user id(由IdService生成)
     */
    private Long id;

    /**
     * 随机生成的UUID
     */
    private String token;

    public UserToken(Long userId, String token) {
        this.id = userId;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

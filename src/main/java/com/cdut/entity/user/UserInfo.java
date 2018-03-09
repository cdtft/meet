package com.cdut.entity.user;

import com.cdut.entity.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by king on 2017/10/31.
 */
@Entity
@Table(name = "adm_user_info")
public class UserInfo extends BaseEntity{

    private static final long serialVersionUID = -7715035202894100320L;

    private Long userId;

    private String info;

    private String cnName;

    private String userName;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

package com.cdut.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by king on 2017/11/6.
 */
public class UserRetrievePasswordEvent extends ApplicationEvent {

    private static final long serialVersionUID = -1669871946805705295L;

    private String email;

    private String username;

    private String cnName;

    private Long id;

    public UserRetrievePasswordEvent(Object source) {
        super(source);
    }

    public UserRetrievePasswordEvent(Object source, String email, String username, Long id) {
        super(source);
        this.email = email;
        this.username = username;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

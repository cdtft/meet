package com.cdut.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by king on 2017/11/6.
 */
public class UserEvent extends ApplicationEvent {

    private static final long serialVersionUID = 4261342981052974448L;

    private Long id;

    private String username;

    private String email;

    public UserEvent(Object source) {
        super(source);
    }

    public UserEvent(Object source,Long id, String username, String email) {
        super(source);
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

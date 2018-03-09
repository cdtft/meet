package com.cdut.entity.user;

import com.cdut.entity.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色表
 * Created by king on 2017/10/24.
 */
@Entity
@Table(name = "adm_role")
public class Role extends BaseEntity {

    private static final long serialVersionUID = -1185469409617925492L;

    @Id
    private Long id;

    /**
     * 权限名称
     */
    @Column(length = 40, unique = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}

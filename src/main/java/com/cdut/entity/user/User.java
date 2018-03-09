package com.cdut.entity.user;

import com.cdut.enums.CommonStatusEnum;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 用户PO
 * Created by king on 2017/10/13.
 */
@Entity
@Table(name = "adm_user")
public class User implements Serializable {

    private static final long serialVersionUID = -4893508116146129438L;

    @Id
    private Long id;

    /**
     * 登陆名称
     */
    @Column(length = 40)
    private String username;

    /**
     * 中文名
     */
    @Column(length = 40)
    private String cnName;


    private String password;

    @Column(length = 40)
    private String qqNum;

    @Column(length = 80)
    //@Pattern(regexp = "")
    private String email;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "adm_admin_role", joinColumns = {
            @JoinColumn(name = "username", referencedColumnName = "username")}, inverseJoinColumns = {
            @JoinColumn(name = "roleId", referencedColumnName = "id")}, uniqueConstraints = {
            @UniqueConstraint(columnNames = {"username", "roleId"})})
    private List<Role> roles;

    /**
     * 创建时间
     */
    private Timestamp createTimestamp = new Timestamp(System.currentTimeMillis());

    /**
     * 是否通过邮箱验证
     */
    private CommonStatusEnum commonStatusEnum;

    public CommonStatusEnum getCommonStatusEnum() {
        return commonStatusEnum;
    }

    public void setCommonStatusEnum(CommonStatusEnum commonStatusEnum) {
        this.commonStatusEnum = commonStatusEnum;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQqNum() {
        return qqNum;
    }

    public void setQqNum(String qqNum) {
        this.qqNum = qqNum;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public Timestamp getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Timestamp createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("username", username)
                .append("password", password)
                .append("qqNum", qqNum)
                .append("roles", roles)
                .append("cnName", cnName)
                .append("email", email)
                .toString();
    }
}

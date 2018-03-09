package com.cdut.entity.menu;

import com.cdut.entity.common.BaseEntity;
import com.cdut.enums.CommonStatusEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户信息输入表单（可配置）
 * Created by king on 2017/10/25.
 */
@Entity
@Table(name = "form_field")
public class UserInfoFormPO extends BaseEntity {

    private static final long serialVersionUID = 2532157860995886465L;

    @Column(length = 40)
    private String name;

    @Column(length = 40)
    private String type;

    @Column(length = 40)
    private String label;

    @Column(length = 40)
    private String tips;

    private Boolean required;

    private Boolean readOnly;

    private Integer indexNum;

    @Column(length = 500)
    private String inputParams;

    @Column(length = 40)
    private String defaultValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public Integer getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(Integer indexNum) {
        this.indexNum = indexNum;
    }

    public String getInputParams() {
        return inputParams;
    }

    public void setInputParams(String inputParams) {
        this.inputParams = inputParams;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}

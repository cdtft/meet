package com.cdut.entity.page;

import com.cdut.entity.common.BaseEntity;
import com.cdut.enums.UnitTypeEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 首页单位信息
 * Created by king on 2017/10/25.
 */
@Entity
@Table(name = "page_unit_item")
public class UnitItemPO extends BaseEntity {

    private static final long serialVersionUID = -125948320279238289L;

    /**
     * 单位名称
     */
    @Column(length = 40)
    private String name;

    /**
     * 排序
     */
    private Integer indexNum;

    @ManyToOne(fetch = FetchType.LAZY)
    private UnitPO unitPO;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(Integer indexNum) {
        this.indexNum = indexNum;
    }

    public UnitPO getUnitPO() {
        return unitPO;
    }

    public void setUnitPO(UnitPO unitPO) {
        this.unitPO = unitPO;
    }
}

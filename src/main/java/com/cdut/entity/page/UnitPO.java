package com.cdut.entity.page;

import com.cdut.entity.common.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * Created by king on 2017/10/26.
 */
@Entity
@Table(name = "page_unit")
public class UnitPO extends BaseEntity {

    private static final long serialVersionUID = -4856690092584876035L;

    @Column(length = 40)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "unitPO")
    private List<UnitItemPO> unitItemPOS;

    @ManyToOne(fetch = FetchType.LAZY)
    private PageInfoPO pageInfoPO;

    private Integer indexNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UnitItemPO> getUnitItemPOS() {
        return unitItemPOS;
    }

    public void setUnitItemPOS(List<UnitItemPO> unitItemPOS) {
        this.unitItemPOS = unitItemPOS;
    }

    public Integer getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(Integer indexNum) {
        this.indexNum = indexNum;
    }

    public PageInfoPO getPageInfoPO() {
        return pageInfoPO;
    }

    public void setPageInfoPO(PageInfoPO pageInfoPO) {
        this.pageInfoPO = pageInfoPO;
    }
}

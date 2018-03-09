package com.cdut.entity.page;

import com.cdut.entity.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by king on 2017/10/26.
 */
@Entity
@Table(name = "page_side_time")
public class SideTimePO extends BaseEntity {

    private static final long serialVersionUID = 1161678841210537085L;

    /**
     * 事件
     */
    @Column(length = 40)
    private String event;

    /**
     * 日期
     */
    @Column(length = 40)
    private String time;

    /**
     * 组件颜色
     */
    @Column(length = 40)
    private String color;

    /**
     * 序列号
     */
    @Column(length = 40)
    private String indexNum;

    @ManyToOne(fetch = FetchType.LAZY)
    private PageInfoPO pageInfoPO;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(String indexNum) {
        this.indexNum = indexNum;
    }

    public PageInfoPO getPageInfoPO() {
        return pageInfoPO;
    }

    public void setPageInfoPO(PageInfoPO pageInfoPO) {
        this.pageInfoPO = pageInfoPO;
    }
}

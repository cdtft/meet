package com.cdut.entity.page;

import com.cdut.entity.common.BaseEntity;
import com.cdut.enums.CommonStatusEnum;
import org.springframework.web.bind.annotation.CookieValue;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * 首页信息PO
 * Created by king on 2017/10/25.
 */
@Entity
@Table(name = "page_information")
public class PageInfoPO extends BaseEntity {

    private static final long serialVersionUID = -4481486827665071920L;

    /**
     * 首页标题
     */
    @Column(length = 40)
    private String title;

    @Column(length = 40)
    private String subTitle;

    /**
     * 会议地点
     */
    @Column(length = 40)
    private String location;

    /**
     * 会议时间
     */
    @Column(length = 40)
    private String time;

    /**
     * 是否开放注册
     */
    private Boolean enableRegister;

    /**
     * 首页图片信息
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "pageInfoPO")
    private List<PicturePO> picturePOS;

    /**
     * 首页单位信息
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "pageInfoPO")
    private List<UnitPO> unitPOS;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "pageInfoPO")
    private List<SideTimePO> sideTimePOS;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "pageInfoPO")
    private List<FriendLinkPO> friendLinkPOS;


    @Enumerated(value = EnumType.STRING)
    private CommonStatusEnum commonStatusEnum;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<UnitPO> getUnitPOS() {
        return unitPOS;
    }

    public void setUnitPOS(List<UnitPO> unitPOS) {
        this.unitPOS = unitPOS;
    }

    public List<PicturePO> getPicturePOS() {
        return picturePOS;
    }

    public void setPicturePOS(List<PicturePO> picturePOS) {
        this.picturePOS = picturePOS;
    }

    public List<SideTimePO> getSideTimePOS() {
        return sideTimePOS;
    }

    public void setSideTimePOS(List<SideTimePO> sideTimePOS) {
        this.sideTimePOS = sideTimePOS;
    }


    public CommonStatusEnum getCommonStatusEnum() {
        return commonStatusEnum;
    }

    public void setCommonStatusEnum(CommonStatusEnum commonStatusEnum) {
        this.commonStatusEnum = commonStatusEnum;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public List<FriendLinkPO> getFriendLinkPOS() {
        return friendLinkPOS;
    }

    public void setFriendLinkPOS(List<FriendLinkPO> friendLinkPOS) {
        this.friendLinkPOS = friendLinkPOS;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getEnableRegister() {
        return enableRegister;
    }

    public void setEnableRegister(Boolean enableRegister) {
        this.enableRegister = enableRegister;
    }
}

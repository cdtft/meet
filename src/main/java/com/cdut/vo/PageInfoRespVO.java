package com.cdut.vo;



import java.util.List;

/**
 * Created by king on 2017/10/25.
 */
public class PageInfoRespVO {

    /**
     * logo url地址
     */
    private String logoUrl;

    private String qrTitle;

    private String logoTitle;

    private String bgUrl;

    /**
     * 二维码地址
     */
    private String qrCodeUrl;

    /**
     * 轮播图地址
     */
    private List<CarouselPictureVO> carouselList;

    /**
     * 首页名称
     */
    private String titleName;

    private String subTitle;

    /**
     * 侧边栏日期
     */
    private List<SideTimeRespVO> sideTimeRespVOList;

    /**
     * 单位
     */
    private List<UnitRespVO> unitRespVOList;

    /**
     * 友情链接
     */
    private List<FriendLinkVO> friendLinkVOList;

    private String location;

    private String time;

    private Boolean enableRegister;

    public String getBgUrl() {
        return bgUrl;
    }

    public void setBgUrl(String bgUrl) {
        this.bgUrl = bgUrl;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public List<CarouselPictureVO> getCarouselList() {
        return carouselList;
    }

    public void setCarouselList(List<CarouselPictureVO> carouselList) {
        this.carouselList = carouselList;
    }

    public List<SideTimeRespVO> getSideTimeRespVOList() {
        return sideTimeRespVOList;
    }

    public void setSideTimeRespVOList(List<SideTimeRespVO> sideTimeRespVOList) {
        this.sideTimeRespVOList = sideTimeRespVOList;
    }

    public List<UnitRespVO> getUnitRespVOList() {
        return unitRespVOList;
    }

    public void setUnitRespVOList(List<UnitRespVO> unitRespVOList) {
        this.unitRespVOList = unitRespVOList;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getQrTitle() {
        return qrTitle;
    }

    public void setQrTitle(String qrTitle) {
        this.qrTitle = qrTitle;
    }

    public String getLogoTitle() {
        return logoTitle;
    }

    public void setLogoTitle(String logoTitle) {
        this.logoTitle = logoTitle;
    }

    public List<FriendLinkVO> getFriendLinkVOList() {
        return friendLinkVOList;
    }

    public void setFriendLinkVOList(List<FriendLinkVO> friendLinkVOList) {
        this.friendLinkVOList = friendLinkVOList;
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

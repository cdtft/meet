package com.cdut.vo;

import java.util.List;

/**
 * Created by king on 2017/11/4.
 */
public class UserInfoPageVO {

    private Integer page;

    private Integer pageSize;

    private Long total;

    private List<UserInfoVO> userInfoVOList;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<UserInfoVO> getUserInfoVOList() {
        return userInfoVOList;
    }

    public void setUserInfoVOList(List<UserInfoVO> userInfoVOList) {
        this.userInfoVOList = userInfoVOList;
    }
}

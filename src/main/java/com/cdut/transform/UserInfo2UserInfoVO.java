package com.cdut.transform;

import com.cdut.entity.user.UserInfo;
import com.cdut.vo.UserInfoVO;
import com.google.common.base.Function;

import javax.annotation.Nullable;
import java.text.SimpleDateFormat;

/**
 * Created by king on 2017/10/31.
 */
public class UserInfo2UserInfoVO implements Function<UserInfo, UserInfoVO> {

    @Nullable
    @Override
    public UserInfoVO apply(@Nullable UserInfo po) {

        if (po != null) {
            UserInfoVO vo = new UserInfoVO();
            vo.setId(po.getId());
            vo.setUserId(po.getUserId());
            vo.setInfo(po.getInfo());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = format.format(po.getCreateTimestamp());
            vo.setCreateTime(createTime);
            String updateTime = format.format(po.getUpdateTimestamp());
            vo.setUpdateTime(updateTime);
            return vo;
        }
        return null;
    }
}

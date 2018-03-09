package com.cdut.transform;


import com.cdut.entity.user.User;
import com.cdut.vo.UserRequestVo;
import com.google.common.base.Function;

import javax.annotation.Nullable;

/**
 * user vo trans po
 * Created by king on 2017/9/13.
 */
public class UserReqVo2User implements Function<UserRequestVo, User> {

    @Nullable
    @Override
    public User apply(@Nullable UserRequestVo vo) {
        if (vo != null) {
            User user = new User();
            user.setUsername(vo.getUsername().trim());
            user.setPassword(vo.getPassword().trim());
            user.setQqNum(vo.getQqNum().trim());
            user.setCnName(vo.getCnName().trim());
            user.setEmail(vo.getEmail().trim());
            return user;
        }
        return null;
    }
}

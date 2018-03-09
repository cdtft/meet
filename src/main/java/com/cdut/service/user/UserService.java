package com.cdut.service.user;

import com.cdut.entity.common.JsonResult;
import com.cdut.entity.user.User;
import com.cdut.vo.PasswordVO;
import com.cdut.vo.RetrievePasswordVO;
import com.cdut.vo.UserRequestVo;

/**
 * Created by king on 2017/10/13.
 */
public interface UserService {

    /**
     * 用户登陆
     * @param username
     * @param password
     * @return
     */
    JsonResult login(String username, String password);

    /**
     * 推出登陆
     * @param
     * @return
     */
    JsonResult logout(Long id);

    /**
     * 验证用户名是否被注册
     * @param username
     * @return
     */
    JsonResult usernameIsExist(String username);

    /**
     * 用户注册
     * @param vo
     * @return
     */
    JsonResult register(UserRequestVo vo);

    /**
     * 修改密码
     * @return
     */
    JsonResult resetPassword(Long userId, String newPassword, String oldPassword);

    /**
     * 查询所有user
     * @return
     */
    JsonResult findAll();

    JsonResult checkVerifyCode(String code, String correctCode);

    JsonResult checkToken(String token);

    JsonResult emailIsExist(String email);

    JsonResult retrievePassword(RetrievePasswordVO vo);

    JsonResult setUserStatusEnable(Long userId);

    void setPassword(Long userId, String password);

	JsonResult deleteAllUserInfoAndUser();
}

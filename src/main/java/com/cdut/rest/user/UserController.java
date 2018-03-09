package com.cdut.rest.user;

import com.cdut.annotation.Authorization;
import com.cdut.annotation.LoginUser;
import com.cdut.annotation.RoleRequired;
import com.cdut.dao.mysql.user.UserInfoRepository;
import com.cdut.entity.common.JsonResult;
import com.cdut.entity.user.User;
import com.cdut.enums.ResultStatus;
import com.cdut.rest.BaseRestController;
import com.cdut.security.JWTUtil;
import com.cdut.service.user.UserInfoService;
import com.cdut.service.user.UserService;
import com.cdut.util.CheckImgService;
import com.cdut.vo.EmailTokenVO;
import com.cdut.vo.EmailVO;
import com.cdut.vo.PageVO;
import com.cdut.vo.PasswordVO;
import com.cdut.vo.RetrievePasswordVO;
import com.cdut.vo.UserInfoVO;
import com.cdut.vo.UserRequestVo;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by king on 2017/10/13.
 */
@RestController
public class UserController extends BaseRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private CheckImgService checkImgService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RedisTemplate stringRedisTemplate;

    /**
     * 用户登陆方法,执行成功后data中封装了UserToken信息
     */
    @ApiOperation(value = "用户登陆")
    @ApiImplicitParam(name = "vo", value = "用户名密码", required = true, dataType = "UserRequestVo")
    @RequestMapping(value = "v1/api/session", method = RequestMethod.POST)
    public JsonResult login(@RequestBody UserRequestVo vo) {

        if (StringUtils.isBlank(vo.getUsername()) || StringUtils.isBlank(vo.getPassword())) {

            return new JsonResult("用户名或密码为空", ResultStatus.INVALID_REQUEST);
        }
        return userService.login(vo.getUsername(), vo.getPassword());
    }

    /**
     * 退出登陆
     *
     * @param
     * @return
     */
    @Authorization
    @RequestMapping(value = "v1/api/session", method = RequestMethod.DELETE)
    public JsonResult logout(HttpServletRequest request) {
        String token = request.getHeader("authorization");
        Claims claims = JWTUtil.getClaimsFromToken(token);
        Long userId = Long.parseLong(claims.get("ID").toString());
        return userService.logout(userId);
    }

    /**
     * 用户注册
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "v1/api/user", method = RequestMethod.POST)
    public JsonResult register(@RequestBody UserRequestVo vo) {

        return userService.register(vo);
    }

    /**
     * 检查用户名是否被注册
     *
     * @param username user表主键
     * @return
     */
    @RequestMapping(value = "v1/api/user/{username}", method = RequestMethod.POST)
    public JsonResult checkUsername(@PathVariable("username") String username) {
        return userService.usernameIsExist(username);
    }

    /**
     * 校验email是否已经存在
     * @param
     * @return
     */
    @RequestMapping(value = "v1/api/emails", method = RequestMethod.POST)
    public JsonResult checkEmail(@RequestBody EmailVO vo) {
        return userService.emailIsExist(vo.getEmail());
    }

    /**
     * 找回密码，通过邮件认证
     * @return
     */
    @RequestMapping(value = "v1/api/user/password", method = RequestMethod.POST)
    public JsonResult retrievePassword(@RequestBody RetrievePasswordVO vo) {
        return userService.retrievePassword(vo);
    }

    /**
     * 只用传newPassword, userId在用户登陆后就放在了request中（该接口需要登陆验证）
     *
     * @param
     * @param
     * @return
     */
    @Authorization
    @RequestMapping(value = "v1/api/user", method = RequestMethod.PUT)
    public JsonResult resetPassword(HttpServletRequest request, @RequestBody PasswordVO vo) {
        String token = request.getHeader("authorization");
        Long userId = JWTUtil.getUserId(token);
        String newPassword = vo.getNewPassword();
        if (StringUtils.isBlank(newPassword)) {
            logger.info("[{}] 用户重设密码失败", userId);
            return new JsonResult("新的密码为空", ResultStatus.INVALID_REQUEST);
        }
        return userService.resetPassword(userId, newPassword, vo.getOldPassword());
    }

    @RequestMapping(value = "v1/api/user/identification", method = RequestMethod.POST)
    public JsonResult verifyUser(@RequestBody EmailTokenVO vo) {
        String token = vo.getToken();
        Claims claims = JWTUtil.getClaimsFromToken(token);
        Long userId = null;
        try {
            userId = Long.parseLong(claims.get("ID").toString());
        } catch (NullPointerException e){
            e.printStackTrace();
            logger.info("未解析出userId");
            return new JsonResult("认证失败", ResultStatus.INVALID_REQUEST);
        }
        logger.info("email:[{}]", userId);
        String cacheToken = null;
        try {
            cacheToken = stringRedisTemplate.opsForValue().get("email:" + userId).toString();
        } catch (NullPointerException e) {
            return new JsonResult("链接已经过期", ResultStatus.FORBIDDEN);
        }

        if (StringUtils.isEmpty(cacheToken)) {
            logger.info("[{}] 链接过期", userId);
            return new JsonResult("链接已经过期", ResultStatus.FORBIDDEN);
        }
        if (!token.equals(cacheToken)) {
            logger.info("[{}] 认证失败");
            return new JsonResult("认证失败", ResultStatus.FORBIDDEN);
        }
        return userService.setUserStatusEnable(userId);
    }

    @RequestMapping(value = "v1/api/user/verifyToken", method = RequestMethod.POST)
    public JsonResult verifyResetToken(@RequestBody EmailTokenVO vo) {
        String token = vo.getToken();
        Claims claims = JWTUtil.getClaimsFromToken(token);
        Long userId = null;
        try {
            userId = Long.parseLong(claims.get("ID").toString());
        } catch (NullPointerException e){
            e.printStackTrace();
            logger.info("未解析出userId");
            return new JsonResult("认证失败", ResultStatus.INVALID_REQUEST);
        }
        logger.info("email:[{}]", userId);
        String cacheToken = null;
        try {
            cacheToken = stringRedisTemplate.opsForValue().get("reset:" + userId).toString();
        } catch (NullPointerException e) {
            return new JsonResult("链接过期", ResultStatus.FORBIDDEN);
        }
        if (StringUtils.isBlank(cacheToken)) {
            logger.info("[{}] 链接过期", userId);
            return new JsonResult("链接已经过期", ResultStatus.FORBIDDEN);
        }
        if (!token.equals(cacheToken)) {
            logger.info("[{}] 认证失败");
            return new JsonResult("认证失败", ResultStatus.FORBIDDEN);
        }
        userService.setPassword(userId, vo.getPassword());
        return new JsonResult("OK", ResultStatus.SUCCESS);
    }

    /**
     * 对管理员用户开放，查找所用的用户（还未验证该用户是否为拥有管理员权限）
     *
     * @return
     */
    @Authorization
    @RequestMapping(value = "v1/api/users", method = RequestMethod.GET)
    public JsonResult findAll() {
        return userService.findAll();
    }

    /**
     * 获取验证码图片
     */
    @RequestMapping(value = "v1/api/verifyCode", method = RequestMethod.GET)
    public void getVerifyCode(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        checkImgService.execute(request, response, session);
    }

    /**
     * 验证用户输入的验证码是否正确
     * @param code
     * @param session
     * @return
     */
    @RequestMapping(value = "v1/api/checkImg/{customerVerifyCode}", method = RequestMethod.POST)
    public JsonResult checkVerifyCode(@PathVariable("customerVerifyCode") String code, HttpSession session) {

        String correct;
        try {
            correct = session.getAttribute("verifyCode").toString();
        } catch (Exception e) {
            logger.error("从session中获取VerifyCode失败");
            return new JsonResult(Boolean.FALSE, "从session中获取VerifyCode失败", ResultStatus.SUCCESS);
        }
        return userService.checkVerifyCode(code, correct);
    }

    @RequestMapping(value = "v1/api/token", method = RequestMethod.GET)
    public JsonResult checkToken(HttpServletRequest request) {
        String authorization = request.getHeader("authorization");
        logger.info("验证token [{}]", authorization);
        return userService.checkToken(authorization);
    }

    //-----------------------------------------------------------------------------------

    @Authorization
    @RequestMapping(value = "v1/api/userInfo", method = RequestMethod.POST)
    public JsonResult saveUserInfo(HttpServletRequest request, @RequestBody UserInfoVO vo) {
        String token = request.getHeader("authorization");
        Long userId = JWTUtil.getUserId(token);
        String userInfo = vo.getInfo();
        return userInfoService.save(userInfo, userId);
    }

    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/userInfos", method = RequestMethod.GET)
    public JsonResult getAllUserInfo(PageVO vo) {
        return userInfoService.findAll(vo);
    }

    @Authorization
    @RoleRequired(role = "admin")
    @RequestMapping(value = "v1/api/userInfo/{id}", method = RequestMethod.DELETE)
    public JsonResult deleteByUserInfoId(@PathVariable("id") Long id) {
        return userInfoService.deleteByUserInfoId(id);
    }

    @Authorization
    @RequestMapping(value = "v1/api/userInfo", method = RequestMethod.GET)
    public JsonResult getUserInfoByUserId(HttpServletRequest request) {
        String token = request.getHeader("authorization");
        Long userId = JWTUtil.getUserId(token);
        return userInfoService.findUserInfoByUserId(userId);
    }
    
    @Authorization
    @RequestMapping(value = "v1/api/userInfos", method = RequestMethod.DELETE)
    public JsonResult deleteAll() {
    	return userService.deleteAllUserInfoAndUser();
    }
}

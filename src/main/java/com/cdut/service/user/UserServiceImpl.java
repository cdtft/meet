package com.cdut.service.user;

import com.cdut.dao.mysql.page.PageRepository;
import com.cdut.dao.mysql.user.RoleRepository;
import com.cdut.dao.mysql.user.UserInfoRepository;
import com.cdut.dao.mysql.user.UserRepository;
import com.cdut.entity.common.JsonResult;
import com.cdut.entity.page.PageInfoPO;
import com.cdut.entity.user.Role;
import com.cdut.entity.user.User;
import com.cdut.entity.user.UserInfo;
import com.cdut.entity.user.UserToken;
import com.cdut.enums.CommonStatusEnum;
import com.cdut.enums.ResultStatus;
import com.cdut.enums.RoleEnum;
import com.cdut.event.UserEvent;
import com.cdut.event.UserRetrievePasswordEvent;
import com.cdut.security.JWTUtil;
import com.cdut.security.TokenManager;
import com.cdut.service.BaseService;
import com.cdut.transform.User2UserRespVo;
import com.cdut.transform.UserReqVo2User;
import com.cdut.vo.PasswordVO;
import com.cdut.vo.RetrievePasswordVO;
import com.cdut.vo.UserRequestVo;
import com.cdut.vo.UserRespVo;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by king on 2017/10/13.
 */
@Service
public class UserServiceImpl extends BaseService implements UserService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RedisTemplate stringRedisTemplate;

    @Autowired
    private PageRepository pageRepository;
    
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public JsonResult login(String username, String password) {

        User user = userRepository.findByUsername(username);
        if (user == null || !StringUtils.equals(user.getPassword(), password)) {
            logger.info("密码或密码不正确, 用户[{}]", user);
            return new JsonResult("密码或用户名不正确", ResultStatus.NOT_FOUND);
        }
        if (user.getCommonStatusEnum().equals(CommonStatusEnum.DISABLE)) {
            logger.info("[{}] 未通过邮箱验证", user);
            logger.info("发送认证邮件 [{}]", user);
            applicationEventPublisher.publishEvent(new UserEvent(this, user.getId(), user.getUsername(), user.getEmail()));
            return new JsonResult("未通过邮箱验证不能登陆", ResultStatus.FORBIDDEN);
        }
        logger.info("创建token信息");
        UserToken tokenModel = tokenManager.createTokenUser(user.getId());
        Map<String, Object> tokenInfo = Maps.newHashMap();
        tokenInfo.put("userId", tokenModel.getId().toString());
        tokenInfo.put("authorization", tokenModel.getToken());
        return new JsonResult(tokenInfo, "登陆成功", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult logout(Long id) {
        tokenManager.deleteToken(id);
        return new JsonResult("用户退出登陆", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult usernameIsExist(String username) {

        if (StringUtils.isBlank(username)) {
            logger.info("校验有户名为空");
            return new JsonResult("校验用户名为空", ResultStatus.INVALID_REQUEST);
        }
        User user =  userRepository.findByUsername(username);
        if (user == null) {
            return new JsonResult(Boolean.FALSE ,"该用户名可用", ResultStatus.SUCCESS);
        }
        return new JsonResult(Boolean.TRUE, "该用户名已存在", ResultStatus.FORBIDDEN);
    }

    @Transactional
    @Override
    public JsonResult register(UserRequestVo vo) {
        PageInfoPO pageInfoPO = pageRepository.findByCommonStatusEnum(CommonStatusEnum.ENABLE);
        if (!pageInfoPO.getEnableRegister() || pageInfoPO.getEnableRegister() == null) {
            logger.info("register 还未开放注册");
            return new JsonResult("还未开放注册", ResultStatus.FORBIDDEN);
        }
        if (StringUtils.isBlank(vo.getUsername())) {
            logger.info("register 用户提交的用户名为空[{}]", vo);
            return new JsonResult(null, "用户名不能为空", ResultStatus.INVALID_REQUEST);
        }
        if (StringUtils.isBlank(vo.getPassword())) {
            logger.info("register 用户提交的密码为空[{}]", vo);
            return new JsonResult("密码不能为空", ResultStatus.INVALID_REQUEST);
        }
        User existUser = userRepository.findByUsername(vo.getUsername());
        if (existUser != null) {
            logger.info("该用户已经存在[{}]", vo.getUsername());
            return new JsonResult("该用户名已经存在", ResultStatus.INVALID_REQUEST);
        }
        User user = new UserReqVo2User().apply(vo);
        user.setId(idService.nextId());
        user.setCommonStatusEnum(CommonStatusEnum.DISABLE);
        Role role = roleRepository.findByName(RoleEnum.NORMAL.getRoleName());
        List<Role> roleList = Lists.newArrayList();
        roleList.add(role);
        user.setRoles(roleList);
        User returnPO = userRepository.save(user);
        logger.info("用户注册，用户信息已保存 [{}]", user);
        logger.info("发送事件，开始对该用户发送邮箱认证邮件");
        applicationEventPublisher.publishEvent(new UserEvent(this, returnPO.getId(), returnPO.getUsername(), returnPO.getEmail()));
        return new JsonResult("注册成功", ResultStatus.CREATED);
    }

    @Transactional
    @Override
    public JsonResult resetPassword(Long userId, String newPassword, String oldPassword) {
        User user = userRepository.findOne(userId);
        if (!StringUtils.equals(oldPassword.trim(), user.getPassword())) {
            logger.info("oldPassword不匹配 [{}] ", user.getUsername());
            new JsonResult("输入的密码有误", ResultStatus.INVALID_REQUEST);
        }
        userRepository.updatePasswordById(userId, newPassword.trim());
        logout(userId);
        logger.info("销毁[{}] token", userId);
        return new JsonResult(null, "密码修改成功", ResultStatus.CREATED);
    }

    @Override
    public JsonResult findAll() {
        List<User> users = userRepository.findAll();
        List<UserRespVo> userRespVos = Lists.transform(users, new User2UserRespVo());
        return new JsonResult(userRespVos, "获取所用的用户", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult checkVerifyCode(String code, String correctCode) {
        if (code.equalsIgnoreCase(correctCode)) {
            return new JsonResult(Boolean.TRUE, "验证成功", ResultStatus.SUCCESS);
        }
        return new JsonResult(Boolean.FALSE, "验证失败", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return new JsonResult("token为空", ResultStatus.NOT_FOUND);
        }
        UserToken userToken = tokenManager.getToken(token);
        if (userToken == null) {
            return new JsonResult("token信息错误", ResultStatus.NOT_FOUND);
        }
        Boolean isLogin = tokenManager.checkToken(userToken);
        if (isLogin) {
            logger.info("token验证成功, 用户id [{}]", userToken.getId());
            Claims claims = JWTUtil.getClaimsFromToken(token);
            List<Role> roleList = (List<Role>) claims.get("ROLE");
            return new JsonResult(roleList, "已登陆", ResultStatus.SUCCESS);
        } else {
            return new JsonResult(false, "token过期", ResultStatus.FORBIDDEN);
        }
    }

    @Override
    public JsonResult emailIsExist(String email) {
        if (StringUtils.isBlank(email)) {
            return new JsonResult("邮件地址为空", ResultStatus.INVALID_REQUEST);
        }
        User user = userRepository.findByEmail(email.trim());
        if (user != null) {
            logger.info("[{}] 已经被用户[{}] 注册", email, user);
            return new JsonResult(true, "email已存在", ResultStatus.SUCCESS);
        }
        return new JsonResult(false, "email未被注册", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult retrievePassword(RetrievePasswordVO vo) {
        /*if (StringUtils.isBlank(vo.getUsername())) {
            return new JsonResult("username不能为空", ResultStatus.INVALID_REQUEST);
        }*/
        if (StringUtils.isBlank(vo.getEmail())) {
            return new JsonResult("email不能为空", ResultStatus.INVALID_REQUEST);
        }
        /*User user = userRepository.findByUsername(vo.getUsername().trim());
        if (user == null) {
            return new JsonResult("用户名不存在", ResultStatus.NOT_FOUND);
        }*/
        User user = userRepository.findByEmail(vo.getEmail());
        if (user == null) {
            logger.info("retrievePassword 该邮箱不存在 [{}]", vo.getEmail());
            return new JsonResult("该邮箱不存在", ResultStatus.INVALID_REQUEST);
        }
        if (user.getCommonStatusEnum().equals(CommonStatusEnum.DISABLE)) {
            logger.info("retrievePassword [{}]该用户还未通过邮箱认证", user);
            return new JsonResult("该账号还未通过邮箱认证", ResultStatus.INVALID_REQUEST);
        }
        /*if (!vo.getUsername().equals(user.getUsername())  || !vo.getEmail().equals(user.getEmail())) {
            return new JsonResult("输入信息不匹配", ResultStatus.FORBIDDEN);
        }*/
        applicationEventPublisher.publishEvent(new UserRetrievePasswordEvent(this,
                user.getEmail(), user.getUsername(), user.getId()));
        return new JsonResult("已发送找回密码邮件", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult setUserStatusEnable(Long userId) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            logger.info("未找到该用户 [{}]", userId);
            return new JsonResult("未找到该用户", ResultStatus.NO_CONTENT);
        }
        user.setCommonStatusEnum(CommonStatusEnum.ENABLE);
        User returnPO = userRepository.save(user);
        logger.info("[{}] 已通过验证", returnPO);
        //删除redis中的token缓存
        stringRedisTemplate.delete("email:" + userId);
        return new JsonResult("已通过验证", ResultStatus.SUCCESS);
    }

    @Override
    @Transactional
    public void setPassword(Long userId, String password) {
        User user = userRepository.findOne(userId);
        user.setPassword(password);
        userRepository.save(user);
        stringRedisTemplate.delete("reset:"+userId);
        logger.info("删除resettoken[{}]", userId);
    }

	@Override
	public JsonResult deleteAllUserInfoAndUser() {
		List<User> userList = userRepository.findAll();
		userList = userList.stream().filter(p -> {
			List<Role> roleList = p.getRoles();
			for (Role role : roleList) {
				if (role.getName().equals("admin")) {
					return false;
				}
			}
			return true;
		}).collect(Collectors.toList());
		userRepository.delete(userList);
		List<UserInfo> userInfoList = userInfoRepository.findAll();
		userInfoRepository.delete(userInfoList);
		return new JsonResult("清空数据", ResultStatus.SUCCESS);
	}
}

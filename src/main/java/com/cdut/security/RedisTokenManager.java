package com.cdut.security;

import com.cdut.dao.mysql.user.UserRepository;
import com.cdut.entity.user.Role;
import com.cdut.entity.user.User;
import com.cdut.entity.user.UserToken;
import com.google.common.collect.Maps;
import io.jsonwebtoken.Claims;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * redis Token验证器
 * Created by king on 2017/9/11.
 */
@Component
public class RedisTokenManager implements TokenManager {

    private static final Logger logger = Logger.getLogger(RedisTokenManager.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public RedisTokenManager(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        redisTemplate.setKeySerializer(new JdkSerializationRedisSerializer());
    }

    @Override
    public UserToken createTokenUser(Long userId) {
        logger.info("为用户生成token信息");
        //使用uuid作为源token信息
        User loginUser = userRepository.findById(userId);
        List<Role> roleList = loginUser.getRoles();
        List<String> roleName = roleList.stream().map(Role::getName).collect(Collectors.toList());
        //目前一个账户只有一个角色
        Map<String, Object> claims = Maps.newHashMap();
        // TODO 加入用户的角色权限信息
        claims.put("ID", userId);
        claims.put("CREATE_TIME", new Date(System.currentTimeMillis()));
        claims.put("ROLE", roleName);
        String token = JWTUtil.generateToken(claims);
        UserToken userToken = new UserToken(userId, token);
        //todo 将时间提取出来
        redisTemplate.boundValueOps(userId).set(token, 10, TimeUnit.MINUTES);
        return userToken;
    }

    @Override
    public boolean checkToken(UserToken tokenUser) {
        if (tokenUser == null) {
            return false;
        }
        try {
            String token = (String) redisTemplate.boundValueOps(tokenUser.getId()).get();
            if (token == null || !tokenUser.getToken().equals(token)) {
                return false;
            }
            //验证成功存重新入redis延长token信息过期时间
            redisTemplate.boundValueOps(tokenUser.getId()).expire(30, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public UserToken getToken(String authentication) {

        try {
            Claims claims = JWTUtil.getClaimsFromToken(authentication);
            if (claims == null) {
                return null;
            }
            Long userId = Long.parseLong(claims.get("ID").toString());
            return new UserToken(userId, authentication);
        } catch (Exception e) {
            logger.info("获取UserToken失败");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteToken(Long userId) {

        redisTemplate.delete(userId);
    }
}

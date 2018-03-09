package com.cdut.service.user;

import com.cdut.dao.mysql.user.UserInfoRepository;
import com.cdut.dao.mysql.user.UserRepository;
import com.cdut.entity.common.JsonResult;
import com.cdut.entity.user.User;
import com.cdut.entity.user.UserInfo;
import com.cdut.enums.ResultStatus;
import com.cdut.service.BaseService;
import com.cdut.transform.UserInfo2UserInfoVO;
import com.cdut.vo.PageVO;
import com.cdut.vo.UserInfoPageVO;
import com.cdut.vo.UserInfoVO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by king on 2017/10/31.
 */
@Service
public class UserInfoServiceImpl extends BaseService implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public JsonResult save(String info, Long id) {

        UserInfo po = userInfoRepository.findByUserId(id);
        if (po != null) {
            po.setInfo(info);
            UserInfo returnPO = userInfoRepository.save(po);
            UserInfoVO vo = new UserInfoVO();
            vo.setId(returnPO.getId());
            vo.setUserId(returnPO.getUserId());
            vo.setInfo(returnPO.getInfo());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = format.format(returnPO.getCreateTimestamp());
            vo.setCreateTime(createTime);
            String updateTime = format.format(returnPO.getUpdateTimestamp());
            vo.setUpdateTime(updateTime);
            return new JsonResult(vo, "更新成功", ResultStatus.SUCCESS);
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(id);
        userInfo.setInfo(info);
        UserInfo returnPO = userInfoRepository.save(userInfo);
        UserInfoVO vo = new UserInfoVO();
        vo.setId(returnPO.getId());
        vo.setUserId(returnPO.getUserId());
        vo.setInfo(returnPO.getInfo());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(returnPO.getCreateTimestamp());
        vo.setCreateTime(createTime);
        String updateTime = format.format(returnPO.getUpdateTimestamp());
        vo.setUpdateTime(updateTime);
        return new JsonResult(vo, "保存成功", ResultStatus.SUCCESS);
    }


    @Override
    public JsonResult deleteByUserInfoId(Long userId) {
        UserInfo userInfo = userInfoRepository.findByUserId(userId);
        if (userInfo != null) {
            return new JsonResult("该id没有对应的用户信息", ResultStatus.SUCCESS);
        }
        userInfoRepository.delete(userInfo.getId());
        return new JsonResult("删除成功", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult findAll(PageVO vo) {

        if (vo.getPage() == null || vo.getPageSize() == null) {
            logger.info("获取所有的已填用户信息");
            List<UserInfo> allUserInfo = userInfoRepository.findAll();
            if (CollectionUtils.isEmpty(allUserInfo)) {
                logger.info("没有用户信息");
                return new JsonResult("没有任何的有户信息", ResultStatus.SUCCESS);
            }
            List<Long> userIdList = allUserInfo.stream().map(UserInfo::getUserId).collect(Collectors.toList());
            List<User> userList = userRepository.findByIdIn(userIdList);
            Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, Function.identity()));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<UserInfoVO> userInfoVOList = Lists.newArrayList();
            for (UserInfo info : allUserInfo) {
                if (userMap.get(info.getUserId()) == null) {
                    logger.info("未找到对应信息的用户 [{}]", info.getId());
                    continue;
                }
                UserInfoVO userInfoVO = new UserInfoVO();
                userInfoVO.setUserId(info.getUserId());
                userInfoVO.setCnName(userMap.get(info.getUserId()).getCnName());
                userInfoVO.setUsername(userMap.get(info.getUserId()).getUsername());
                userInfoVO.setInfo(info.getInfo());
                String createTime = format.format(info.getCreateTimestamp());
                String updateTime = format.format(info.getUpdateTimestamp());
                userInfoVO.setCreateTime(createTime);
                userInfoVO.setUpdateTime(updateTime);
                userInfoVOList.add(userInfoVO);
            }
            logger.info("返回所有的用户信息");
            return new JsonResult(userInfoVOList, "Ok", ResultStatus.SUCCESS);
        }
        Page<UserInfo> userInfoPage = userInfoRepository.findAll(new PageRequest(vo.getPage()-1, vo.getPageSize(),
                new Sort(Sort.Direction.DESC, "createTimestamp")));
        List<UserInfo> userInfoList = userInfoPage.getContent();
        List<Long> userIdList = userInfoList.stream().map(UserInfo::getUserId).collect(Collectors.toList());
        List<User> userList = userRepository.findByIdIn(userIdList);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, Function.identity()));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<UserInfoVO> userInfoVOList = Lists.newArrayList();
        for (UserInfo info : userInfoList) {
            if (userMap.get(info.getUserId()) == null) {
                logger.info("未找到对应信息的用户 [{}]", info.getId());
                continue;
            }
            UserInfoVO userInfoVO = new UserInfoVO();
            userInfoVO.setUserId(info.getUserId());
            userInfoVO.setCnName(userMap.get(info.getUserId()).getCnName());
            userInfoVO.setUsername(userMap.get(info.getUserId()).getUsername());
            userInfoVO.setInfo(info.getInfo());
            String createTime = format.format(info.getCreateTimestamp());
            String updateTime = format.format(info.getUpdateTimestamp());
            userInfoVO.setCreateTime(createTime);
            userInfoVO.setUpdateTime(updateTime);
            userInfoVOList.add(userInfoVO);
        }
        UserInfoPageVO pageVO = new UserInfoPageVO();
        pageVO.setTotal(userInfoPage.getTotalElements());
        pageVO.setPageSize(vo.getPageSize());
        pageVO.setPage(vo.getPage());
        pageVO.setUserInfoVOList(userInfoVOList);
        return new JsonResult(pageVO, "Ok", ResultStatus.SUCCESS);
    }

    @Override
    public JsonResult findUserInfoByUserId(Long id) {
        UserInfo userInfo = userInfoRepository.findByUserId(id);
        if (userInfo == null) {
            logger.info("没有找到当前用户的信息id :[{}]", id);
            return new JsonResult("没有找到当前用户的信息id :[{}]", ResultStatus.SUCCESS);
        }
        UserInfoVO vo = new UserInfo2UserInfoVO().apply(userInfo);
        return new JsonResult(vo, "OK", ResultStatus.SUCCESS);
    }

}

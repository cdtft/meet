package com.cdut.service.page;

import com.cdut.entity.common.JsonResult;
import com.cdut.vo.FriendLinkVO;

/**
 * Created by king on 2017/11/6.
 */
public interface FriendLinkService {

    JsonResult addFriendLink(FriendLinkVO vo);

    JsonResult updateFriendLink(FriendLinkVO vo);

    JsonResult deleteFriendLink(Long id);

    JsonResult findAll();
}

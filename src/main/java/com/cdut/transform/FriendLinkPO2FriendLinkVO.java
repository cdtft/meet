package com.cdut.transform;

import com.cdut.entity.page.FriendLinkPO;
import com.cdut.vo.FriendLinkVO;
import com.google.common.base.Function;

import javax.annotation.Nullable;

/**
 * Created by king on 2017/11/6.
 */
public class FriendLinkPO2FriendLinkVO implements Function<FriendLinkPO, FriendLinkVO> {

    @Nullable
    @Override
    public FriendLinkVO apply(@Nullable FriendLinkPO po) {

        if (po != null) {
            FriendLinkVO vo = new FriendLinkVO();
            vo.setId(po.getId());
            vo.setIndexNum(po.getIndexNum());
            vo.setUrl(po.getUrl());
            vo.setText(po.getText());
            return vo;
        }
        return null;
    }
}

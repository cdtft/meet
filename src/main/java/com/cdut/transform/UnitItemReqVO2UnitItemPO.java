package com.cdut.transform;

import com.cdut.entity.page.UnitItemPO;
import com.cdut.util.IdService;
import com.cdut.vo.UnitItemReqVO;
import com.google.common.base.Function;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.sql.Timestamp;

/**
 * Created by king on 2017/10/28.
 */
public class UnitItemReqVO2UnitItemPO implements Function<UnitItemReqVO, UnitItemPO> {

    private IdService idService;

    public UnitItemReqVO2UnitItemPO(IdService idService) {
        this.idService = idService;
    }

    @Nullable
    @Override
    public UnitItemPO apply(@Nullable UnitItemReqVO input) {
        if (input != null) {
            UnitItemPO po = new UnitItemPO();
            po.setIndexNum(input.getIndexNum());
            po.setName(input.getName());
            return po;
        }
        return null;
    }
}

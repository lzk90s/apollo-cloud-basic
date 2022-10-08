package pers.kun.core.web.model.unsupported;

import pers.kun.core.base.BaseDO;
import pers.kun.core.web.model.BaseAddCmd;

/**
 * @author : qihang.liu
 * @date 2021-11-25
 */
public class UnsupportedAddCmd<ENTITY extends BaseDO> extends BaseAddCmd<ENTITY> {
    public UnsupportedAddCmd() {
        throw new UnsupportedOperationException();
    }
}

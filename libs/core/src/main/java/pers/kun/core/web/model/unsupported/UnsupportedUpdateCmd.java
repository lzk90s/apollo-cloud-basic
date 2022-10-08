package pers.kun.core.web.model.unsupported;

import pers.kun.core.base.BaseDO;
import pers.kun.core.web.model.BaseUpdateCmd;

/**
 * @author : qihang.liu
 * @date 2021-11-25
 */
public class UnsupportedUpdateCmd<ENTITY extends BaseDO> extends BaseUpdateCmd<ENTITY> {
    public UnsupportedUpdateCmd() {
        throw new UnsupportedOperationException();
    }
}

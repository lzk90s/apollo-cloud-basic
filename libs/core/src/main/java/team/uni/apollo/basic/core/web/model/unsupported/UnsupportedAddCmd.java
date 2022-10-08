package team.uni.apollo.basic.core.web.model.unsupported;

import team.uni.apollo.basic.core.base.BaseDO;
import team.uni.apollo.basic.core.web.model.BaseAddCmd;

/**
 * @author : qihang.liu
 * @date 2021-11-25
 */
public class UnsupportedAddCmd<ENTITY extends BaseDO> extends BaseAddCmd<ENTITY> {
    public UnsupportedAddCmd() {
        throw new UnsupportedOperationException();
    }
}

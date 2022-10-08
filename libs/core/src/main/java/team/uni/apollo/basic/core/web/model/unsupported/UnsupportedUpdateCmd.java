package team.uni.apollo.basic.core.web.model.unsupported;

import team.uni.apollo.basic.core.base.BaseDO;
import team.uni.apollo.basic.core.web.model.BaseUpdateCmd;

/**
 * @author : qihang.liu
 * @date 2021-11-25
 */
public class UnsupportedUpdateCmd<ENTITY extends BaseDO> extends BaseUpdateCmd<ENTITY> {
    public UnsupportedUpdateCmd() {
        throw new UnsupportedOperationException();
    }
}

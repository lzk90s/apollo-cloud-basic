package team.javis.apollo.basic.core.web.model.unsupported;

import team.javis.apollo.basic.core.base.BaseDO;
import team.javis.apollo.basic.core.web.model.BaseImportCmd;

/**
 * @author : qihang.liu
 * @date 2021-11-25
 */
public class UnsupportedImportCmd<ENTITY extends BaseDO> extends BaseImportCmd<ENTITY> {
    public UnsupportedImportCmd() {
        throw new UnsupportedOperationException();
    }
}

package pers.kun.core.web.model.unsupported;

import pers.kun.core.base.BaseDO;
import pers.kun.core.web.model.BaseImportCmd;

/**
 * @author : qihang.liu
 * @date 2021-11-25
 */
public class UnsupportedImportCmd<ENTITY extends BaseDO> extends BaseImportCmd<ENTITY> {
    public UnsupportedImportCmd() {
        throw new UnsupportedOperationException();
    }
}

package pers.kun.core.web.model;

import pers.kun.core.base.BaseDO;

/**
 * cmd基类
 *
 * @author : qihang.liu
 * @date 2021-11-23
 */
public class BaseCmd<DO extends BaseDO> {
    public DO convertToDO() {
        return null;
    }
}

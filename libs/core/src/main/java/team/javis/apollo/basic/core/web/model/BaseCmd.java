package team.javis.apollo.basic.core.web.model;

import team.javis.apollo.basic.core.base.BaseDO;

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

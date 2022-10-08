package pers.kun.core.web.model.unsupported;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import pers.kun.core.base.BaseDO;
import pers.kun.core.web.model.BasePageQuery;

/**
 * @author : qihang.liu
 * @date 2021-11-25
 */
public class UnsupportedPageQuery<ENTITY extends BaseDO> extends BasePageQuery<ENTITY> {
    public UnsupportedPageQuery() {
        throw new UnsupportedOperationException();
    }

    @Override
    public QueryWrapper<ENTITY> buildQueryWrapper() {
        throw new UnsupportedOperationException();
    }
}

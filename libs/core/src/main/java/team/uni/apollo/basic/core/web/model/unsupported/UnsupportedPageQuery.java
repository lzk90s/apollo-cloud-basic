package team.uni.apollo.basic.core.web.model.unsupported;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import team.uni.apollo.basic.core.base.BaseDO;
import team.uni.apollo.basic.core.web.model.BasePageQuery;

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

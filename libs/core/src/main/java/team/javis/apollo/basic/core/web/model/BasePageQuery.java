package team.javis.apollo.basic.core.web.model;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import team.javis.apollo.basic.core.base.BaseDO;

/**
 * 分页查询基类
 *
 * @author : qihang.liu
 * @date 2021-11-23
 */
@Data
public abstract class BasePageQuery<DO extends BaseDO> {
    private Integer pageNo;
    private Integer pageSize;
    private String orderBy;

    public Integer getPageNo() {
        if (null == pageNo) {
            pageNo = 1;
        }
        return pageNo;
    }

    public Integer getPageSize() {
        if (null == pageSize) {
            pageSize = 30;
        }
        return pageSize;
    }

    abstract public QueryWrapper<DO> buildQueryWrapper();
}

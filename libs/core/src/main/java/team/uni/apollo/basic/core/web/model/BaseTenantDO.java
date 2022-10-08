package team.uni.apollo.basic.core.web.model;

import lombok.Data;
import team.uni.apollo.basic.core.base.BaseDO;

/**
 * 多租户domain object基类
 *
 * @author : qihang.liu
 * @date 2021-11-23
 */
@Data
public class BaseTenantDO extends BaseDO {
    /**
     * 租户id
     */
    private String tenantId;
}

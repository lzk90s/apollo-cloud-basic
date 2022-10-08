package team.uni.apollo.basic.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : qihang.liu
 * @date 2021-11-29
 */
@Data
@ConfigurationProperties(prefix = "tenant")
public class TenantProperties {
    /**
     * 是否开启租户模式
     */
    private Boolean enable = true;

    /**
     * 多租户字段名称
     */
    private String column = "tenant_id";

    /**
     * 需要排除的多租户的表
     */
    private List<String> exclusionTable = new ArrayList<>();
}

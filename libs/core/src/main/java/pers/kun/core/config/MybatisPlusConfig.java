package pers.kun.core.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.StringValue;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.CollectionUtils;
import pers.kun.core.auth.UserProvider;

@EnableTransactionManagement
@MapperScan("pers.kun.**.dao.mapper")
@EnableConfigurationProperties(TenantProperties.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MybatisPlusConfig {
    private final TenantProperties tenantProperties;

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        //多租户插件
        if (tenantProperties.getEnable()) {
            interceptor.addInnerInterceptor(tenantLineInnerInterceptor());
        }

        //分页插件
        interceptor.addInnerInterceptor(paginationInterceptor());

        //防止全表更新与删除插件: BlockAttackInnerInterceptor
        BlockAttackInnerInterceptor blockAttackInnerInterceptor = new BlockAttackInnerInterceptor();
        interceptor.addInnerInterceptor(blockAttackInnerInterceptor);
        return interceptor;
    }

    @Bean
    public PaginationInnerInterceptor paginationInterceptor() {
        var pageInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        pageInterceptor.setMaxLimit(2000L);
        return pageInterceptor;
    }

    @Bean
    public TenantLineInnerInterceptor tenantLineInnerInterceptor() {
        return new TenantLineInnerInterceptor(new TenantLineHandler() {
            /**
             * 获取租户ID
             * @return Expression
             */
            @Override
            public Expression getTenantId() {
                String tenant = UserProvider.getTenantId();
                if (tenant != null) {
                    return new StringValue(tenant);
                }
                return new NullValue();
            }

            /**
             * 获取多租户的字段名
             * @return String
             */
            @Override
            public String getTenantIdColumn() {
                return tenantProperties.getColumn();
            }

            /**
             * 过滤不需要根据租户隔离的表
             * 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
             * @param tableName 表名
             */
            @Override
            public boolean ignoreTable(String tableName) {
                if (CollectionUtils.isEmpty(tenantProperties.getExclusionTable())) {
                    return false;
                }
                return tenantProperties.getExclusionTable().stream().anyMatch(
                        (t) -> t.equalsIgnoreCase(tableName)
                );
            }
        });
    }
}
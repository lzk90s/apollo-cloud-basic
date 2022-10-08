package team.javis.apollo.basic.core.web;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import team.javis.apollo.basic.core.auth.UserConst;
import team.javis.apollo.basic.core.base.BaseDO;

import java.util.List;

/**
 * @author : qihang.liu
 * @date 2021-12-06
 */
public interface TenantQueryMapper<ENTITY extends BaseDO> {
    String getTableName();

    @InterceptorIgnore(tenantLine = "true")
    @Select("SELECT DISTINCT " + UserConst.TENANT_ID + " FROM ${table} WHERE deleted=false")
    List<String> getAllTenant(@Param("table") String table);

    default List<String> getAllTenant() {
        return this.getAllTenant(getTableName());
    }
}

package team.javis.apollo.basic.system.dao.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import team.javis.apollo.basic.system.dao.entity.UserDO;

@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

    /**
     * 无租户获取用户
     *
     * @param name
     * @return
     */
    @InterceptorIgnore(tenantLine = "true")
    @Select("select * from t_user where `name`=#{name} or `phone`=#{name} and state=true")
    UserDO getUserNoTenant(String name);
}

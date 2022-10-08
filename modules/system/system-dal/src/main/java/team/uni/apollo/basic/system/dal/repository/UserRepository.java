package team.uni.apollo.basic.system.dal.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import team.uni.apollo.basic.system.dal.entity.UserDO;
import team.uni.apollo.basic.system.dal.mapper.UserMapper;


@Repository
@Transactional
public class UserRepository extends ServiceImpl<UserMapper, UserDO> {

    public UserDO getUserNoTenant(String name) {
        return getBaseMapper().getUserNoTenant(name);
    }
}

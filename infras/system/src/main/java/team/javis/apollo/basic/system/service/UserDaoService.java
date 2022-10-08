package team.javis.apollo.basic.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.javis.apollo.basic.system.dao.entity.UserDO;
import team.javis.apollo.basic.system.dao.mapper.UserMapper;

@Service
@Transactional
public class UserDaoService extends ServiceImpl<UserMapper, UserDO> {

    public UserDO getUserNoTenant(String name) {
        return getBaseMapper().getUserNoTenant(name);
    }
}

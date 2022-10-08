package pers.kun.uaa.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.kun.uaa.dao.entity.UserDO;
import pers.kun.uaa.dao.mapper.UserMapper;

@Service
@Transactional
public class UserDaoService extends ServiceImpl<UserMapper, UserDO> {

    public UserDO getUserNoTenant(String name) {
        return getBaseMapper().getUserNoTenant(name);
    }
}

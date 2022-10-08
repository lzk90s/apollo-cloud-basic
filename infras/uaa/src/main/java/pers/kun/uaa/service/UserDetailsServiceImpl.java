package pers.kun.uaa.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pers.kun.uaa.dao.entity.UserDO;
import pers.kun.uaa.model.SecurityUser;
import pers.kun.core.exception.BizException;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author liuzhikun
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDaoService userDaoService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO userDO = userDaoService.getUserNoTenant(username);
        Optional.ofNullable(userDO).orElseThrow(() -> new BizException("用户不存在"));
        return new SecurityUser(userDO, Arrays.asList("admin"));
    }
}

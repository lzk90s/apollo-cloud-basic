package team.javis.apollo.basic.system.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import team.javis.apollo.basic.core.exception.BizException;
import team.javis.apollo.basic.system.dao.entity.UserDO;
import team.javis.apollo.basic.system.model.SecurityUser;

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

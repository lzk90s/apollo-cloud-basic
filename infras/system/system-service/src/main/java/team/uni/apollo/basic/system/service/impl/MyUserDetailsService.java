package team.uni.apollo.basic.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import team.uni.apollo.basic.core.exception.BizException;
import team.uni.apollo.basic.system.dal.entity.UserDO;
import team.uni.apollo.basic.system.dal.repository.UserRepository;
import team.uni.apollo.basic.system.service.bo.SecurityUserBO;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author liuzhikun
 */
@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO userDO = userRepository.getUserNoTenant(username);
        Optional.ofNullable(userDO).orElseThrow(() -> new BizException("用户不存在"));
        return new SecurityUserBO(userDO, Arrays.asList("admin"));
    }
}

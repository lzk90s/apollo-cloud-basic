package team.uni.apollo.basic.system.service.bo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import team.uni.apollo.basic.system.dal.entity.UserDO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author : qihang.liu
 * @date 2022-01-18
 */
@Data
public class SecurityUserBO implements UserDetails {

    /**
     * ID
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 租户id
     */
    private String tenantId;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户状态
     */
    private Boolean enabled;
    /**
     * 权限数据
     */
    private Collection<SimpleGrantedAuthority> authorities;

    public SecurityUserBO() {

    }

    public SecurityUserBO(UserDO userDO, List<String> roles) {
        this.setId(userDO.getId());
        this.setUsername(userDO.getName());
        this.setTenantId(userDO.getTenantId());
        this.setPassword(userDO.getPassword());
        this.setEnabled(userDO.getState());
        if (roles != null) {
            authorities = new ArrayList<>();
            roles.forEach(item -> authorities.add(new SimpleGrantedAuthority(item)));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}

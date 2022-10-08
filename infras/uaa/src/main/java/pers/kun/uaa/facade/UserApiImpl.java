package pers.kun.uaa.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.kun.core.convert.BeanConverter;
import pers.kun.uaa.dao.entity.UserDO;
import pers.kun.uaa.service.UserDaoService;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/internal/user")
public class UserApiImpl {
    @Autowired
    private UserDaoService userDaoService;

    @GetMapping("/info/tenant")
    public UserInfoDTO getTenantUserInfo(@NotBlank @RequestParam String tenantId) {
        return Optional.ofNullable(userDaoService.getOne(new QueryWrapper<UserDO>()
                        .eq("tenant_id", tenantId)))
                .map(s -> convertUserInfoDTO().s2t(s)).orElseThrow(IllegalArgumentException::new);
    }

    @GetMapping("/info/username")
    public UserInfoDTO getByUserName(@NotBlank @RequestParam("userName") String userName){
        var user = userDaoService.getOne(new QueryWrapper<UserDO>()
                .eq("name", userName));
        return Optional.ofNullable(user).map(s->convertUserInfoDTO().s2t(s)).orElseThrow(IllegalArgumentException::new);
    }

    public BeanConverter<UserDO, UserInfoDTO> convertUserInfoDTO() {
        return BeanConverter.of(UserDO.class, UserInfoDTO.class);
    }

    @Data
    public static class UserInfoDTO {
        private Long id;
        private String name;
        private String password;
        private String phone;
        private Boolean state;
        private String email;
        private String app;
        private Date addTime;
    }
}

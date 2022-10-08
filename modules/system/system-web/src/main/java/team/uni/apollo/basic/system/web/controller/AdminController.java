package team.uni.apollo.basic.system.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.uni.apollo.basic.core.auth.UserProvider;
import team.uni.apollo.basic.core.rest.R;
import team.uni.apollo.basic.core.web.model.PageData;
import team.uni.apollo.basic.system.dal.entity.UserDO;
import team.uni.apollo.basic.system.dal.repository.UserRepository;
import team.uni.apollo.basic.system.web.converter.UserConverter;
import team.uni.apollo.basic.system.web.vo.UserRes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

/**
 * @author liuzhikun
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final String DEFAULT_PASSWORD = "e10adc3949ba59abbe56e057f20f883e";
    private static final String ADMIN = "admin";

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    R<PageData<UserRes>> listUsers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "0") Integer start,
            @RequestParam(required = false, defaultValue = "100") Integer limit) {
        var entityWrapper = new QueryWrapper<UserDO>();
        // admin用户可以查询列表，其他用户只能查询自己
        if (!ADMIN.equals(UserProvider.getUserName())) {
            entityWrapper.eq("name", UserProvider.getUserName());
        }
        Page<UserDO> result = userRepository.page(new Page<>(start, limit), entityWrapper);
        return R.ok(new PageData<>(result.getCurrent(), result.getTotal(), UserConverter.INSTANCE.toRes(result.getRecords())));
    }

    @PostMapping("/users")
    R<Void> addUser(@RequestBody UserDO userDO) {
        userDO.setState(true);
        userDO.setTenantId(userDO.getName());
        userRepository.save(userDO);
        return R.ok();
    }

    @PutMapping("/users/{id}")
    R<Void> updateUser(@PathVariable("id") Long id,
                       @RequestParam @NotBlank String phone,
                       @RequestParam @NotBlank String email) {
        userRepository.updateById(new UserDO().setId(id).setPhone(phone).setEmail(email));
        return R.ok();
    }

    @PostMapping("/users/{id}/reset_password")
    R<Void> resetPassword(@PathVariable("id") Long id, HttpServletResponse response) {
        UserDO user = userRepository.getById(id);
        if (user == null) {
            response.setStatus(404);
            return R.error(404, "用户不存在");
        }
        UserDO userDO = new UserDO().setId(id).setPassword(DEFAULT_PASSWORD);
        userRepository.updateById(userDO);
        return R.ok();
    }
}

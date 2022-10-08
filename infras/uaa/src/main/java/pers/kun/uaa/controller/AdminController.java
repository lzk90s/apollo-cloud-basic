package pers.kun.uaa.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.kun.uaa.dao.entity.UserDO;
import pers.kun.uaa.service.UserDaoService;
import pers.kun.core.auth.UserProvider;
import pers.kun.core.rest.R;
import pers.kun.core.web.model.PageData;

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
    private UserDaoService userDaoService;

    @GetMapping("/users")
    R<PageData<UserDO>> listUsers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "0") Integer start,
            @RequestParam(required = false, defaultValue = "100") Integer limit) {
        var entityWrapper = new QueryWrapper<UserDO>();
        // admin用户可以查询列表，其他用户只能查询自己
        if (!ADMIN.equals(UserProvider.getUserName())) {
            entityWrapper.eq("name", UserProvider.getUserName());
        }
        Page<UserDO> result = userDaoService.page(new Page<>(start, limit), entityWrapper);
        return R.ok(new PageData<>(result.getCurrent(), result.getTotal(), result.getRecords()));
    }

    @PostMapping("/users")
    R<Void> addUser(@RequestBody UserDO userDO) {
        userDO.setState(true);
        userDO.setTenantId(userDO.getName());
        userDaoService.save(userDO);
        return R.ok();
    }

    @PutMapping("/users/{id}")
    R<Void> updateUser(@PathVariable("id") Long id,
                       @RequestParam @NotBlank String phone,
                       @RequestParam @NotBlank String email) {
        userDaoService.updateById(new UserDO().setId(id).setPhone(phone).setEmail(email));
        return R.ok();
    }

    @PostMapping("/users/{id}/reset_password")
    R<Void> resetPassword(@PathVariable("id") Long id, HttpServletResponse response) {
        UserDO user = userDaoService.getById(id);
        if (user == null) {
            response.setStatus(404);
            return R.error(404, "用户不存在");
        }
        UserDO userDO = new UserDO().setId(id).setPassword(DEFAULT_PASSWORD);
        userDaoService.updateById(userDO);
        return R.ok();
    }
}

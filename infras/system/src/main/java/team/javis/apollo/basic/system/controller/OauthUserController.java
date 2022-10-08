package team.javis.apollo.basic.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


/**
 * @author wangdl
 */
@RestController
public class OauthUserController {
    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}

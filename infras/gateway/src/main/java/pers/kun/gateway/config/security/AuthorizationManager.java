//package pers.kun.gateway.config.security;
//
//import cn.hutool.core.convert.Convert;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.authorization.AuthorizationDecision;
//import org.springframework.security.authorization.ReactiveAuthorizationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.server.authorization.AuthorizationContext;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//import java.util.stream.Collectors;
//
///**
// * @author : qihang.liu
// * @date 2022-01-18
// */
//@Slf4j
//@Component
//public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//    @Override
//    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
//        //黑名单
//        URI uri = authorizationContext.getExchange().getRequest().getURI();
//        Object obj = redisTemplate.opsForHash().get(RedisConstant.RESOURCE_ROLES_BLACK_LIST_MAP, uri.getPath());
//        var blackAuthorities = Convert.toList(String.class, obj).stream()
//                .map(i -> i = AuthConstant.AUTHORITY_PREFIX + i)
//                .collect(Collectors.toList());
//        //认证通过且角色匹配且不在黑名单url中
//        return mono
//                .filter(Authentication::isAuthenticated)
//                .flatMapIterable(Authentication::getAuthorities)
//                .map(GrantedAuthority::getAuthority)
//                .all(s -> !blackAuthorities.contains(s))
//                .map(AuthorizationDecision::new)
//                .defaultIfEmpty(new AuthorizationDecision(false));
//    }
//}
package pers.kun.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import pers.kun.core.auth.UserConst;
import pers.kun.core.auth.UserProvider;

/**
 * @author : qihang.liu
 * @date 2021-12-05
 */
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(UserConst.TENANT_ID, UserProvider.getTenantId());
    }
}

package team.javis.apollo.basic.core.auth;

import team.javis.apollo.basic.core.util.ServletUtil;

import java.util.Optional;

public class UserProvider {
    private static final ThreadLocal<String> LOCAL_TENANT = new ThreadLocal<>();

    public static String getUserName() {
        var isDebug = ServletUtil.getRequest().getParameter("debug");
        if ("true".equalsIgnoreCase(isDebug)) {
            return ServletUtil.getRequest().getParameter(UserConst.USER_NAME);
        }
        return Optional.ofNullable(ServletUtil.getRequest().getHeader(UserConst.USER_NAME)).orElseThrow(IllegalArgumentException::new);
    }

    public static void setLocalTenant(String tenantId) {
        LOCAL_TENANT.set(tenantId);
    }

    public static void removeLocalTenant() {
        LOCAL_TENANT.remove();
    }

    public static String getTenantId() {
        // 先从local tenant里面获取
        if (null != LOCAL_TENANT.get()) {
            return LOCAL_TENANT.get();
        }

        try {
            var request = ServletUtil.getRequest();
            String tenantId = request.getHeader(UserConst.TENANT_ID);
            if (null == tenantId || tenantId.isEmpty()) {
                tenantId = getUserName();
            }
            return tenantId;
        } catch (Exception e) {
            return "";
        }
    }
}

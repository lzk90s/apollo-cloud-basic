package team.javis.apollo.basic.gateway.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {
    public static final String TENANT_ID = "tenant_id";
    public static final String USER_NAME = "name";

    public static String getUserName() {
        String username = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.User) {
            username = ((org.springframework.security.core.userdetails.User) principal).getUsername();
        } else if (principal instanceof String) {
            username = (String) principal;
        }
        return username;
    }

    public static String getTenantId() {
        return getUserName();
    }
}

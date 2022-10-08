package team.uni.apollo.basic.feign.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import team.uni.apollo.basic.core.auth.UserProvider;
import team.uni.apollo.basic.core.exception.BizException;
import team.uni.apollo.basic.core.exception.BizResultCode;
import team.uni.apollo.basic.core.util.JsonUtil;

@Aspect
@Order(1)
@Slf4j
public class IntegrationAspect {

    @Around(value = "execution(public * pers.kun.*.integration.*.*(..))")
    public Object handleException(ProceedingJoinPoint pjp) {
        long begin = System.currentTimeMillis();
        String className = pjp.getSignature().getDeclaringTypeName();
        String method = pjp.getSignature().getName();
        boolean success = false;

        try {
            Object result = pjp.proceed();
            if (getResLogSwitch()) {
                log.info("[IntegrationDetail]result^{}^{}^{}^{}", className, method, JsonUtil.obj2json(pjp.getArgs()), result);
            }
            success = true;
            return result;
        } catch (BizException e) {
            log.error(buildErrorMsg(className, method, e + e.getMessage()), e);
            throw e;
        } catch (Throwable e) {
            log.error(buildErrorMsg(className, method, e.getMessage()), e);
            throw new BizException(BizResultCode.RPC_FAILED.getCode(), e.getMessage());
        } finally {

            long end = System.currentTimeMillis();
            long runTime = end - begin;
            log.info("[IntegrationDigest]({})^{}^{}^{}^{}^{}", UserProvider.getTenantId(), success ? "Y" : "N", className, method,
                    JsonUtil.obj2json(pjp.getArgs()), runTime);
        }
    }

    private String buildErrorMsg(String className, String method, String errorMsg) {
        return "[IntegrationException]" + "^" + className + "^" + method + "^" + errorMsg;
    }

    private boolean getResLogSwitch() {
        return true;
    }
}

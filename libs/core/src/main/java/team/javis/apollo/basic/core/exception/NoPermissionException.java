package team.javis.apollo.basic.core.exception;

/**
 * @author : qihang.liu
 * @date 2021-09-08
 */
public class NoPermissionException extends BizException {
    public NoPermissionException(String msg) {
        super(401, "没有权限: " + msg);
    }

    public NoPermissionException() {
        super(401, "没有权限");
    }
}

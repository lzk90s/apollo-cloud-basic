package team.javis.apollo.basic.core.exception;

/**
 * @author : qihang.liu
 * @date 2022-01-19
 */
public enum BizResultCode implements ErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    RPC_FAILED(501, "远程调用失败");


    private int code;
    private String message;

    private BizResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

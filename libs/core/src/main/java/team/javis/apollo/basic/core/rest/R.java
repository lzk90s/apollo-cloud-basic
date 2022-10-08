package team.javis.apollo.basic.core.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Restful接口统一响应消息格式
 *
 * @param <T>
 * @author liuzk
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Integer OK = 0;
    private static final String DEFAULT_MESSAGE = "";

    /**
     * 时间戳
     */
    private Long timestamp;
    /**
     * 状态码
     *
     * @mock 0
     */
    private Integer code = OK;
    /**
     * 消息
     *
     * @mock
     */
    private String message = DEFAULT_MESSAGE;
    /**
     * 数据
     */
    private T data = null;
    /**
     * 扩展字段
     */
    private Map<String, Object> extend = null;


    /**
     * 设置为private，禁止外部直接new
     */
    private R() {
    }

    /*-------------------------------------------------*/
    public static <T> R<T> ok() {
        return new R<T>().
                code(OK).
                body(null).
                putTimeStamp();
    }

    public static <T> R<T> ok(T data) {
        return new R<T>().
                code(OK).
                body(data).
                putTimeStamp();
    }

    public static <T> R<T> error(String message) {
        return error(500, message);
    }
    /*-------------------------------------------------*/

    public static <T> R<T> error(int status, String message) {
        return new R<T>().
                code(status).
                message(message).
                putTimeStamp();
    }

    @JsonIgnore
    public boolean isOk() {
        return this.code.equals(OK);
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public R<T> code(int code) {
        this.code = code;
        return this;
    }

    public R<T> message(String message) {
        this.message = message;
        return this;
    }

    public R<T> body(T data) {
        setData(data);
        return this;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }

    public Object getExtend(String key) {
        if (extend == null) {
            return null;
        }

        return extend.get(key);
    }

    public R<T> addExtend(Map<String, Object> extend) {
        if (extend == null) {
            return this;
        }

        if (this.extend == null) {
            this.extend = new HashMap<>(extend.size());
        }

        this.extend.putAll(extend);
        return this;
    }

    public R<T> addExtend(String key, Object object) {
        if (extend == null) {
            extend = new HashMap<>(16);
        }
        extend.put(key, object);
        return this;
    }

    private R<T> putTimeStamp() {
        this.timestamp = System.currentTimeMillis();
        return this;
    }
}

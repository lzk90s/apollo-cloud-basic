package team.uni.apollo.basic.gateway.service.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Msg<T> implements Serializable {
    private static final long serialVersionUID = -1177183613782210351L;
    private Long timestamp;
    private Integer status;
    private String message;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static Msg<Void> error(Integer status, String message) {
        var msg = new Msg<Void>();
        msg.setStatus(status);
        msg.setMessage(message);
        return msg;
    }
}

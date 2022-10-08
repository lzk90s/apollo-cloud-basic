package pers.kun.core.base;

import lombok.Data;

import java.util.Date;

/**
 * value object基类
 *
 * @author : qihang.liu
 * @date 2021-11-23
 */
@Data
public class BaseVO {
    private Long id;
    private Date addTime;
    private Date updateTime;

    public BaseVO convertFromDO(BaseDO obj) {
        return null;
    }
}
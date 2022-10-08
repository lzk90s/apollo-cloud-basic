package pers.kun.core.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * 领域对象基类
 *
 * @author : qihang.liu
 * @date 2021-12-05
 */
@Data
public class BaseDO {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 数据是否删除（软删除）
     */
    private Boolean deleted;
    /**
     * 添加时间
     */
    private Date addTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}

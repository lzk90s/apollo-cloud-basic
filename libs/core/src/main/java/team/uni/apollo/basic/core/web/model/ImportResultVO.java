package team.uni.apollo.basic.core.web.model;

import lombok.Data;
import team.uni.apollo.basic.core.base.BaseVO;

/**
 * @author : qihang.liu
 * @date 2021-11-24
 */
@Data
public class ImportResultVO extends BaseVO {
    private Integer index;
    private Boolean succeed;
    private String message;
}

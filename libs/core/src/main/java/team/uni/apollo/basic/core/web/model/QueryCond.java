package team.uni.apollo.basic.core.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import team.uni.apollo.basic.core.util.JsonUtil;

/**
 * @author : qihang.liu
 * @date 2021-09-23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryCond {
    private String key;
    private String op;
    private String value;

    public QueryCond(String key, QueryCond.OpEnum opEnum, String value) {
        this(key, opEnum.getOp(), value);
    }

    public static QueryCond parse(String cond) {
        if (StringUtils.isEmpty(cond)) {
            return null;
        }
        for (var v : OpEnum.values()) {
            int idx = cond.indexOf(v.op);
            if (idx > 0) {
                String key = cond.substring(0, idx - 1);
                String op = v.op;
                String value = cond.substring(idx + v.op.length());
                return new QueryCond(key.strip(), op.strip(), value.strip());
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String str = "price %like% 10";
        System.out.println(JsonUtil.obj2json(parse(str)));
    }

    /**
     * @author : qihang.liu
     * @date 2021-09-23
     */
    public enum OpEnum {
        /**
         * like
         */
        LIKE("%like%"),
        /**
         * 等于
         */
        EQ("=="),
        /**
         * 不等于
         */
        NE("!="),
        /**
         * 小于等于
         */
        LE("<="),
        /**
         * 大于等于
         */
        GE(">="),
        /**
         * 小于
         */
        LT("<"),
        /**
         * 大于
         */
        GT(">"),
        /**
         * 为空时赋值
         */
        NULL_ASSIGN("?="),
        /**
         * 赋值
         */
        ASSIGN("=");

        private String op;

        OpEnum(String op) {
            this.op = op;
        }

        public static OpEnum get(String op) {
            for (var v : OpEnum.values()) {
                if (v.op.equals(op)) {
                    return v;
                }
            }
            return null;
        }

        public String getOp() {
            return this.op;
        }
    }
}

package team.uni.apollo.basic.core.validate;

/**
 * @author : qihang.liu
 * @date 2021-11-28
 */
public @interface EnumValidator {
    Class validateClass();

    String validateMethod();
}

package team.uni.apollo.basic.core.convert;

import java.lang.reflect.InvocationTargetException;

/**
 * @author : qihang.liu
 * @date 2021-11-23
 */
public class ObjectFactory {
    public static <T> T newInstance(Class<T> tClass) {
        try {
            return tClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}

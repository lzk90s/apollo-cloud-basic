package pers.kun.core.convert;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * bean转换器（双向）
 *
 * @param <SOURCE>
 * @param <TARGET>
 * @author kun
 */
public class BeanMutualConverter<SOURCE, TARGET> {
    private final Class<SOURCE> sourceClass;
    private final Class<TARGET> targetClass;

    protected BeanMutualConverter(Class<SOURCE> sourceClass, Class<TARGET> targetClass) {
        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
    }

    public static <SOURCE, TARGET> BeanMutualConverter<SOURCE, TARGET> of(Class<SOURCE> sourceClass, Class<TARGET> targetClass) {
        return new BeanMutualConverter<>(sourceClass, targetClass);
    }

    public final TARGET s2t(SOURCE source) {
        return doForward(source);
    }

    public final SOURCE t2s(TARGET target) {
        return doBackward(target);
    }

    public final List<TARGET> s2t(List<SOURCE> source) {
        if (null == source) {
            return Collections.emptyList();
        }
        List<TARGET> targets = new ArrayList<>();
        for (var s : source) {
            targets.add(doForward(s));
        }
        return targets;
    }

    public final List<SOURCE> t2s(List<TARGET> source) {
        if (null == source) {
            return Collections.emptyList();
        }
        List<SOURCE> sources = new ArrayList<>();
        for (var s : source) {
            sources.add(doBackward(s));
        }
        return sources;
    }

    protected TARGET doForward(SOURCE source) {
        try {
            TARGET a2 = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, a2);
            return a2;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected SOURCE doBackward(TARGET target) {
        try {
            SOURCE a1 = sourceClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(target, a1);
            return a1;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}

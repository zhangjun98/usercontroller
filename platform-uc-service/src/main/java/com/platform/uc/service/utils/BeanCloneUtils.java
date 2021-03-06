package com.platform.uc.service.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @program: ABParser
 * @description: 要拷贝字段的目标对象不可以是抽象类、接口，或者只有参构造方法，
 * 否则在targetClass.newInstance()时会报java.lang.InstantiationException
 * 【依赖】org.springframework.spring-beans
 * @author: yuhui.xie
 * @create: 2019-09-16 10:15
 **/
@Slf4j
public class BeanCloneUtils {

    public static <T> T convert(Object source, Class<T> targetClass) {
        return convert(source, targetClass, (String[]) null);
    }

    public static <T> T convert(Object source, T target) {
        return convert(source, target, (String[]) null);
    }

    public static <T> T convertIgnoreNullProperty(Object source, Class<T> targetClass) {
        return convert(source, targetClass, getNullPropertyNames(source));
    }

    public static <T> T convertIgnoreNullProperty(Object source, T target) {
        return convert(source, target, getNullPropertyNames(source));
    }

    public static <T> List<T> convert(List<?> sourceList, Class<T> targetClass) {
        return convert(sourceList, targetClass, false);
    }

    public static <T> List<T> convertIgnoreNullProperty(List<?> sourceList, Class<T> targetClass) {
        return convert(sourceList, targetClass, true);
    }

    private static <T> T convert(Object source, T target, String... ignoreProperties) {
        if (source == null || target == null) {
            return null;
        }
        try {
            BeanUtils.copyProperties(source, target, ignoreProperties);
            return target;
        } catch (Exception e) {
            log.error("ABParser convert happen exception", e);
            return null;
        }
    }

    private static <T> T convert(Object source, Class<T> targetClass, String... ignoreProperties) {
        if (source == null) {
            return null;
        }
        try {
            T target = targetClass.newInstance();
            BeanUtils.copyProperties(source, target, ignoreProperties);
            return target;
        } catch (Exception e) {
            log.error("ABParser convert happen exception", e);
            return null;
        }
    }

    private static <T> List<T> convert(List<?> sourceList, Class<T> targetClass, boolean isIgnoreProperties) {
        if (sourceList == null || sourceList.size() == 0) {
            return new ArrayList<T>(0);
        }
        List<T> list = new ArrayList<T>(sourceList.size());
        for (Object obj : sourceList) {
            String[] ignoreProperties = (String[]) null;
            if (isIgnoreProperties) {
                ignoreProperties = getNullPropertyNames(obj);
            }
            list.add(convert(obj, targetClass, ignoreProperties));
        }
        return list;
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
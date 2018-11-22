package com.zb.qjs.order.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class BeanUtils {
    private static final Logger LOG = LoggerFactory.getLogger(BeanUtils.class);
    private static final ConcurrentHashMap<String, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap();

    private BeanUtils() {
    }

    private static BeanCopier getBeanCopier(Class<?> sourceClass, Class<?> destClass) {
        String key = sourceClass.getCanonicalName() + ":" + destClass.getCanonicalName();
        BeanCopier beanCopier = (BeanCopier) BEAN_COPIER_CACHE.get(key);
        if (beanCopier == null) {
            beanCopier = BeanCopier.create(sourceClass, destClass, false);
            BEAN_COPIER_CACHE.putIfAbsent(key, beanCopier);
        }

        return beanCopier;
    }

    public static <T> T copyAs(Object source, Class<T> destType) throws Exception {
        if (source != null && destType != null) {
            try {
                BeanCopier beanCopier = getBeanCopier(source.getClass(), destType);
                T dest = destType.newInstance();
                beanCopier.copy(source, dest, (Converter) null);
                return dest;
            } catch (Exception var4) {
                throw var4;
            }
        } else {
            return null;
        }
    }

    public static <T, K> List<K> copyAs(Collection<T> source, Class<K> destType) throws Exception {
        if (!CollectionUtils.isNullOrEmpty(source) && destType != null) {
            List<K> result = new ArrayList();
            if (source.isEmpty()) {
                return result;
            } else {
                try {
                    Iterator i$ = source.iterator();

                    while (i$.hasNext()) {
                        Object object = i$.next();
                        BeanCopier beanCopier = getBeanCopier(object.getClass(), destType);
                        K dest = destType.newInstance();
                        beanCopier.copy(object, dest, (Converter) null);
                        result.add(dest);
                    }

                    return result;
                } catch (Exception var7) {
                    throw var7;
                }
            }
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    public static void copy(Object source, Object target) {
        if (source != null && target != null) {
            BeanCopier beanCopier = getBeanCopier(source.getClass(), target.getClass());
            beanCopier.copy(source, target, (Converter) null);
        }
    }

    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    public static void setProperty(Object bean, String name, Object value) throws Exception {
        try {
            org.apache.commons.beanutils.BeanUtils.setProperty(bean, name, value);
        } catch (Exception var4) {
            throw var4;
        }
    }

    public static Object getProperty(Object bean, String name) throws Exception {
        try {
            return org.apache.commons.beanutils.BeanUtils.getProperty(bean, name);
        } catch (Exception var3) {
            throw var3;
        }
    }

    public static void setFieldValue(Object bean, String fieldName, Object value) throws Exception {
        try {
            Field field = findField(bean.getClass(), fieldName);
            if (field != null) {
                field.setAccessible(true);
                field.set(bean, value);
            }
        } catch (Exception var4) {
            throw var4;
        }
    }

    public static Field findField(Class<?> clz, String fieldName) {
        Field f = null;

        try {
            f = clz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException var4) {
            if (clz.getSuperclass() != null) {
                f = findField(clz.getSuperclass(), fieldName);
            }

            if (LOG.isTraceEnabled()) {
                LOG.trace(var4.getMessage(), var4);
            }
        }

        return f;
    }

    public static void copyNotNullProperties(Object srcModel, Object destModel) throws Exception {
        if (srcModel != null && destModel != null) {
            try {
                PropertyDescriptor[] srcDescriptors = Introspector.getBeanInfo(srcModel.getClass()).getPropertyDescriptors();
                PropertyDescriptor[] destDescriptors = Introspector.getBeanInfo(destModel.getClass()).getPropertyDescriptors();
                Map<String, PropertyDescriptor> destPropertyNameDescriptorMap = new HashMap();
                PropertyDescriptor[] arr$ = destDescriptors;
                int len$ = destDescriptors.length;

                int i$;
                PropertyDescriptor srcDescriptor;
                for (i$ = 0; i$ < len$; ++i$) {
                    srcDescriptor = arr$[i$];
                    destPropertyNameDescriptorMap.put(srcDescriptor.getName(), srcDescriptor);
                }

                arr$ = srcDescriptors;
                len$ = srcDescriptors.length;

                for (i$ = 0; i$ < len$; ++i$) {
                    srcDescriptor = arr$[i$];
                    PropertyDescriptor destDescriptor = (PropertyDescriptor) destPropertyNameDescriptorMap.get(srcDescriptor.getName());
                    if (destDescriptor != null && destDescriptor.getPropertyType() == srcDescriptor.getPropertyType() && destDescriptor.getPropertyType() != Class.class && srcDescriptor.getReadMethod() != null) {
                        Object val = srcDescriptor.getReadMethod().invoke(srcModel);
                        if (val != null && destDescriptor.getWriteMethod() != null) {
                            destDescriptor.getWriteMethod().invoke(destModel, val);
                        }
                    }
                }

            } catch (Exception var11) {
                throw var11;
            }
        }
    }

    public static void copyNonCollectionProperties(Object source, Object target) {
        List<String> ignoreProperties = new ArrayList();
        Field[] declaredFields = target.getClass().getDeclaredFields();
        Field[] arr$ = declaredFields;
        int len$ = declaredFields.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            Field field = arr$[i$];
            if (Collection.class.isAssignableFrom(field.getType()) || Map.class.isAssignableFrom(field.getType())) {
                ignoreProperties.add(field.getName());
            }
        }

        org.springframework.beans.BeanUtils.copyProperties(source, target, (String[]) ignoreProperties.toArray(new String[ignoreProperties.size()]));
    }
}

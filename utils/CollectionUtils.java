package com.zb.qjs.order.common.utils;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.AllPredicate;

import java.util.*;

public final class CollectionUtils {
    private CollectionUtils() {
    }

    public static <T> boolean isNullOrEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean isNotNullOrEmptyArray(T[] array) {
        return array != null && array.length != 0;
    }

    public static <T> List<T> mergeAll(List... lists) {
        List<T> mergedList = new ArrayList();
        int i = 0;

        for (int len = lists.length; i < len; ++i) {
            List<T> list = lists[i];
            if (list != null && !list.isEmpty()) {
                int j = 0;

                for (int lenj = list.size(); j < lenj; ++j) {
                    T obj = list.get(j);
                    if (obj != null) {
                        mergedList.add(obj);
                    }
                }
            }
        }

        return mergedList;
    }

    public static <T> List<T> asList(T... objs) {
        if (objs == null) {
            return Collections.EMPTY_LIST;
        } else {
            List<T> list = new ArrayList();
            Collections.addAll(list, objs);
            return list;
        }
    }

    public static <K, V> Map<K, V> makeMap(K key, V value) {
        Map<K, V> map = new HashMap();
        map.put(key, value);
        return map;
    }

    public static <K, V> Map<K, V> makeMapFromLists(List<K> list1, List<V> list2) {
        Map<K, V> map = new HashMap();
        if (list1.size() != list2.size()) {
            return null;
        } else {
            for (int i = 0; i < list1.size(); ++i) {
                map.put(list1.get(i), list2.get(i));
            }

            return map;
        }
    }

    public static <K, V> Map<K, V> makeMap(K key1, V value1, K key2, V value2) {
        Map<K, V> map = new HashMap();
        map.put(key1, value1);
        map.put(key2, value2);
        return map;
    }

    public static String join(Object[] words, String seperator) {
        StringBuilder sb = new StringBuilder();
        if (words != null) {
            for (int i = 0; i < words.length; ++i) {
                sb.append(words[i]);
                if (i < words.length - 1) {
                    sb.append(seperator);
                }
            }
        }

        return sb.toString();
    }

    public static String join(Collection<?> collection, String seperator) {
        Object[] objs = new Object[collection.size()];
        collection.toArray(objs);
        return join(objs, seperator);
    }

    public static String join(List<?> list, String seperator) {
        Object[] objs = new Object[list.size()];
        list.toArray(objs);
        return join(objs, seperator);
    }

    public static <T, PropType> Collection<PropType> collect(Collection<T> collection, String propertyName) {
        Transformer transformer = new BeanToPropertyValueTransformer(propertyName);
        return org.apache.commons.collections.CollectionUtils.collect(collection, transformer);
    }

    public static <T> T[] copy(T[] sourceArray) {
        return sourceArray == null ? null : Arrays.copyOf(sourceArray, sourceArray.length);
    }
}

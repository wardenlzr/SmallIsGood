package com.warden.lib.util;

import java.util.ArrayList;

/**
 * Created by yubin 2020/10/21 0021  11:23
 */
public class DataUtils {
    public static ArrayList<String> testData() {
        return testData(10);
    }

    public static ArrayList<String> testData(int count) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add("" + i);
        }
        return list;
    }

    /**
     * @param count 返回数据的个数
     * @param clazz 所需要的Bean对象
     * @param <T>
     * @return 返回泛型（你所传入Bean对象）的测试数据
     */
    public static <T> ArrayList<T> testData(int count, Class<T> clazz) {

        ArrayList<T> list = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            T obj = null;
            try {
                obj = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            list.add(obj);
        }
        return list;
    }
}

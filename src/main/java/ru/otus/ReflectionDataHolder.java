package ru.otus;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

class ReflectionDataHolder {
    private Map<Class, Field[]> classesMetaData;

    static ReflectionDataHolder getClassInstance() {
        ReflectionDataHolder reflectionDataHolder = new ReflectionDataHolder();
        reflectionDataHolder.classesMetaData = new HashMap<>();
        return reflectionDataHolder;
    }

    Field[] getClassFields(Class clazz) {
        if (classesMetaData.containsKey(clazz)) {
            return classesMetaData.get(clazz);
        } else {
            Field[] fields = clazz.getDeclaredFields();
            classesMetaData.put(clazz, fields);
            return fields;
        }
    }

}

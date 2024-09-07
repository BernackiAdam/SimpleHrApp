package com.bernacki.hrapp.dao;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;

import java.lang.reflect.Field;

public abstract class DaoBaseTestClass {

    public boolean checkIfManyToManyLazyInit(Class<?> entityClass, String fieldName) throws NoSuchFieldException {
        Field field = entityClass.getDeclaredField(fieldName);
        ManyToMany manyToMany = field.getAnnotation(ManyToMany.class);

        return manyToMany.fetch() == FetchType.LAZY;
    }
}

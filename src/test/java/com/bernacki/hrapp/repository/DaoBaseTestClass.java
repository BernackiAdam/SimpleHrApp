package com.bernacki.hrapp.repository;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.lang.reflect.Field;

public abstract class DaoBaseTestClass {

    public boolean checkIfManyToManyLazyInit(Class<?> entityClass, String fieldName) throws NoSuchFieldException {
        Field field = entityClass.getDeclaredField(fieldName);
        ManyToMany manyToMany = field.getAnnotation(ManyToMany.class);

        return manyToMany.fetch() == FetchType.LAZY;
    }

    public boolean checkIfOneToManyLazyInit(Class<?> entityClass, String fieldName) throws NoSuchFieldException{
        Field field = entityClass.getDeclaredField(fieldName);
        OneToMany oneToMany = field.getAnnotation(OneToMany.class);

        return oneToMany.fetch() == FetchType.LAZY;
    }
}

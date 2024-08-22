package com.strongduanmu;

import lombok.SneakyThrows;

import java.lang.reflect.Method;

public final class Reflection {
    
    @SneakyThrows
    public static void main(final String[] args) {
        if (3 != args.length) {
            throw new IllegalArgumentException("You must provide class name, method name and arguments.");
        }
        String className = args[0];
        String methodName = args[1];
        String arguments = args[2];
        Class<?> clazz = Class.forName(className);
        Method method = clazz.getDeclaredMethod(methodName, String.class);
        Object result = method.invoke(null, arguments);
        System.out.println(result);
    }
}

package top.niunaijun.blackbox.utils;


import android.annotation.SuppressLint;

import org.lsposed.hiddenapibypass.HiddenApiBypass;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

public class Reflector {
    public static final String LOG_TAG = "Reflector";

    protected Class<?> mType;
    protected Object mCaller;
    protected Constructor mConstructor;
    protected Field mField;
    protected Method mMethod;


    public static Reflector on(String name) throws Exception {
        return on(name, true, Reflector.class.getClassLoader());
    }

    public static Reflector on(String name, boolean initialize) throws Exception {
        return on(name, initialize, Reflector.class.getClassLoader());
    }

    public static Reflector on(String name, boolean initialize, ClassLoader loader) throws Exception {
        try {
            return on(Class.forName(name, initialize, loader));
        } catch (Throwable e) {
            throw new Exception("Oops!", e);
        }
    }

    public static Reflector on(Class<?> type) {
        Reflector reflector = new Reflector();
        reflector.mType = type;
        return reflector;
    }

    public static Reflector with(Object caller) throws Exception {
        return on(caller.getClass()).bind(caller);
    }

    protected Reflector() {

    }

    @SuppressLint("NewApi")
    public Reflector constructor(Class<?>... parameterTypes) throws Exception {
        try {
            mConstructor = HiddenApiBypass.getDeclaredConstructor(mType, parameterTypes);
            mConstructor.setAccessible(true);
            mField = null;
            mMethod = null;
            return this;
        } catch (Throwable e) {
            throw new Exception("Oops!", e);
        }
    }

    @SuppressWarnings("unchecked")
    public <R> R newInstance(Object... initargs) throws Exception {
        if (mConstructor == null) {
            throw new Exception("Constructor was null!");
        }
        try {
            return (R) mConstructor.newInstance(initargs);
        } catch (InvocationTargetException e) {
            throw new Exception("Oops!", e.getTargetException());
        } catch (Throwable e) {
            throw new Exception("Oops!", e);
        }
    }

    protected Object checked(Object caller) throws Exception {
        if (caller == null || mType.isInstance(caller)) {
            return caller;
        }
        throw new Exception("Caller [" + caller + "] is not a instance of type [" + mType + "]!");
    }

    protected void check(Object caller, Member member, String name) throws Exception {
        if (member == null) {
            throw new Exception(name + " was null!");
        }
        if (caller == null && !Modifier.isStatic(member.getModifiers())) {
            throw new Exception("Need a caller!");
        }
        checked(caller);
    }

    public Reflector bind(Object caller) throws Exception {
        mCaller = checked(caller);
        return this;
    }

    public Reflector unbind() {
        mCaller = null;
        return this;
    }

    public Reflector field(String name) throws Exception {
        try {
            mField = findField(name);
            mField.setAccessible(true);
            mConstructor = null;
            mMethod = null;
            return this;
        } catch (Throwable e) {
            throw new Exception("Oops!", e);
        }
    }

    protected Field findField(String name) throws NoSuchFieldException {
        try {
            return mType.getField(name);
        } catch (NoSuchFieldException e) {
            try {
                return findInstanceField(name);
            } catch (NoSuchFieldException ex) {
                return findStaticField(name);
            }
        }
    }

    @SuppressLint("NewApi")
    protected Field findInstanceField(String name) throws NoSuchFieldException {
        List<Field> allInstanceFields = HiddenApiBypass.getInstanceFields(mType);
        for (Field f : allInstanceFields) {
            if (f.getName().equals(name)) {
                return f;
            }
        }
        throw new NoSuchFieldException();
    }

    @SuppressWarnings("unchecked")
    protected Field findStaticField(String name) throws NoSuchFieldException {
        List<Field> allStaticFields = HiddenApiBypass.getStaticFields(mType);
        for (Field f : allStaticFields) {
            if (f.getName().equals(name)) {
                return f;
            }
        }
        throw new NoSuchFieldException();
    }

    @SuppressWarnings("unchecked")
    public <R> R get() throws Exception {
        return get(mCaller);
    }

    @SuppressWarnings("unchecked")
    public <R> R get(Object caller) throws Exception {
        check(caller, mField, "Field");
        try {
            return (R) mField.get(caller);
        } catch (Throwable e) {
            throw new Exception("Oops!", e);
        }
    }

    public Reflector set(Object value) throws Exception {
        return set(mCaller, value);
    }

    public Reflector set(Object caller, Object value) throws Exception {
        check(caller, mField, "Field");
        try {
            mField.set(caller, value);
            return this;
        } catch (Throwable e) {
            throw new Exception("Oops!", e);
        }
    }

    public Reflector method(String name, Class<?>... parameterTypes) throws Exception {
        try {
            mMethod = findMethod(name, parameterTypes);
            mMethod.setAccessible(true);
            mConstructor = null;
            mField = null;
            return this;
        } catch (NoSuchMethodException e) {
            throw new Exception("Oops!", e);
        }
    }

    @SuppressLint("NewApi")
    protected Method findMethod(String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        try {
            return mType.getMethod(name, parameterTypes);
        } catch (Exception e) {
            return HiddenApiBypass.getDeclaredMethod(mType, name, parameterTypes);
        }
    }

    public <R> R call(Object... args) throws Exception {
        return callByCaller(mCaller, args);
    }

    @SuppressWarnings("unchecked")
    public <R> R callByCaller(Object caller, Object... args) throws Exception {
        check(caller, mMethod, "Method");
        try {
            return (R) mMethod.invoke(caller, args);
        } catch (InvocationTargetException e) {
            throw new Exception("Oops!", e.getTargetException());
        } catch (Throwable e) {
            throw new Exception("Oops!", e);
        }
    }

    @SuppressLint("NewApi")
    public static <R> R invoke(Class<?> clazz, Object thiz, String methodName, Object... args) {
        return (R) HiddenApiBypass.invoke(clazz, thiz, methodName, args);
    }

    public static Method findMethodByFirstName(Class<?> clazz, String methodName) {
        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            if (methodName.equals(declaredMethod.getName())) {
                return declaredMethod;
            }
        }
        return null;
    }
}

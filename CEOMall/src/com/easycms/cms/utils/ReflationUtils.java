package com.easycms.cms.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReflationUtils {

	private static boolean isDecalred = false;

	/**
	 * 通过类名获取类
	 * 
	 * @param 类名
	 * @return 类
	 */
	public static Class<?> findClass(String clazz) {
		if (!Assert.notEmpty(clazz))
			return null;
		try {
			return Class.forName(clazz);
		} catch (ClassNotFoundException e) {
			Log4jUtils.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 设计声明模式
	 * 
	 * @param isEnabled
	 */
	public static void decalred(boolean isEnabled) {
		isDecalred = isEnabled;
	}

	/**
	 * 获取构造方法
	 * 
	 * @param clazz
	 * @return
	 */
	public static Constructor<?>[] getConstructors(Class<?> clazz) {
		if (Assert.isNull(clazz))
			return null;
		return isDecalred ? clazz.getDeclaredConstructors() : clazz.getConstructors();
	}

	/**
	 * 获取构造方法
	 * 
	 * @param clazz
	 * @param parameterTypes
	 * @return
	 */
	public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameterTypes) {
		if (Assert.isNull(clazz))
			return null;
		try {
			return isDecalred ? clazz.getDeclaredConstructor(parameterTypes) : clazz.getConstructor(parameterTypes);
		} catch (Exception e) {
			Log4jUtils.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 获取实例
	 * 
	 * @param constructor
	 * @param initargs
	 * @return
	 */
	public static <T> T newInstance(Constructor<T> constructor, Object... initargs) {
		if (Assert.isNull(constructor))
			return null;
		try {
			return constructor.newInstance(initargs);
		} catch (Exception e) {
			Log4jUtils.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 获取实例
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> T newInstance(Class<T> clazz) {
		if (Assert.isNull(clazz))
			return null;
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			Log4jUtils.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 获取所有属性
	 * 
	 * @param 类
	 * @return 属性集合
	 */
	public static Field[] getFields(Class<?> clazz) {
		if (Assert.isNull(clazz))
			return null;
		return isDecalred ? clazz.getDeclaredFields() : clazz.getFields();
	}

	/**
	 * 获取属性
	 * 
	 * @param 类
	 * @param 属性名
	 * @return 属性
	 */
	public static Field getField(Class<?> clazz, String name) {
		if (Assert.isNull(clazz) || !Assert.notEmpty(name))
			return null;
		try {
			return isDecalred ? clazz.getDeclaredField(name) : clazz.getField(name);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取所有方法
	 * 
	 * @param 类
	 * @return 方法集合
	 */
	public static Method[] getMethods(Class<?> clazz) {
		if (Assert.isNull(clazz))
			return null;
		return isDecalred ? clazz.getDeclaredMethods() : clazz.getMethods();
	}

	/**
	 * 获取制定名的方法
	 * 
	 * @param clazz
	 * @param name
	 * @return
	 */
	public static Method[] getMethods(Class<?> clazz, String regexName) {
		if (Assert.isNull(clazz, regexName) || Assert.isEmpty(regexName))
			return null;
		Method[] methods = getMethods(clazz);
		if (null == methods || methods.length == 0)
			return null;
		List<Method> list = new ArrayList<Method>();
		for (Method method : methods) {
			if (RegexUtils.isMatch(regexName, method.getName()))
				list.add(method);
		}
		methods = new Method[list.size()];
		list.toArray(methods);
		return methods;
	}

	/**
	 * 获取方法
	 * 
	 * @param 类
	 * @param 方法名
	 * @param 方法参数集合
	 * @return 方法
	 */
	public static Method getMethod(Class<?> clazz, String name, Class<?>... clazzs) {
		if (Assert.isNull(clazz) || !Assert.notEmpty(name))
			return null;
		try {
			return isDecalred ? clazz.getDeclaredMethod(name, clazzs) : clazz.getMethod(name, clazzs);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 调用方法
	 * 
	 * @param 实例
	 *            静态方法:null
	 * @param 方法
	 * @param 参数
	 * @return 结果
	 */
	public static Object invoke(Object target, Method method, Object... args) {
		if (Assert.isNull(method))
			return null;
		try {
			return method.invoke(target, args);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取泛型
	 * 
	 * @param clazz
	 * @param idx
	 * @return
	 */
	public static Class<?> getGenericType(Class<?> clazz, int idx) {
		if (null == clazz)
			return null;
		Type genericType = clazz.getGenericSuperclass();
		if (genericType instanceof ParameterizedType) {
			ParameterizedType type = (ParameterizedType) genericType;
			int count = type.getActualTypeArguments().length;
			if (idx < count)
				return type.getActualTypeArguments()[idx].getClass();
		}
		return null;

	}
}

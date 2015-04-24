package com.twm.pt.softball.softballlist.utility;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Preference工具类
 *
 * @author Altas
 * @email Altas.TuTu@gmail.com
 * @date 2014年9月25日
 */
public class PreferenceUtils {
    public final static String TAG = PreferenceUtils.class.getSimpleName();

    private PreferenceUtils() {
    }

    public static boolean setValue(Context context, String key, String value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.edit().putString(key, value).commit();
    }

    public static String getValue(Context context, String key, String defValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(key, defValue);
    }

    /**
     * @param context
     * @param name    Desired preferences file. If a preferences file by this name does not exist, it will be created when you retrieve an editor
     * @param key
     * @param value
     * @return
     */
    public static boolean setValue(Context context, String name, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.edit().putString(key, value).commit();
    }

    public static String getValue(Context context, String name, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public static boolean setValue(Context context, String key, boolean value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getValue(Context context, String key, boolean defValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(key, defValue);
    }

    public static boolean setValue(Context context, String name, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getValue(Context context, String name, String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    public static boolean setValue(Context context, String name, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.edit().putInt(key, value).commit();
    }

    public static int getValue(Context context, String name, String key, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    public static boolean setValue(Context context, String key, int value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.edit().putInt(key, value).commit();
    }

    public static int getValue(Context context, String key, int defValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(key, defValue);
    }

    public static boolean setValue(Context context, String key, long value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.edit().putLong(key, value).commit();
    }

    public static long getValue(Context context, String key, long defValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getLong(key, defValue);
    }

    /**
     * 存储Object,支持简单类型
     *
     * @param context
     * @param o
     * @return
     */
    public static boolean setObject(Context context, Object o) {
        Field[] fields = o.getClass().getFields();
        SharedPreferences sp = context.getSharedPreferences(o.getClass()
                .getName(), Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        for (int i = 0; i < fields.length; i++) {
            Class<?> type = fields[i].getType();
            if (isSingle(type)) {
                try {
                    final String name = fields[i].getName();
                    if (type == Character.TYPE || type.equals(String.class)) {
                        Object value = fields[i].get(o);
                        if (null != value)
                            editor.putString(name, value.toString());
                    } else if (type.equals(int.class)
                            || type.equals(Short.class))
                        editor.putInt(name, fields[i].getInt(o));
                    else if (type.equals(double.class))
                        editor.putFloat(name, (float) fields[i].getDouble(o));
                    else if (type.equals(float.class))
                        editor.putFloat(name, fields[i].getFloat(o));
                    else if (type.equals(long.class))
                        editor.putLong(name, fields[i].getLong(o));
                    else if (type.equals(Boolean.class))
                        editor.putBoolean(name, fields[i].getBoolean(o));

                } catch (IllegalAccessException e) {
                    Log.e(TAG, e.getMessage());
                } catch (IllegalArgumentException e) {
                    Log.e(TAG, e.getMessage());
                }
            } else {
                // FIXME is not simple type,not write
            }
        }

        return editor.commit();
    }

    /**
     * 存储Object,支持简单类型
     *
     * @param context
     * @param <T>
     * @return
     */
    public static <T> T getObject(Context context, Class<T> clazz) {
        T o = null;
        try {
            o = clazz.newInstance();
        } catch (InstantiationException e) {
            Log.e(TAG, e.getMessage());
            return o;
        } catch (IllegalAccessException e) {
            Log.e(TAG, e.getMessage());
            return o;
        }
        Field[] fields = clazz.getFields();
        SharedPreferences sp = context.getSharedPreferences(clazz.getName(),
                Context.MODE_PRIVATE);
        for (int i = 0; i < fields.length; i++) {
            Class<?> type = fields[i].getType();
            if (isSingle(type)) {
                try {
                    final String name = fields[i].getName();
                    fields[i].setAccessible(true);
                    if (type == Character.TYPE || type.equals(String.class)) {
                        final String value = sp.getString(name, null);
                        if (null != value)
                            fields[i].set(o, value);
                    } else if (type.equals(int.class)
                            || type.equals(Short.class))
                        fields[i].setInt(o, sp.getInt(name, 0));
                    else if (type.equals(double.class))
                        fields[i].setDouble(o, sp.getFloat(name, 0));
                    else if (type.equals(float.class))
                        fields[i].setFloat(o, sp.getFloat(name, 0));
                    else if (type.equals(long.class))
                        fields[i].setLong(o, sp.getLong(name, 0));
                    else if (type.equals(Boolean.class))
                        fields[i].setBoolean(o, sp.getBoolean(name, false));

                } catch (IllegalAccessException e) {
                    Log.e(TAG, e.getMessage());
                } catch (IllegalArgumentException e) {
                    Log.e(TAG, e.getMessage());
                }
            } else {
                // FIXME is not simple type,not write
            }
        }

        return o;
    }

    /**
     * 判断是否是值类型 *
     */
    private static boolean isSingle(Class<?> clazz) {
        return isBoolean(clazz) || isNumber(clazz) || isString(clazz);
    }

    /**
     * 是否布尔值 *
     */
    public static boolean isBoolean(Class<?> clazz) {
        return (clazz != null)
                && ((Boolean.TYPE.isAssignableFrom(clazz)) || (Boolean.class
                .isAssignableFrom(clazz)));
    }

    /**
     * 是否数值 *
     */
    public static boolean isNumber(Class<?> clazz) {
        return (clazz != null)
                && ((Byte.TYPE.isAssignableFrom(clazz))
                || (Short.TYPE.isAssignableFrom(clazz))
                || (Integer.TYPE.isAssignableFrom(clazz))
                || (Long.TYPE.isAssignableFrom(clazz))
                || (Float.TYPE.isAssignableFrom(clazz))
                || (Double.TYPE.isAssignableFrom(clazz)) || (Number.class
                .isAssignableFrom(clazz)));
    }

    /**
     * 判断是否是字符串 *
     */
    public static boolean isString(Class<?> clazz) {
        return (clazz != null)
                && ((String.class.isAssignableFrom(clazz))
                || (Character.TYPE.isAssignableFrom(clazz)) || (Character.class
                .isAssignableFrom(clazz)));
    }

    public static void clearSettings(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().clear().commit();
    }


//    public static String toJson() {
//        Gson gson = new Gson();
//        String json = gson.toJson(MyObject);
//    }
}
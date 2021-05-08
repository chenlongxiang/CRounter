package com.chenlx.crouter_init;


import android.content.Context;
import android.util.Log;

import com.chenlx.crouter_annotation.ModuleFunction;
import com.chenlx.crouter_init.utils.ClassUtils;
import com.chenlx.crouter_init.utils.DebugUtils;
import com.chenlx.crouter_init.utils.PackageUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.chenlx.crouter_init.utils.Constants.CROUTER_SP_CACHE_KEY;
import static com.chenlx.crouter_init.utils.Constants.CROUTER_SP_KEY_API_MAP;
import static com.chenlx.crouter_init.utils.Constants.CROUTER_SP_KEY_MAP;


public class CRouter {


    /**
     * 所有method 调用对象集合
     */
    public static Map<String, Object> allInstancesContainer = new HashMap<>();
    /**
     * 根据模块名称 ，拆分的method调用对象集合
     */
    public static Map<String, Map<String, Object>> instancesByGroupContainer = new HashMap<>();

    /**
     * 所有的拦截对象
     */
    public static Map<String, Object> allInterceptorContainer = new HashMap<>();
    /**
     * 对应method调用对象的拦截器类名
     */
    public static Map<String, Class[]> specificInterceptors = new HashMap<>();


    public static Map<String, Class[]> getSpecificInterceptors() {
        return specificInterceptors;
    }

    public static Map<String, Object> getAllInstancesContainer() {
        return allInstancesContainer;
    }

    public static Map<String, Map<String, Object>> getInstancesByGroupContainer() {
        return instancesByGroupContainer;
    }


    public static Map<String, Object> getInterceptorContainer() {
        return allInterceptorContainer;
    }

    static {
        allInstancesContainer.clear();
        instancesByGroupContainer.clear();
        allInterceptorContainer.clear();
        specificInterceptors.clear();
    }

    public static final String TAG = "CRouter_init";

    private volatile static boolean hasInit = false;


    public static boolean isHasInit() {
        return hasInit;
    }

    public static void setHasInit(boolean hasInit) {
        CRouter.hasInit = hasInit;
    }

    /**
     * @param context
     * @param packgeName     应用包，所有的模块都需要以此开头
     * @param excludePackage 需要过滤的路径
     * @param apiPath        api路径，主要是接口声明，特殊扫描
     * @throws Exception
     */
    public static synchronized boolean init(Context context, String packgeName,
                                            String excludePackage,
                                            String apiPath) throws Exception {


        /**
         * 判断是否在debug,以及版本对比
         */

        Set<String> routerMap;
        Set<String> apiMap;


        if (DebugUtils.isApkInDebug(context) || PackageUtils.isNewVersion(context)) {


            Set[] sets = getNewSet(context, packgeName, excludePackage,
                    apiPath);

            routerMap = sets[0];
            apiMap = sets[1];


        } else {

            routerMap = new HashSet<>(context.getSharedPreferences(CROUTER_SP_CACHE_KEY,
                    Context.MODE_PRIVATE).getStringSet(CROUTER_SP_KEY_MAP, new HashSet<String>()));


            apiMap = new HashSet<>(context.getSharedPreferences(CROUTER_SP_CACHE_KEY,
                    Context.MODE_PRIVATE).getStringSet(CROUTER_SP_KEY_API_MAP,
                    new HashSet<String>()));


            /**
             * 容错，有为空的，重新获取更新一次
             */
            if (routerMap == null || apiMap == null) {
                Set[] sets = getNewSet(context, packgeName, excludePackage,
                        apiPath);

                routerMap = sets[0];
                apiMap = sets[1];

            }


        }


        /**
         * 查找接口实现类，解析其注解，获取其对象以及参数。
         */


        for (String className : routerMap) {

            Log.i(TAG, "routerMap:" + className);

            Class c = Class.forName(className);
            try {

                /**
                 *isAssignableFrom
                 *
                 * A.isAssignableFrom(B)
                 * 判断A是否是B的父类
                 */

                if (c.isAnnotationPresent(ModuleFunction.class)) {

                    Class[] classes = c.getInterfaces();
                    if (classes != null) {
                        for (Class i : classes) {
                            if (apiMap.contains(i.getName())) {
                                Log.i(TAG, "getInterfaces contains:" + className);
                                /**
                                 * TODO
                                 * 初始化以及保存
                                 */
                                Object object = c.getConstructor().newInstance();


                                /**
                                 * 如果有通过注解添加拦截器，那么在此设置。
                                 */

                                ModuleFunction moduleFunction =
                                        (ModuleFunction) c.getAnnotation(ModuleFunction.class);
                                Class[] interceptors = moduleFunction.interceptor();

                                Log.i(TAG, "interceptor :" + interceptors);

                                if (interceptors != null) {


                                    /**
                                     * 另外保存一个映射关系中
                                     *
                                     * i-> class[]
                                     */


                                    for (Class cl : interceptors) {

                                        /**
                                         * 保存关系
                                         */


                                        if (!getInterceptorContainer().containsKey(cl.getName())) {

                                            Object io = cl.getConstructor().newInstance();
                                            getInterceptorContainer().put(cl.getName(), io);
                                            Log.i(TAG, "interceptor :" + specificInterceptors);


                                            Log.i(TAG, "interceptor :" + cl.getName());


                                        }
                                    }


                                    specificInterceptors.put(i.getName(), interceptors);
                                    Log.i(TAG, "interceptor :" + specificInterceptors);


                                }


                                allInstancesContainer.put(i.getName(), object);

                                String pName = c.getPackage().getName();


                                /**
                                 * 针对
                                 */
                                Map<String, Object> containeOfModule;
                                if (instancesByGroupContainer.containsKey(pName)) {

                                    containeOfModule = instancesByGroupContainer.get(pName);

                                } else {
                                    containeOfModule = new HashMap<>();
                                }


                                containeOfModule.put(i.getName(), object);

                                instancesByGroupContainer.put(pName, containeOfModule);


                            }
                        }
                    }


                }


            } catch (Exception e) {
                Log.i(TAG, "routerMap,********has:" + c + ",e" + e);
                e.printStackTrace();
            }
        }


        return true;
    }


    private static Set[] getNewSet(Context context, String packgeName, String excludePackage,
                                   String apiPath) throws Exception {

        Set[] sets;

        // It will rebuild router map every times when debuggable.
        sets = ClassUtils.getFileNameByPackageName(context, packgeName, excludePackage,
                apiPath);


        /**
         * 保存中文件
         */

        context.getSharedPreferences(CROUTER_SP_CACHE_KEY, Context.MODE_PRIVATE).edit().putStringSet(CROUTER_SP_KEY_MAP, sets[0]).apply();
        context.getSharedPreferences(CROUTER_SP_CACHE_KEY, Context.MODE_PRIVATE).edit().putStringSet(CROUTER_SP_KEY_API_MAP, sets[1]).apply();

        PackageUtils.updateVersion(context);

        return sets;
    }
}
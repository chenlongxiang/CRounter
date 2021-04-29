package com.chenlx.crouter_init.proxy;

import com.chenlx.crouter_api.IModuleMain;
import com.chenlx.crouter_init.$;

import java.lang.reflect.Proxy;

public class ProxyManager {


    /**
     * 它用来生成代理对象
     * 它需要所有的参数
     * 目标对象
     * 增强
     */
    /*
     * 1、创建代理工厂
     * 2、给工厂设置三样东西：
     *   目标对象：setTargetObject(XXX);
     *   前置增强：setBeforeAdvice(该接口的实现)
     *   后置增强：setAfterAdvice（该接口的实现）
     * 3、调用createProxy（）得到代理对象
     *   执行代理对象方法时：
     *       执行BeforeAdvice的before（）
     *       目标对象的目标方法
     *       执行AfterAdvice的after（）
     * */
    public static class ProxyFactory {


        //用来生成代理对象
        public static <T> T createProxy(Object targetObject, String clasName) {
            /*
             * 1、给出三大参数
             * */
            ClassLoader loader = targetObject.getClass().getClassLoader();
            Class[] interfaces = targetObject.getClass().getInterfaces();

            ProxyInvocationHandler h = new ProxyInvocationHandler(targetObject, clasName);

            //2、得到代理对象
            Object proxObject = Proxy.newProxyInstance(loader, interfaces, h);
            return (T) proxObject;
        }


    }

    public static void main(String[] args) {

        IModuleMain iModuleMain = $.find(IModuleMain.class);
        IModuleMain iModuleMainP = ProxyFactory.createProxy(
                iModuleMain, IModuleMain.class.getName());

        iModuleMainP.callModule("ddd");
    }
}

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
//        private Object targetObject;//目标对象
//        private BeforeAdvice beforeAdvice;//前置增强
//        private AfterAdvice afterAdvice;//后置增强

        //用来生成代理对象
        public static <T> T createProxy(Object targetObject) {
            /*
             * 1、给出三大参数
             * */
            ClassLoader loader = targetObject.getClass().getClassLoader();
            Class[] interfaces = targetObject.getClass().getInterfaces();
//            InvocationHandler h = new InvocationHandler() {
//                @Override
//                public Object invoke(Object proxy, Method method, Object[] args) throws
//                Throwable {
//                    /*
//                     * 在调用代理对象的方法时会执行这里的内容
//                     * */
//                    //执行前置增强
////                    if (beforeAdvice != null) {
////                        beforeAdvice.before();
////                    }
//                    T result = (T) method.invoke(targetObject, args);//执行目标对象的目标方法
//                    //执行后置增强
////                    if (afterAdvice != null) {
////                        afterAdvice.after();
////                    }
//                    return result;
//                }
//            };

            ProxyInvocationHandler h = new ProxyInvocationHandler(targetObject);

            //2、得到代理对象
            Object proxObject = Proxy.newProxyInstance(loader, interfaces, h);
            return (T) proxObject;
        }


    }

    public static void main(String[] args) {

        IModuleMain iModuleMain = $.find(IModuleMain.class);
        IModuleMain iModuleMainP = ProxyFactory.createProxy(
                iModuleMain);

        iModuleMainP.callModule("ddd");
    }
}

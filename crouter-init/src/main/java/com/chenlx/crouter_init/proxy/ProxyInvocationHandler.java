package com.chenlx.crouter_init.proxy;

import android.util.Log;

import com.chenlx.crouter_api.Interceptor;
import com.chenlx.crouter_init.$;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyInvocationHandler<T> implements InvocationHandler {

    T target;
    String clasName;

    public ProxyInvocationHandler(T target, String clasName) {
        this.target = target;
        this.clasName = clasName;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        /**
         * 可以做的事情
         */

        Log.i("ProxyInvocationHandler", "invoke");

        Class[] iInterceptors = $.findSpecificInterceptor(clasName);


        if (iInterceptors != null) {


            for (Class c : iInterceptors) {

                Interceptor i = $.findInterceptor(c.getName());
                if (!i.interceptor()) {

                    Method interceptorMethod = target.getClass().getMethod("intercepter",
                            String.class);

                    return interceptorMethod.invoke(target, i.getClass().getName());
                }
            }
        }


        Object result = method.invoke(target, args);


        Log.i("ProxyInvocationHandler", "invoke after");
        return result;
    }
}

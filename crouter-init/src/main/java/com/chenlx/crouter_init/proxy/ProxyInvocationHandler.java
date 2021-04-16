package com.chenlx.crouter_init.proxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyInvocationHandler<T> implements InvocationHandler {

    T target;

    public ProxyInvocationHandler(T target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        /**
         * 可以做的事情
         */

        Log.i("ProxyInvocationHandler", "invoke");

        Object result = method.invoke(target, args);


        Log.i("ProxyInvocationHandler", "invoke after");
        return result;
    }
}

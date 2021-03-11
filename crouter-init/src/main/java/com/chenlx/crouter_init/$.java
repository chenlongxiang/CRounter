package com.chenlx.crouter_init;

import java.util.HashMap;
import java.util.Map;

public class $ {


    public static <T> T find(Class<T> claz) {

        return (T) CRouter.getAllInstancesContainer().get(claz.getName());

    }

    public static <T> T find(String modulePath, Class<T> claz) {

        return (T) CRouter.getInstancesByGroupContainer().get(modulePath).get(claz.getName());

    }


    public static void addOrReplaceInstance(Object object, Class tClass) throws Exception {


        CRouter.getAllInstancesContainer().put(tClass.getName(), object);


        String modulePackage = object.getClass().getPackage().getName();

        boolean containModule =
                CRouter.getInstancesByGroupContainer().containsKey(modulePackage);

        Map<String, Object> map;
        if (!containModule) {

            map = new HashMap<>();

        } else {
            map = CRouter.getInstancesByGroupContainer().get(modulePackage);
        }

        map.put(tClass.getName(), object);

        CRouter.getInstancesByGroupContainer().put(modulePackage, map);

    }

}
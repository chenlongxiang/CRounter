package com.chenlx.crouter_init.mapmanager;

import java.util.HashMap;
import java.util.Map;

public class MapManager {


    /**
     * 包名和《路径和对象》
     */
    static Map<String, Map<String, Object>> container = new HashMap<>();

    static Map<String, Map<String, Object>> getContainer() {
        return container;
    }


    static {


        container.clear();
    }


}

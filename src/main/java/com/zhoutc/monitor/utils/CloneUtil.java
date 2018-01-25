package com.zhoutc.monitor.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-21 18:29
 */
public class CloneUtil {

    private CloneUtil(){
    }

    public static List cloneList(List origin) {
        List copy = new ArrayList<>(Arrays.asList(new String[origin.size()]));
        Collections.copy(copy, origin);
        return copy;
    }
}

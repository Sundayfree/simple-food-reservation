package com.weltec.utils;

import java.util.Random;

/**
 * Description:
 * Author: Liu
 * Date: 2019-05-04 14:42
 */
public class KeyGenerator {
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}

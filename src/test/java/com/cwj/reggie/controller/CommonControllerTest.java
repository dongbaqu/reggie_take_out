package com.cwj.reggie.controller;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;

/**
 * @author cwj
 * @version 1.0.0
 * @date 2022/9/16 22:07
 * @description:
 */

class CommonControllerTest {

    @Test
    void upload() {
        String test = "0f252364-a561-4e8d-8065-9a6797a6b1d3.jpg";
        String[] split = test.split("\\.");
        System.out.println(split);
    }

    @Test
    void test() {
        String test = "2";
        System.out.println(Strings.isBlank(test));
    }

}
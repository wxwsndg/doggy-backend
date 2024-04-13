package com.doggy.project.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UserInterfaceInfoServiceTest {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Test
    public void invokeCount() {
        //调用了userInterfaceInfoService的invokeCount方法,并传入两个参数(1L，1L)
        boolean b = userInterfaceInfoService.invokeCount(1L,1L);
        //表示断言b的值为true，即测试用例期望invokecount方法返回true
        Assertions.assertTrue(b);
    }
}
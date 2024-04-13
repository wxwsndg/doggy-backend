package com.doggy.doggyinterface;

import com.doggy.doggyclientsdk.client.DoggyApiclient;
import com.doggy.doggyclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

//表示这是一个基于Spring Boot的测试类
@SpringBootTest
class DoggyInterfaceApplicationTests {
    //注入一个名为yuApiclient的Bean
    @Resource
    private DoggyApiclient doggyApiclient;
    //表示这是一个测试方法
    @Test
    void contextLoads() {
        //调用doggyApiclient的getNameByGet方法，并传入参数"apple"
        String result = doggyApiclient.getNameByGet("apple");
        //创建一个User对象
        User user = new User();
        //设置User对象的username属性为"banana"
        user.setUsername("banana");
        //调用doggyApiclient的getUserNameByPost方法，并传入user对象作为参数，将返回的结果赋值给usernameByPost变量
        String usernameByPost = doggyApiclient.getUserNameByPost(user);
        //打印result变量的值
        System.out.println(result);
        //打印usernameByPost变量的值
        System.out.println(usernameByPost);
    }

}

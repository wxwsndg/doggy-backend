package com.doggy.doggyinterface.controller;

import com.doggy.doggyclientsdk.model.User;
import com.doggy.doggyclientsdk.utils.SignUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 名称API
 * @author Ziqing Sun
 */
@RestController
@RequestMapping("/name")
public class NameController {
    @GetMapping("/get")
    public String getNameByGet(String name,HttpServletRequest request){
        //获取请求头名为”abandon“的值
        System.out.println(request.getHeader("abandon"));
        return "GET方法 你的名字是:" +name;
    }
    @PostMapping("/post")
    public String getNameByPost(@RequestParam String name){
        return "POST方法 你的名字是:"+name;
    }
    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request){
        String result = "使用POST方法 用户名字是" + user.getUsername();
        return result;

    }
}

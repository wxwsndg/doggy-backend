package com.doggy.doggyinterface.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/***
 * @author Ziqing Sun
 * 每日一句功能控制器
 */
@RestController
@RequestMapping("/perdayaline")
public class PerDayEnglishController {

    @PostMapping("/post")
    public String getPerDayALineByPost(){
        System.out.println("每日一句接口调用");
        String[] str_array= new String[11];//11句
        str_array[0] = "两个黄鹂鸣翠柳，一行白鹭上青天。";
        str_array[1] = "桃李春风一杯酒，江湖夜雨十年灯。";
        str_array[2] = "唯见月寒日暖，来煎人寿。";
        str_array[3] = "已识乾坤大，犹怜草木青。";
        str_array[4] = "世事漫随流水，算来一梦浮生。";
        str_array[5] = "夜深忽梦少年事，梦啼妆泪红阑干。";
        str_array[6] = "今人不见古时月，今月曾经照古人。";
        str_array[7] = "人生到处知何似，应是飞鸿踏雪泥。";
        str_array[8] = "满堂花醉三千客，一剑霜寒十四州。";
        str_array[9] = "君埋泉下泥销骨，我寄人间雪满头。";
        str_array[10] ="我与丹青两幻身，世间流转会成尘。";
        Random random = new Random();
        int index = random.nextInt(str_array.length);// 生成范围在[0, length-1]之间的随机索引值
        String result = str_array[index];
        return result ;
    }
    @GetMapping("/get")
    public String getNameByGet(String name, HttpServletRequest request){
        //获取请求头名为”abandon“的值
        System.out.println(request.getHeader("abandon"));
        return "GET方法 你的名字是:" +name;
    }
}

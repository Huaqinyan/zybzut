package com.zyb.zut.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
//@ComponentScan("com.zyb")当要扫描的包不在application子目录下时需要加，说明需要扫哪一个包下
//@ComponentScan({"com.zyb",con.test}) //扫描不和主类在同一级的类
@RestController //返回一个字字符串，Controller用来返回一个页面
public class TestController {

    @Value("${test.hello:TEST}")//:后面是没有配置的时候的默认值
    public String str;
    /**
     * GTE,POST,PUT,DELETE
     * /user?id=1
     * /user/1
     * @return
     */
    //@RequestMapping("/Hello")//表示这个接口支持所有的请求方式(GET,POST,PUT,DELET)
    //@RequestMapping(value = "/user/1", method = RequestMethod.GET)
    //@RequestMapping(value = "/user/1", method = RequestMethod.DELETE)
    //@DeketeMapping("/hello")
    @GetMapping("/hello")//只支持get请求，变成一个接口
    public String hello(){
        return "Hello World!"+str;
    }

    @PostMapping("/hello/post")//只支持get请求
    public String hellopost(String name){
        return "Hello World!"+name;
    }
}

package com.zyb.zut.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
//@ComponentScan("com.zyb")
//@ComponentScan({"com.zyb",con.test}) //扫描不和主类在同一级的类
@RestController
public class TestController {

    /**
     * GTE,POST,PUT,DELETE
     * @return
     */
    //@RequestMapping("/Hello")//表示这个接口支持所有的请求方式(GET,POST,PUT,DELET)
    //@RequestMapping(value = "/hello", method = RequestMethod.GET)
    //@RequestMapping(value = "/hello", method = RequestMethod.DELETE)
    //@DeketeMapping("/hello")
    @GetMapping("/hello")//只支持get请求
    public String hello(){
        return "Hello World!";
    }
}

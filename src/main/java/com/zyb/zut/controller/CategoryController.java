package com.zyb.zut.controller;

import com.zyb.zut.req.CategoryQueryReq;
import com.zyb.zut.req.CategorySaveReq;
import com.zyb.zut.resp.CommonResp;
import com.zyb.zut.resp.CategoryQueryResp;
import com.zyb.zut.resp.PageResq;
import com.zyb.zut.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/all")
    public CommonResp all(){
        CommonResp<List<CategoryQueryResp>> resp =new CommonResp<>();
        List<CategoryQueryResp> list= categoryService.all();
        resp.setContent(list);
        return resp;
    }

    @GetMapping("/list")
    public CommonResp list(@Valid CategoryQueryReq categoryQuerykReq){
        CommonResp<PageResq<CategoryQueryResp>> resp =new CommonResp<>();
        PageResq<CategoryQueryResp> list= categoryService.list(categoryQuerykReq);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody CategorySaveReq categorySaveReq){
        CommonResp resp =new CommonResp<>();
        categoryService.save(categorySaveReq);
        return resp;
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp =new CommonResp<>();
        categoryService.delete(id);
        return resp;
    }
}

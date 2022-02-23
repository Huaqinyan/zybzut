package com.zyb.zut.controller;

import com.zyb.zut.req.EbooQuerykReq;
import com.zyb.zut.req.EbooSavekReq;
import com.zyb.zut.resp.CommonResp;
import com.zyb.zut.resp.EbookQueryResp;
import com.zyb.zut.resp.PageResq;
import com.zyb.zut.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(@Valid EbooQuerykReq ebooQuerykReq){
        CommonResp<PageResq<EbookQueryResp>> resp =new CommonResp<>();
        PageResq<EbookQueryResp> list= ebookService.list(ebooQuerykReq);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@RequestBody EbooSavekReq ebooSavekReq){
        CommonResp resp =new CommonResp<>();
        ebookService.save(ebooSavekReq);
        return resp;
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp =new CommonResp<>();
        ebookService.delete(id);
        return resp;
    }
}

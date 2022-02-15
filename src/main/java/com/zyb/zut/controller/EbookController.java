package com.zyb.zut.controller;

import com.github.pagehelper.Page;
import com.zyb.zut.req.EbookReq;
import com.zyb.zut.resp.CommonResp;
import com.zyb.zut.resp.EbookResp;
import com.zyb.zut.resp.PageResq;
import com.zyb.zut.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(EbookReq ebookReq){
        CommonResp<PageResq<EbookResp>> resp =new CommonResp<>();
        PageResq<EbookResp> list= ebookService.list(ebookReq);
        resp.setContent(list);
        return resp;
    }
}

package com.zyb.zut.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zyb.zut.domain.Ebook;
import com.zyb.zut.domain.EbookExample;
import com.zyb.zut.mapper.EbookMapper;
import com.zyb.zut.req.EbookReq;
import com.zyb.zut.resp.EbookResp;
import com.zyb.zut.resp.PageResq;
import com.zyb.zut.util.CopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    private static final Logger LOG= LoggerFactory.getLogger(EbookService.class);
    @Resource
    private EbookMapper ebookMapper;


    public PageResq<EbookResp> list(EbookReq ebookReq){

        EbookExample ebookExample=new EbookExample();
        EbookExample.Criteria criteria=ebookExample.createCriteria();
        if(!ObjectUtils.isEmpty(ebookReq.getName())){
            criteria.andNameLike("%"+ebookReq.getName()+"%");
        }
        PageHelper.startPage(ebookReq.getPage(),ebookReq.getSize());
        List<Ebook> ebookList= ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook> pageInfo=new PageInfo<>(ebookList);
        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());
//        List<EbookResp> respList=new ArrayList<>();
//        for (Ebook ebook:ebookList){
////            EbookResp ebookResp=new EbookResp();
////            BeanUtils.copyProperties(ebook,ebookResp);
//            对象复制
//            EbookResp ebookResp= CopyUtil.copy(ebook,EbookResp.class);
//
//            respList.add(ebookResp);
//        }

//        列表复制
        List<EbookResp> list = CopyUtil.copyList(ebookList, EbookResp.class);
        PageResq<EbookResp> pageResq=new PageResq<>();
        pageResq.setTotal(pageInfo.getTotal());
        pageResq.setList(list);
        return pageResq;
    }
}

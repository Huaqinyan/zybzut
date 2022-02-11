package com.zyb.zut.service;

import com.zyb.zut.domain.Ebook;
import com.zyb.zut.domain.EbookExample;
import com.zyb.zut.mapper.EbookMapper;
import com.zyb.zut.req.EbookReq;
import com.zyb.zut.resp.EbookResp;
import com.zyb.zut.util.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {
    @Resource
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq ebookReq){
        EbookExample ebookExample=new EbookExample();
        EbookExample.Criteria criteria=ebookExample.createCriteria();
        criteria.andNameLike("%"+ebookReq.getName()+"%");
        List<Ebook> ebookList= ebookMapper.selectByExample(ebookExample);

//        List<EbookResp> respList=new ArrayList<>();
//        for (Ebook ebook:ebookList){
////            EbookResp ebookResp=new EbookResp();
////            BeanUtils.copyProperties(ebook,ebookResp);
//            对象复制
//            EbookResp ebookResp= CopyUtil.copy(ebook,EbookResp.class);
//
//            respList.add(ebookResp);
//        }

        List<EbookResp> list = CopyUtil.copyList(ebookList, EbookResp.class);
        return list;
    }
}

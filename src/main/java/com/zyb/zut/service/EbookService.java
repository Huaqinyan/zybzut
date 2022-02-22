package com.zyb.zut.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zyb.zut.domain.Ebook;
import com.zyb.zut.domain.EbookExample;
import com.zyb.zut.mapper.EbookMapper;
import com.zyb.zut.req.EbooQuerykReq;
import com.zyb.zut.req.EbooSavekReq;
import com.zyb.zut.resp.EbookQueryResp;
import com.zyb.zut.resp.PageResq;
import com.zyb.zut.util.CopyUtil;
import com.zyb.zut.util.SnowFlake;
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

    @Resource
    private SnowFlake snowFlake;


    public PageResq<EbookQueryResp> list(EbooQuerykReq ebooQuerykReq){

        EbookExample ebookExample=new EbookExample();
        EbookExample.Criteria criteria=ebookExample.createCriteria();
        if(!ObjectUtils.isEmpty(ebooQuerykReq.getName())){
            criteria.andNameLike("%"+ ebooQuerykReq.getName()+"%");
        }
        PageHelper.startPage(ebooQuerykReq.getPage(), ebooQuerykReq.getSize());
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
        List<EbookQueryResp> list = CopyUtil.copyList(ebookList, EbookQueryResp.class);
        PageResq<EbookQueryResp> pageResq=new PageResq<>();
        pageResq.setTotal(pageInfo.getTotal());
        pageResq.setList(list);
        return pageResq;
    }

    /**
     * 保存
     * @param req
     */
    public void save(EbooSavekReq req){
        Ebook ebook=CopyUtil.copy(req,Ebook.class);
        if(ObjectUtils.isEmpty(req.getId())){
            //新增
            ebook.setId(snowFlake.nextId());
            ebookMapper.insert(ebook);
        }else{
            //更新
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }
}

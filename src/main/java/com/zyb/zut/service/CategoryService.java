package com.zyb.zut.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zyb.zut.domain.Category;
import com.zyb.zut.domain.CategoryExample;
import com.zyb.zut.mapper.CategoryMapper;
import com.zyb.zut.req.CategoryQueryReq;
import com.zyb.zut.req.CategorySaveReq;
import com.zyb.zut.resp.CategoryQueryResp;
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
public class CategoryService {

    private static final Logger LOG= LoggerFactory.getLogger(CategoryService.class);
    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;


    public List<CategoryQueryResp> all(){

        CategoryExample categoryExample=new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList= categoryMapper.selectByExample(categoryExample);

//        列表复制
        List<CategoryQueryResp> list = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
        return list;
    }

    public PageResq<CategoryQueryResp> list(CategoryQueryReq categoryQueryReq){

        CategoryExample categoryExample=new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        CategoryExample.Criteria criteria=categoryExample.createCriteria();
        PageHelper.startPage(categoryQueryReq.getPage(), categoryQueryReq.getSize());
        List<Category> categoryList= categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo=new PageInfo<>(categoryList);
        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());
//        List<CategoryResp> respList=new ArrayList<>();
//        for (Category category:categoryList){
////            CategoryResp categoryResp=new CategoryResp();
////            BeanUtils.copyProperties(category,categoryResp);
//            对象复制
//            CategoryResp categoryResp= CopyUtil.copy(category,CategoryResp.class);
//
//            respList.add(categoryResp);
//        }

//        列表复制
        List<CategoryQueryResp> list = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
        PageResq<CategoryQueryResp> pageResq=new PageResq<>();
        pageResq.setTotal(pageInfo.getTotal());
        pageResq.setList(list);
        return pageResq;
    }

    /**
     * 保存
     * @param req
     */
    public void save(CategorySaveReq req){
        Category category=CopyUtil.copy(req,Category.class);
        if(ObjectUtils.isEmpty(req.getId())){
            //新增
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        }else{
            //更新
            categoryMapper.updateByPrimaryKey(category);
        }
    }
    /**
     * 删除
     */
    public void delete(Long id){
        categoryMapper.deleteByPrimaryKey(id);
    }
}

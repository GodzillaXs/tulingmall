package com.tulingxueyuan.mall.modules.pms.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.dto.ProductCategoryDTO;
import com.tulingxueyuan.mall.dto.ProductCategoryTwoDTO;
import com.tulingxueyuan.mall.modules.pms.entity.Brand;
import com.tulingxueyuan.mall.modules.pms.entity.ProductCategory;
import com.tulingxueyuan.mall.modules.pms.entity.ProductCategoryAttributeRelation;
import com.tulingxueyuan.mall.modules.pms.mapper.ProductCategoryAttributeRelationMapper;
import com.tulingxueyuan.mall.modules.pms.mapper.ProductCategoryMapper;
import com.tulingxueyuan.mall.modules.pms.service.ProductCategoryAttributeRelationService;
import com.tulingxueyuan.mall.modules.pms.service.ProductCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 *
 * @author fyl
 * @since 2021-11-15
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

    @Autowired
    ProductCategoryAttributeRelationService productCategoryAttributeRelationService;

    @Autowired
    ProductCategoryMapper productCategoryMapper;

    @Override
    public Page getPageF(Long parentId, Integer pageNum, Integer pageSize) {
        //使用plus提供的service
        Page page=new Page(pageNum,pageSize);
        //条件构造器之一的查询构造器
        QueryWrapper<ProductCategory> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(ProductCategory::getParentId,parentId);
        queryWrapper.lambda().orderByAsc(ProductCategory::getSort);
        return this.page(page,queryWrapper);
    }

    @Override
    public boolean updateNavStatus(List<Integer> ids, Integer navStatus) {
        UpdateWrapper<ProductCategory> updateWrapper=new UpdateWrapper<>();
        updateWrapper.lambda()
                //修改内容
                .set(ProductCategory::getNavStatus,navStatus)
                //条件
                .in(ProductCategory::getId,ids);
        return this.update(updateWrapper);
    }

    @Override
    public boolean updateShowStatus(List<Integer> ids, Integer showStatus) {
        UpdateWrapper<ProductCategory> updateWrapper=new UpdateWrapper<>();
        updateWrapper.lambda()
                //修改内容
                .set(ProductCategory::getShowStatus,showStatus)
                //条件
                .in(ProductCategory::getId,ids);
        return this.update(updateWrapper);
    }

    @Override
    @Transactional
    public boolean create(ProductCategoryDTO productCategoryDTO) {
        //保存商品分类信息
        ProductCategory productCategory=new ProductCategory();
        //BeanUtils.copyProperties(a,b)可以把a对象中与b对象属性名相同的属性的值赋值给b对象对应的属性
        BeanUtils.copyProperties(productCategoryDTO,productCategory);
        //给商品数量和级别设置值
        productCategory.setProductCount(0);
        if (productCategory.getParentId()==0){
            productCategory.setLevel(0);
        }else {
            productCategory.setLevel(1);
        }
        boolean save = this.save(productCategory);

        //保存此商品分类与商品属性的关系
        saveProductCategoryAttributeRelation(productCategoryDTO, productCategory);

        return save;
    }

    @Override
    @Transactional
    public boolean updateByIdF(ProductCategoryDTO productCategoryDTO) {
        //保存商品分类信息
        ProductCategory productCategory=new ProductCategory();
        //BeanUtils.copyProperties(a,b)可以把a对象中与b对象属性名相同的属性的值赋值给b对象对应的属性
        BeanUtils.copyProperties(productCategoryDTO,productCategory);
        //给商品数量和级别设置值
        if (productCategory.getParentId()==0){
            productCategory.setLevel(0);
        }else {
            productCategory.setLevel(1);
        }
        boolean update = this.updateById(productCategory);

        //删除已保存的关联商品属性
        QueryWrapper<ProductCategoryAttributeRelation> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(ProductCategoryAttributeRelation::getProductCategoryId,productCategory.getId());
        productCategoryAttributeRelationService.remove(queryWrapper);

        //重新保存此商品分类与商品属性的关系
        saveProductCategoryAttributeRelation(productCategoryDTO, productCategory);

        return update;

    }

    @Override
    public List<ProductCategoryTwoDTO> listWithChildren() {
        return productCategoryMapper.listWithChildren();
    }

    /**
     * @param productCategoryDTO
     * @param productCategory
     * 这是公共部分抽取出来的方法，将商品分类与商品属性的关系保存到ProductCategoryAttributeRelation中
     * @Date 2021/11/19 18:40
     * @return boolean
     */
    public boolean saveProductCategoryAttributeRelation(ProductCategoryDTO productCategoryDTO,ProductCategory productCategory){

        List<Long> productAttributeIdList = productCategoryDTO.getProductAttributeIdList();
        List<ProductCategoryAttributeRelation> list=new ArrayList<>();
        for (Long attId : productAttributeIdList) {
            //ProductCategoryAttributeRelation是保存商品分类id与选中的商品属性id这两个id的中间表,
            // 需要注意的是并没有保存这些商品属性id所对应的商品类型（有点绕，对照数据库的数据多理解），实现商品分类编辑功能的时候会通过属性id再去查询所在的商品类型
            ProductCategoryAttributeRelation productCategoryAttributeRelation=new ProductCategoryAttributeRelation();
            productCategoryAttributeRelation.setProductCategoryId(productCategory.getId());
            productCategoryAttributeRelation.setProductAttributeId(attId);
            list.add(productCategoryAttributeRelation);
        }
        return productCategoryAttributeRelationService.saveBatch(list);
    }

}

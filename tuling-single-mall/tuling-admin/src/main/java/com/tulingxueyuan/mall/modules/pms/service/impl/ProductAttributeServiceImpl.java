package com.tulingxueyuan.mall.modules.pms.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.dto.RelationAttrInfoDTO;
import com.tulingxueyuan.mall.modules.pms.entity.Brand;
import com.tulingxueyuan.mall.modules.pms.entity.ProductAttribute;
import com.tulingxueyuan.mall.modules.pms.entity.ProductAttributeCategory;
import com.tulingxueyuan.mall.modules.pms.mapper.ProductAttributeMapper;
import com.tulingxueyuan.mall.modules.pms.service.ProductAttributeCategoryService;
import com.tulingxueyuan.mall.modules.pms.service.ProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 服务实现类
 * </p>
 *
 * @author fyl
 * @since 2021-11-15
 */
@Service
public class ProductAttributeServiceImpl extends ServiceImpl<ProductAttributeMapper, ProductAttribute> implements ProductAttributeService {

    //为了更新对应商品类型的属性或参数值
    @Autowired
    ProductAttributeCategoryService productAttributeCategoryService;

    @Autowired
    ProductAttributeMapper productAttributeMapper;

    @Override
    public Page getPageF(Long cid, Integer type, Integer pageNum, Integer pageSize) {
        Page page=new Page(pageNum,pageSize);
        QueryWrapper<ProductAttribute> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(ProductAttribute::getProductAttributeCategoryId,cid)
                .eq(ProductAttribute::getType,type);
        queryWrapper.lambda().orderByAsc(ProductAttribute::getSort);
        return this.page(page,queryWrapper);
    }

    @Override
    public boolean create(ProductAttribute productAttribute) {
        boolean save = this.save(productAttribute);
        if (save){
            //更新对应商品类型的属性或参数的数量，加一
            UpdateWrapper<ProductAttributeCategory> updateWrapper=new UpdateWrapper<>();
            if (productAttribute.getType()==0){
                updateWrapper.setSql("attribute_count=attribute_count+1");
            }else if (productAttribute.getType()==1){
                updateWrapper.setSql("param_count=param_count+1");
            }
            updateWrapper.lambda().eq(ProductAttributeCategory::getId,productAttribute.getProductAttributeCategoryId());
            productAttributeCategoryService.update(updateWrapper);
        }
        return  save;
    }

    @Override
    @Transactional //批量操作记得最好加上这个事务注解
    public boolean deleteById(List<Long> ids) {
        //先从ids中取出一个存在的id对应的对象，以便了解删除的数据是属于属性还是参数
        ProductAttribute productAttribute=null;
        for (Long id : ids) {
            productAttribute=this.getById(id);
            if (productAttribute!=null){
                break;
            }
        }
        //删除这些数据
        int length = productAttributeMapper.deleteBatchIds(ids);
        //更新对应的商品类型的属性或参数数量
        if (length>0 && productAttribute !=null){
            //更新对应商品类型的属性或参数的数量，减length
            UpdateWrapper<ProductAttributeCategory> updateWrapper=new UpdateWrapper<>();
            if (productAttribute.getType()==0){
                updateWrapper.setSql("attribute_count=attribute_count-"+length);
            }else if (productAttribute.getType()==1){
                updateWrapper.setSql("param_count=param_count-"+length);
            }
            updateWrapper.lambda().eq(ProductAttributeCategory::getId,productAttribute.getProductAttributeCategoryId());
            productAttributeCategoryService.update(updateWrapper);
        }
        return length>0;
    }

    @Override
    public List<RelationAttrInfoDTO> getRelationAttrInfo(Long productCategoryId) {
       return productAttributeMapper.getRelationAttrInfo(productCategoryId);
    }
}

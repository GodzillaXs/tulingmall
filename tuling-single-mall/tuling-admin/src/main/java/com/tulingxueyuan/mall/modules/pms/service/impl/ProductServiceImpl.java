package com.tulingxueyuan.mall.modules.pms.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.dto.ProductDTO;
import com.tulingxueyuan.mall.dto.ProductThreeDTO;
import com.tulingxueyuan.mall.dto.ProductTwoDTO;
import com.tulingxueyuan.mall.modules.pms.entity.MemberPrice;
import com.tulingxueyuan.mall.modules.pms.entity.Product;
import com.tulingxueyuan.mall.modules.pms.entity.ProductCategory;
import com.tulingxueyuan.mall.modules.pms.entity.ProductLadder;
import com.tulingxueyuan.mall.modules.pms.mapper.ProductMapper;
import com.tulingxueyuan.mall.modules.pms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author fyl
 * @since 2021-11-15
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    MemberPriceService memberPriceService;
    @Autowired
    ProductLadderService productLadderService;
    @Autowired
    ProductFullReductionService productFullReductionService;
    @Autowired
    SkuStockService skuStockService;
    @Autowired
    ProductAttributeValueService productAttributeValueService;

    @Autowired
    ProductMapper productMapper;

    @Override
    public Page list(ProductDTO productDTO) {
        Page page=new Page(productDTO.getPageNum(),productDTO.getPageSize());
        QueryWrapper<Product> queryWrapper=new QueryWrapper<>();
        LambdaQueryWrapper<Product> lambdaQueryWrapper = queryWrapper.lambda();
        //商品名称
        if (!StrUtil.isBlank(productDTO.getKeyword())){
            lambdaQueryWrapper.like(Product::getName,productDTO.getKeyword());
        }
        //商品货号
        if (!StrUtil.isBlank(productDTO.getProductSn())){
            lambdaQueryWrapper.like(Product::getProductSn,productDTO.getProductSn());
        }
        //商品分类
        if (productDTO.getProductCategoryId()!=null){
            lambdaQueryWrapper.eq(Product::getProductCategoryId,productDTO.getProductCategoryId());
        }
        //商品品牌
        if (productDTO.getBrandId()!=null&&productDTO.getBrandId()>0){
            lambdaQueryWrapper.eq(Product::getBrandId,productDTO.getBrandId());
        }
        //上架状态
        if (productDTO.getPublishStatus()!=null){
            lambdaQueryWrapper.eq(Product::getPublishStatus,productDTO.getPublishStatus());
        }
        //审核状态
        if (productDTO.getVerifyStatus()!=null){
            lambdaQueryWrapper.eq(Product::getVerifyStatus,productDTO.getVerifyStatus());
        }
        lambdaQueryWrapper.orderByAsc(Product::getSort);
        return this.page(page,lambdaQueryWrapper);
    }

    /***
     * @param newStatus
     * @param ids
     * @param getNewStatus
     * 商品列表中的商品个体中的上架，新品，推荐三个状态栏更新的公共方法
     * SFunction<Product,?>是个新类型，不懂的话就看看条件构造器，会用就行
     * @Date 2021/11/19 22:16
     * @return boolean
     */
    @Override
    public boolean updateStatus(Integer newStatus, List<Long> ids, SFunction<Product,?> getNewStatus) {
        UpdateWrapper<Product> updateWrapper=new UpdateWrapper<>();
        updateWrapper.lambda()
                .set(getNewStatus,newStatus)
                .in(Product::getId,ids);
        return this.update(updateWrapper);
    }

    @Override
    @Transactional
    public boolean create(ProductTwoDTO productTwoDTO) {
        //1.保存商品基础主表基础信息
        Product product=productTwoDTO;
        boolean result = this.save(product);
        if (result) {
            switch (productTwoDTO.getPromotionType()){
                case 2:
                    //2.会员价格
                    saveOtherList(productTwoDTO.getMemberPriceList(),product.getId(),memberPriceService);
                    break;
                case 3:
                    //3.阶梯价格
                    saveOtherList(productTwoDTO.getProductLadderList(),product.getId(),productLadderService);
                    break;
                case 4:
                    //4.满减价格
                    saveOtherList(productTwoDTO.getProductFullReductionList(),product.getId(),productFullReductionService);
                    break;
            }
            //5.商品属性相关(spu)
            saveOtherList(productTwoDTO.getProductAttributeValueList(),product.getId(),productAttributeValueService);
            //6.sku(商品参数相关)
            saveOtherList(productTwoDTO.getSkuStockList(),product.getId(),skuStockService);
        }
        return result;
    }

    @Override
    @Transactional
    public ProductThreeDTO updateInfoById(Long id) {
        return productMapper.updateInfoById(id);
    }

    @Override
    @Transactional
    public boolean update(ProductTwoDTO productTwoDTO) {
        //1.修改商品基础主表基础信息
        Product product=productTwoDTO;
        boolean result = this.updateById(product);
        if (result) {
            //其他关联表先删除再重新添加
            switch (productTwoDTO.getPromotionType()){
                case 2:
                    //2.会员价格
                    deleteOtherList(product.getId(),memberPriceService);
                    saveOtherList(productTwoDTO.getMemberPriceList(),product.getId(),memberPriceService);
                    break;
                case 3:
                    //3.阶梯价格
                    deleteOtherList(product.getId(),productLadderService);
                    saveOtherList(productTwoDTO.getProductLadderList(),product.getId(),productLadderService);
                    break;
                case 4:
                    //4.满减价格
                    deleteOtherList(product.getId(),productFullReductionService);
                    saveOtherList(productTwoDTO.getProductFullReductionList(),product.getId(),productFullReductionService);
                    break;
            }
            //5.商品属性相关(spu)
            deleteOtherList(product.getId(),productAttributeValueService);
            saveOtherList(productTwoDTO.getProductAttributeValueList(),product.getId(),productAttributeValueService);
            //6.sku(商品参数相关)
            deleteOtherList(product.getId(),skuStockService);
            saveOtherList(productTwoDTO.getSkuStockList(),product.getId(),skuStockService);
        }
        return result;
    }

    /**
     * @param productId
     * @param service
     * 根据商品id删除关联的表数据的公共方法
     * @Date 2021/11/20 23:13
     * @return void
     */
    public void deleteOtherList(Long productId, IService service){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("product_id",productId);
        service.remove(queryWrapper);
    }

    /**
     * @param list
     * @param productId
     * @param service
     * 保存其他表数据的公共方法
     * @Date 2021/11/20 20:35
     * @return boolean
     */
    public boolean saveOtherList(List list, Long productId, IService service)  {
        if(CollectionUtil.isEmpty(list)){
            return false;
        }
        try {
            //通过循环 反射给productId赋值
            for (Object obj : list) {
                Method setProductId = obj.getClass().getMethod("setProductId", Long.class);
                setProductId.invoke(obj,productId);
//                //顺便清空主键id，防止存入数据的时候出bug
//                Method setId = obj.getClass().getMethod("setId", Long.class);
//                setId.invoke(obj,null);
            }
            service.saveBatch(list);
        } catch (Exception e) {
            //抛出自定义异常
            throw new RuntimeException();
        }
        return true;
    }
}

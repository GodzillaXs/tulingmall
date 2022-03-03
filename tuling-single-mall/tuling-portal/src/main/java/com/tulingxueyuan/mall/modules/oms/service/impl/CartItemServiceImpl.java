package com.tulingxueyuan.mall.modules.oms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.common.api.ResultCode;
import com.tulingxueyuan.mall.common.exception.Asserts;
import com.tulingxueyuan.mall.dto.AddCarDTO;
import com.tulingxueyuan.mall.dto.CartItemStockDTO;
import com.tulingxueyuan.mall.modules.oms.entity.CartItem;
import com.tulingxueyuan.mall.modules.oms.mapper.CartItemMapper;
import com.tulingxueyuan.mall.modules.oms.service.CartItemService;
import com.tulingxueyuan.mall.modules.pms.entity.Product;
import com.tulingxueyuan.mall.modules.pms.entity.SkuStock;
import com.tulingxueyuan.mall.modules.pms.service.ProductService;
import com.tulingxueyuan.mall.modules.pms.service.SkuStockService;
import com.tulingxueyuan.mall.modules.ums.entity.Member;
import com.tulingxueyuan.mall.modules.ums.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author fyl
 * @since 2021-11-28
 */
@Service
public class CartItemServiceImpl extends ServiceImpl<CartItemMapper, CartItem> implements CartItemService {

    @Autowired
    MemberService memberService;

    @Autowired
    SkuStockService skuStockService;

    @Autowired
    ProductService productService;

    @Autowired
    CartItemMapper cartItemMapper;

    @Override
    public boolean add(AddCarDTO addCarDTO) {
        //存入商品ID，商品被选中的sku的ID
        CartItem cartItem=new CartItem();
        BeanUtils.copyProperties(addCarDTO,cartItem);
        //存入用户ID,会员名称等
        Member currentMember = memberService.getCurrentMember();
        cartItem.setMemberId(currentMember.getId());
        cartItem.setMemberNickname(currentMember.getNickname());
        //先查询购物车中是否已经添加过相同的商品，如果有就修改商品数量，没有就添加商品
        CartItem item = getCartItem(cartItem.getProductId(), cartItem.getProductSkuId(), cartItem.getMemberId());
        if (item==null){
            //继续添加
            //存入价格，图片等
            SkuStock skuStock = skuStockService.getById(addCarDTO.getProductSkuId());
            if (skuStock==null) Asserts.fail(ResultCode.VALIDATE_FAILED);
            cartItem.setPrice(skuStock.getPrice());
            cartItem.setProductPic(skuStock.getPic());
            cartItem.setProductAttr(skuStock.getSpData());
            cartItem.setProductSkuCode(skuStock.getSkuCode());
            //存入商品名称，商品所属的品牌名称等
            Product product = productService.getById(addCarDTO.getProductId());
            if (product==null) Asserts.fail(ResultCode.VALIDATE_FAILED);
            cartItem.setProductName(product.getName());
            cartItem.setProductBrand(product.getBrandName());
            cartItem.setProductSn(product.getProductSn());
            cartItem.setProductSubTitle(product.getSubTitle());
            cartItem.setProductCategoryId(product.getProductCategoryId());
            cartItem.setCreateDate(LocalDateTime.now());
            cartItem.setModifyDate(LocalDateTime.now());
//            baseMapper.insert(cartItem);
            return save(cartItem);
        }else {
            //修改 数量+1,以及修改时间
            UpdateWrapper<CartItem> updateWrapper=new UpdateWrapper<>();
            updateWrapper.lambda()
                    .set(CartItem::getQuantity,item.getQuantity()+1)
                    .set(CartItem::getModifyDate,LocalDateTime.now())
                    .eq(CartItem::getId,item.getId());
            return update(updateWrapper);
        }
    }

    @Override
    public Integer getCarProductSum() {
        QueryWrapper<CartItem> queryWrapper=new QueryWrapper<>();
        Long id = memberService.getCurrentMember().getId();
        queryWrapper
                .select("sum(quantity) as count") //查询购物车的商品总数
                .lambda().eq(CartItem::getMemberId,id);
        Map<String, Object> map = getMap(queryWrapper);
        if (!CollectionUtil.isEmpty(map)&&map.get("count")!=null){
            return Integer.parseInt(map.get("count").toString());
        }
        return 0;
    }

    @Override
    public List<CartItemStockDTO> getList() {
        Long id = memberService.getCurrentMember().getId();
        return cartItemMapper.getCartItemStockDTOList(id);
    }

    @Override
    public boolean updateQuantity(Long id, Integer quantity) {
        UpdateWrapper<CartItem> updateWrapper=new UpdateWrapper<>();
        updateWrapper.lambda()
                .set(CartItem::getQuantity,quantity)
                .set(CartItem::getModifyDate,LocalDateTime.now())
                .eq(CartItem::getId,id);
        return update(updateWrapper);
    }

    @Override
    public boolean delete(Long id) {
        return removeById(id);
    }

    @Override
    public Integer getQuantity(Long productSkuId, Long productId) {
        Long id = memberService.getCurrentMember().getId();
        CartItem cartItem = getCartItem(productId, productSkuId, id);
        if (cartItem==null){
            return 0;
        }
        Integer quantity = cartItem.getQuantity();
        return quantity;
    }


    public CartItem getCartItem(Long productId,Long skuId,Long memberId ){
        QueryWrapper<CartItem> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(CartItem::getProductId,productId)
                .eq(CartItem::getProductSkuId,skuId)
                .eq(CartItem::getMemberId,memberId);
        return baseMapper.selectOne(queryWrapper);
    }
}

package com.tulingxueyuan.mall.modules.oms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.common.api.ResultCode;
import com.tulingxueyuan.mall.common.exception.ApiException;
import com.tulingxueyuan.mall.common.exception.Asserts;
import com.tulingxueyuan.mall.common.service.RedisService;
import com.tulingxueyuan.mall.component.OuzoTradePay;
import com.tulingxueyuan.mall.dto.*;
import com.tulingxueyuan.mall.modules.oms.entity.CartItem;
import com.tulingxueyuan.mall.modules.oms.entity.Order;
import com.tulingxueyuan.mall.modules.oms.entity.OrderItem;
import com.tulingxueyuan.mall.modules.oms.entity.OrderSetting;
import com.tulingxueyuan.mall.modules.oms.mapper.CartItemMapper;
import com.tulingxueyuan.mall.modules.oms.mapper.OrderMapper;
import com.tulingxueyuan.mall.modules.oms.service.CartItemService;
import com.tulingxueyuan.mall.modules.oms.service.OrderItemService;
import com.tulingxueyuan.mall.modules.oms.service.OrderService;
import com.tulingxueyuan.mall.modules.oms.service.OrderSettingService;
import com.tulingxueyuan.mall.modules.pms.entity.Product;
import com.tulingxueyuan.mall.modules.pms.entity.SkuStock;
import com.tulingxueyuan.mall.modules.pms.service.ProductService;
import com.tulingxueyuan.mall.modules.pms.service.SkuStockService;
import com.tulingxueyuan.mall.modules.ums.entity.Member;
import com.tulingxueyuan.mall.modules.ums.entity.MemberReceiveAddress;
import com.tulingxueyuan.mall.modules.ums.service.MemberReceiveAddressService;
import com.tulingxueyuan.mall.modules.ums.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author fyl
 * @since 2021-11-29
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    CartItemService cartItemService;
    @Autowired
    ProductService productService;
    @Autowired
    MemberReceiveAddressService memberReceiveAddressService;
    @Autowired
    MemberService memberService;
    @Autowired
    SkuStockService skuStockService;
    @Autowired
    CartItemMapper cartItemMapper;
    @Autowired
    RedisService redisService;
    @Value("${redis.key.prefix.orderId}")
    private String REDIS_KEY_PREFIX_ORDER_ID;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderSettingService orderSettingService;
    @Autowired
    OuzoTradePay ouzoTradePay;

    @Override
    public ConfirmOrderDTO generateConfirmOrder(OrderDTO orderDTO) {
        ConfirmOrderDTO confirmOrderDTO=new ConfirmOrderDTO();
        //通过商品页立即购买按钮生成确认订单
        if (orderDTO.getProductId()>0&&orderDTO.getSkuId()>0){
            Product product = productService.getById(orderDTO.getProductId());
            SkuStock skuStock = skuStockService.getById(orderDTO.getSkuId());
            Member member = memberService.getCurrentMember();
            List<CartItem> cartItemList=new ArrayList<>();
            CartItem cartItem=new CartItem();
            cartItem.setQuantity(1);
            cartItem.setProductId(product.getId());
            cartItem.setProductSkuId(skuStock.getId());
            cartItem.setMemberId(member.getId());
            cartItem.setMemberNickname(member.getNickname());
            cartItem.setCreateDate(LocalDateTime.now());
            cartItem.setModifyDate(LocalDateTime.now());
            cartItem.setDeleteStatus(0);
            cartItem.setPrice(skuStock.getPrice());
            cartItem.setProductName(product.getName());
            cartItem.setProductSubTitle(product.getSubTitle());
            cartItem.setProductPic(skuStock.getPic());
            cartItem.setProductSkuCode(skuStock.getSkuCode());
            cartItem.setProductSn(product.getProductSn());
            cartItem.setProductBrand(product.getBrandName());
            cartItem.setProductCategoryId(product.getProductCategoryId());
            cartItem.setProductAttr(skuStock.getSpData());
            cartItemList.add(cartItem);
            confirmOrderDTO.setCartItemList(cartItemList);
        }else {
            //购物车生成确认订单
            List<Long> itemIds=orderDTO.getItemIds();
            if (CollectionUtil.isEmpty(itemIds)){
                Asserts.fail(ResultCode.VALIDATE_FAILED);
            }
            //商品
            List<CartItem> cartItemList = cartItemService.listByIds(itemIds);
            confirmOrderDTO.setCartItemList(cartItemList);
        }
        //计算价格
        calculatePrice(confirmOrderDTO);
        //收货地址
        Member member = memberService.getCurrentMember();
        QueryWrapper<MemberReceiveAddress> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(MemberReceiveAddress::getMemberId,member.getId());
        List<MemberReceiveAddress> memberReceiveAddressList = memberReceiveAddressService.list(queryWrapper);
        confirmOrderDTO.setAddressList(memberReceiveAddressList);

        return confirmOrderDTO;
    }


    /**
     * 计算价格的方法
     * @Date 2021/11/29 16:20
     * @return void
     */
    public void calculatePrice(ConfirmOrderDTO confirmOrderDTO){
        //商品数量
        Integer productTotal=0;
        //总价
        BigDecimal priceTotal=new BigDecimal(0);
        //运费
        BigDecimal freightAmount=new BigDecimal(0);

        for (CartItem cartItem : confirmOrderDTO.getCartItemList()) {
            //计算商品总件数
            productTotal+=cartItem.getQuantity();
            //计算总价
            BigDecimal multiply=cartItem.getPrice().multiply(new BigDecimal(cartItem.getQuantity()));
            priceTotal=priceTotal.add(multiply);
            //运费
            Product product = productService.getById(cartItem.getProductId());
            String[] serviceIdsArray = product.getServiceIds().split(",");
            //如果商品设置了包邮就不要运费，否则每件加十块钱
            if ( ! (serviceIdsArray.length>0 && ArrayUtil.containsAny(serviceIdsArray,3)) ){
                freightAmount=freightAmount.add(new BigDecimal(10*cartItem.getQuantity()));
            }
        }
        confirmOrderDTO.setProductTotal(productTotal);
        confirmOrderDTO.setPriceTotal(priceTotal);
        confirmOrderDTO.setFreightAmount(freightAmount);
        //应付总额
        confirmOrderDTO.setPayAmount(priceTotal.add(freightAmount));
    }

    /**
     * 下单
     * 1. 判断库存（如果没有库存直接提示）
     *      根据购物车选中的商品项id查询所有信息
     * 2.保存订单主表order信息  订单号
     * 3.保存订单详情表order_item( 购物车转移）
     * 4.锁定库存（如果用户超过10分钟没有支付-恢复库存）
     * 5.删除对应购物车
     * 先不考虑并发
     * @param paramDTO
     * @return
     */
    @Override
    @Transactional
    public Order generateOrder(OrderParamDTO paramDTO) {


        Member currentMember = memberService.getCurrentMember();
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();

        List<CartItemStockDTO> cartItemStockByIds=null;

        //从商品详情页的立即购买而来
        if (paramDTO.getProductId()>0&&paramDTO.getSkuId()>0){
            Product product = productService.getById(paramDTO.getProductId());
            SkuStock skuStock = skuStockService.getById(paramDTO.getSkuId());
            cartItemStockByIds=new ArrayList<>();
            CartItemStockDTO cartItemStockDTO=new CartItemStockDTO();
            cartItemStockDTO.setStock(skuStock.getStock()-skuStock.getLockStock());
            cartItemStockDTO.setQuantity(1);
            cartItemStockDTO.setProductId(product.getId());
            cartItemStockDTO.setProductSkuId(skuStock.getId());
            cartItemStockDTO.setMemberId(currentMember.getId());
            cartItemStockDTO.setMemberNickname(currentMember.getNickname());
            cartItemStockDTO.setCreateDate(LocalDateTime.now());
            cartItemStockDTO.setModifyDate(LocalDateTime.now());
            cartItemStockDTO.setDeleteStatus(0);
            cartItemStockDTO.setPrice(skuStock.getPrice());
            cartItemStockDTO.setProductName(product.getName());
            cartItemStockDTO.setProductSubTitle(product.getSubTitle());
            cartItemStockDTO.setProductPic(skuStock.getPic());
            cartItemStockDTO.setProductSkuCode(skuStock.getSkuCode());
            cartItemStockDTO.setProductSn(product.getProductSn());
            cartItemStockDTO.setProductBrand(product.getBrandName());
            cartItemStockDTO.setProductCategoryId(product.getProductCategoryId());
            cartItemStockDTO.setProductAttr(skuStock.getSpData());
            cartItemStockByIds.add(cartItemStockDTO);
        }
        //购物车页面结算而来
        else {
            // 防止用户篡改
            // 根据购物车选中的商品项id 查询真实库存
            queryWrapper.lambda().eq(CartItem::getMemberId,currentMember.getId())
                    .in(CartItem::getId,paramDTO.getItemIds());
            // 根据购物车id查询所有购物车信息
            cartItemStockByIds = cartItemMapper.getCartItemStockByIds(queryWrapper);
        }


        // 1. 判断库存（如果没有库存直接提示）
        // 获取库存不足的商品名称 如果productName为空说明所有选中商品的库存都未超出
        String productName = hasStock(cartItemStockByIds);
        if(StrUtil.isNotEmpty(productName)){
            throw new ApiException("您的手速过慢，"+productName+"已被别人买走");
        }

        // 如果有库存就进行下单操作：
        //2.保存订单主表order信息  订单号
        Order order = newOrder(paramDTO, currentMember, cartItemStockByIds);
        this.save(order);

        // 3.保存订单详情表order_item( 购物车转移）
        List<OrderItem> list=newOrderItems(order,cartItemStockByIds);
        orderItemService.saveBatch(list);

        // 4.锁定库存
        lockStock(cartItemStockByIds);

        //如果是从商品详情页立即购买来的，因为没有往数据库中存CartItem，所以不需要删除，直接返回
        if (paramDTO.getProductId()>0&&paramDTO.getSkuId()>0){
            return order;
        }
        // 5.删除对应购物车
        removeCartItem(cartItemStockByIds);

        return order;
    }

    @Override
    public OrderDetailDTO getOrderDetail(Long orderId) {
        return orderMapper.getOrderDetail(orderId);
    }

    @Override
    public OrderDetailTWODTO getOrderDetailTwo(Long orderId) {
        return orderMapper.getOrderDetailTwo(orderId);
    }

    /**
     * 取消超时订单
     *
     * @Date 2021/12/1 16:21
     * @return void
     */
    @Override
    public void cancelOverTimeOrder() {
        //获取规定的时间
        OrderSetting orderSetting = orderSettingService.getById(1L);
        Integer overtime = orderSetting.getNormalOrderOvertime();
        //获取当前时间减去规定时间后的时间
        LocalDateTime offsetTime = LocalDateTimeUtil.offset(LocalDateTime.now(), -overtime, ChronoUnit.MINUTES);
        //获取规定时间未完成的订单
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Order::getStatus,0) //未支付
                .le(Order::getCreateTime,offsetTime); //超时 ,le是条件 createTime<=offsetTime
        List<Order> orderList = list(queryWrapper);
        if (CollectionUtil.isEmpty(orderList)){
            log.warn("暂无超时订单");
            return;
        }

        //获取超时订单的订单号，需要根据订单号去查询订单项从而归还锁定库存
        List<Long> orderIdList=new ArrayList<>();
        //改变状态，取消
        for (Order order : orderList) {
            order.setStatus(4); //4代表关闭
            order.setModifyTime(LocalDateTime.now());
            orderIdList.add(order.getId());
        }
        updateBatchById(orderList);

        //归还锁定库存
        QueryWrapper<OrderItem> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.lambda().in(OrderItem::getOrderId,orderIdList);
        List<OrderItem> orderItemList = orderItemService.list(queryWrapper1);
        for (OrderItem orderItem : orderItemList) {
            Integer quantity = orderItem.getProductQuantity();
            Long productSkuId = orderItem.getProductSkuId();
            UpdateWrapper<SkuStock> updateWrapper=new UpdateWrapper<>();
            updateWrapper.setSql("lock_stock=lock_stock-"+quantity)
                    .lambda().eq(SkuStock::getId,productSkuId);
            skuStockService.update(updateWrapper);
        }
    }

    @Override
    public Page getListUserOrder(Integer pageNum, Integer pageSize) {
        Page page=new Page(pageNum,pageSize);
        Long id = memberService.getCurrentMember().getId();
        return orderMapper.getOrderPage(page,id);
    }

    @Override
    @Transactional
    public void paySuccess(Long orderId, Integer payType) {
        // 更新订单状态和支付方式，支付时间
        Order order=new Order();
        order.setId(orderId);
        order.setStatus(1);
        order.setPayType(payType);
        order.setPaymentTime(LocalDateTime.now());
        updateById(order);
        //获取订单的商品项列表
        QueryWrapper<OrderItem> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(OrderItem::getOrderId,orderId);
        List<OrderItem> orderItemList = orderItemService.list(queryWrapper);
        // 清除锁定库存，扣除实际库存
        for (OrderItem orderItem : orderItemList) {
            UpdateWrapper<SkuStock> updateWrapper=new UpdateWrapper<>();
            updateWrapper.setSql("lock_stock=lock_stock-"+orderItem.getProductQuantity())
                    .setSql("stock=stock-"+orderItem.getProductQuantity())
                    .lambda().eq(SkuStock::getId,orderItem.getProductSkuId());
            skuStockService.update(updateWrapper);
        }
        // 删除二维码
        String fileName = String.format("/qr-%s.png", orderId);
        String filePath=ouzoTradePay.getStorePath()+fileName;
        //如果filePath在磁盘存在且确实是一个文件，则二维码存在
        if (FileUtil.exist(filePath) && FileUtil.isFile(filePath)){
            //删除二维码图片文件
            FileUtil.del(filePath);
        }
    }


    /**
     * 判断是否有库存
     * @param cartItemStockByIds
     * @return
     */
    public String hasStock(List<CartItemStockDTO> cartItemStockByIds){
        for (CartItemStockDTO cart : cartItemStockByIds) {
            // 如果当前购物车商品的实际库存 小于 实际购买数量 就库存不足
            if(cart.getStock()<cart.getQuantity()){
                return cart.getProductName();
            }
        }
        return null;
    }


    // 创建Order
    public Order newOrder(OrderParamDTO paramDTO,Member currentMember,List<CartItemStockDTO> cartItemStockByIds){
        Order order = new Order();
        order.setCreateTime(LocalDateTime.now());
        order.setModifyTime(LocalDateTime.now());
        order.setMemberId(currentMember.getId());
        order.setMemberUsername(currentMember.getUsername());

        //  计算价格 需要传入ConfirmOrderDTO
        ConfirmOrderDTO confirmOrderDTO = new ConfirmOrderDTO();
        // 1.购物车集合 因为计算价格是根据购物车列表信息来计算的
        List<CartItem> cartItemsList = new ArrayList<>();
        // 循环将CartItemStockDTO 转换为CartItem
        for (CartItemStockDTO cartItem : cartItemStockByIds) {
            cartItemsList.add(cartItem);
        }

        confirmOrderDTO.setCartItemList(cartItemsList);
        // 2、计算价格
        calculatePrice(confirmOrderDTO);

        // 商品总价
        order.setTotalAmount(confirmOrderDTO.getPriceTotal());
        // 应付总金额
        order.setPayAmount(confirmOrderDTO.getPayAmount());
        // 运费金额
        order.setFreightAmount(confirmOrderDTO.getFreightAmount());
        /*
        促销金额待升级
         */
        // 订单来源：0->PC订单；1->app订单
        order.setSourceType(1);
        // 订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
        order.setStatus(0);
        order.setOrderType(0);   //订单类型：0->正常订单；1->秒杀订单
        order.setAutoConfirmDay(15);  // 自动确认收货时间

        // 地址
        QueryWrapper<MemberReceiveAddress> addressQueryWrapper = new QueryWrapper<>();
        addressQueryWrapper.lambda().eq(MemberReceiveAddress::getMemberId,currentMember.getId())
                .eq(MemberReceiveAddress::getId,paramDTO.getMemberReceiveAddressId());
        MemberReceiveAddress address = memberReceiveAddressService.getOne(addressQueryWrapper);
        //收货人姓名
        order.setReceiverName(address.getName());
        // receiver_phone` varchar(32) NOT NULL COMMENT '收货人电话',
        order.setReceiverPhone(address.getPhoneNumber());
        //`receiver_post_code` varchar(32) DEFAULT NULL COMMENT '收货人邮编',
        order.setReceiverPostCode(address.getPostCode());
        //receiver_province` varchar(32) DEFAULT NULL COMMENT '省份/直辖市',
        order.setReceiverProvince(address.getProvince());
        //城市,
        order.setReceiverCity(address.getCity());
        // '区'
        order.setReceiverRegion(address.getRegion());
        //'详细地址'
        order.setReceiverDetailAddress(address.getDetailAddress());
        // '确认收货状态：0->未确认；1->已确认'
        order.setConfirmStatus(0);
        // 生产订单编码
        order.setOrderSn(getOrderSn(order));
        return order;
    }


    /**
     * 生成订单编号：生成规则:8位日期+2位平台号码+6位以上自增id；
     *
     * @return
     */
    public String getOrderSn(Order order){
        // 订单编号
        StringBuilder sb=new StringBuilder();
        //8位日期
        String yyyyMMdd = new SimpleDateFormat("yyyyMMdd").format(new Date());
        sb.append(yyyyMMdd);
        //2位平台号码  1.pc  2.app
        //String.format：参数说
        // 0 代表前面补充零
        // 6 代表补充长度
        // d 代表正数
        String sourceTyp = String.format("%02d", order.getSourceType());
        sb.append(sourceTyp);
        // 6位以上自增id
        // redis incr  原子性
        // 适合并发的自增方式：
        String key= REDIS_KEY_PREFIX_ORDER_ID+ yyyyMMdd;
        Long incr = redisService.incr(key, 1);
        // 拿到当前的6位以上自增 如果小于6位，自动补全0
        if(incr.toString().length()<=6) {
            sb.append(String.format("%06d", redisService.incr(key, 1)));
        }
        else {
            // 如果是6位 不用补0
            sb.append(incr);
        }
        return sb.toString();

    }


    /**
     *   `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
     *   `order_sn` varchar(64) DEFAULT NULL COMMENT '订单编号',
     *   `product_id` bigint(20) DEFAULT NULL,
     *   `product_pic` varchar(500) DEFAULT NULL,
     *   `product_name` varchar(200) DEFAULT NULL,
     *   `product_brand` varchar(200) DEFAULT NULL,
     *   `product_sn` varchar(64) DEFAULT NULL,
     *   `product_price` decimal(10,2) DEFAULT NULL COMMENT '销售价格',
     *   `product_quantity` int(11) DEFAULT NULL COMMENT '购买数量',
     *   `product_sku_id` bigint(20) DEFAULT NULL COMMENT '商品sku编号',
     *   `product_sku_code` varchar(50) DEFAULT NULL COMMENT '商品sku条码',
     *   `product_category_id` bigint(20) DEFAULT NULL COMMENT '商品分类id',
     *   `promotion_name` varchar(200) DEFAULT NULL COMMENT '商品促销名称',
     *   `promotion_amount` decimal(10,2) DEFAULT NULL COMMENT '商品促销分解金额',
     *   `coupon_amount` decimal(10,2) DEFAULT NULL COMMENT '优惠券优惠分解金额',
     *   `integration_amount` decimal(10,2) DEFAULT NULL COMMENT '积分优惠分解金额',
     *   `real_amount` decimal(10,2) DEFAULT NULL COMMENT '该商品经过优惠后的分解金额',
     *   `gift_integration` int(11) DEFAULT '0',
     *   `gift_growth` int(11) DEFAULT '0',
     *   `product_attr` varchar(500) DEFAULT NULL COMMENT '商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]',
     *    生成订单详情
     * @param cartItemStockByIds
     * @return
     */
    private List<OrderItem> newOrderItems(Order order, List<CartItemStockDTO> cartItemStockByIds) {

        List<OrderItem> list=new ArrayList<>();
        for (CartItemStockDTO cartItemStockById : cartItemStockByIds) {
            OrderItem orderItem=new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setOrderSn(order.getOrderSn());
            orderItem.setProductId(cartItemStockById.getProductId());
            orderItem.setProductPic(cartItemStockById.getProductPic());
            orderItem.setProductName(cartItemStockById.getProductName());
            orderItem.setProductBrand(cartItemStockById.getProductBrand());
            orderItem.setProductSn(cartItemStockById.getProductSn());
            orderItem.setProductPrice(cartItemStockById.getPrice());
            orderItem.setProductQuantity(cartItemStockById.getQuantity());
            orderItem.setProductSkuId(cartItemStockById.getProductSkuId());
            orderItem.setProductSkuCode(cartItemStockById.getProductSkuCode());
            orderItem.setProductCategoryId(cartItemStockById.getProductCategoryId());
            orderItem.setProductAttr(cartItemStockById.getProductAttr());
            list.add(orderItem);
        }
        return list;
    }

    /**
     * 锁定库存  把当前的购买数累加到sku lock_stock中
     * @param cartItemStockByIds
     */
    private void lockStock(List<CartItemStockDTO> cartItemStockByIds) {
        for (CartItemStockDTO cart : cartItemStockByIds) {
            UpdateWrapper<SkuStock> updateWrapper = new UpdateWrapper<>();
            updateWrapper.setSql("lock_stock=lock_stock+"+cart.getQuantity())
                    .lambda()
                    .eq(SkuStock::getId,cart.getProductSkuId());

            skuStockService.update(updateWrapper);
        }
    }

    /**
     * 删除生成订单后的购物车应该消失的商品
     * @param cartItemStockByIds
     */
    private void removeCartItem(List<CartItemStockDTO> cartItemStockByIds) {
        // 1.购物车集合
        List<Long> ids = new ArrayList<>();
        for (CartItemStockDTO cartItem : cartItemStockByIds) {
            ids.add(cartItem.getId());
        }
        // 移除购物车信息
        cartItemService.removeByIds(ids);
    }
}

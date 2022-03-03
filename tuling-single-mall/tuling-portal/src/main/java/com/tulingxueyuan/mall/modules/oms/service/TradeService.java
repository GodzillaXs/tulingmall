package com.tulingxueyuan.mall.modules.oms.service;
/**   
 * 为了生成支付二维码而创建的一个工具service
 * @Date 2021/12/2 15:20
 * @return null 
 */
public interface TradeService {
    String tradeQrCode(Long orderId, Integer payType);
}

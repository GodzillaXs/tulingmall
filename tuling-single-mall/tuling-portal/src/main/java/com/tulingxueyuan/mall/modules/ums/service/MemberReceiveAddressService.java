package com.tulingxueyuan.mall.modules.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.modules.ums.entity.MemberReceiveAddress;

import java.util.List;

/**
 * <p>
 * 会员收货地址表 服务类
 * </p>
 *
 * @author fyl
 * @since 2021-11-27
 */
public interface MemberReceiveAddressService extends IService<MemberReceiveAddress> {

    boolean add(MemberReceiveAddress memberReceiveAddress);

    boolean update(MemberReceiveAddress memberReceiveAddress);

    boolean delete(Long id);

    List<MemberReceiveAddress> getList();
}

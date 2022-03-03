package com.tulingxueyuan.mall.modules.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.modules.sms.entity.HomeAdvertise;

import java.util.List;

/**
 * <p>
 * 首页轮播广告表 服务类
 * </p>
 *
 * @author fyl
 * @since 2021-11-26
 */
public interface HomeAdvertiseService extends IService<HomeAdvertise> {

    List<HomeAdvertise> getHomeBanners();
}

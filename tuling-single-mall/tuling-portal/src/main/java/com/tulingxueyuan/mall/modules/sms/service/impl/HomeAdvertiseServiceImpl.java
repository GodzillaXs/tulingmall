package com.tulingxueyuan.mall.modules.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.modules.sms.entity.HomeAdvertise;
import com.tulingxueyuan.mall.modules.sms.mapper.HomeAdvertiseMapper;
import com.tulingxueyuan.mall.modules.sms.service.HomeAdvertiseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页轮播广告表 服务实现类
 * </p>
 *
 * @author fyl
 * @since 2021-11-26
 */
@Service
public class HomeAdvertiseServiceImpl extends ServiceImpl<HomeAdvertiseMapper, HomeAdvertise> implements HomeAdvertiseService {

    @Override
    public List<HomeAdvertise> getHomeBanners() {
        QueryWrapper<HomeAdvertise> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(HomeAdvertise::getType,0)
                .eq(HomeAdvertise::getStatus,1)
                .orderByAsc(HomeAdvertise::getSort);
        return this.list(queryWrapper);
    }
}

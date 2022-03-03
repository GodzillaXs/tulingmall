package com.tulingxueyuan.mall.modules.sms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.dto.HomeGoodsSaleDTO;
import com.tulingxueyuan.mall.modules.sms.entity.HomeCategory;
import com.tulingxueyuan.mall.modules.sms.mapper.HomeCategoryMapper;
import com.tulingxueyuan.mall.modules.sms.service.HomeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fyl
 * @since 2021-11-26
 */
@Service
public class HomeCategoryServiceImpl extends ServiceImpl<HomeCategoryMapper, HomeCategory> implements HomeCategoryService {

    @Autowired
    private HomeCategoryMapper homeCategoryMapper;

    @Override
    public List<HomeGoodsSaleDTO> goodsSale() {
        return homeCategoryMapper.getHomeCategoryWithProduct();
    }
}

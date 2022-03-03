package com.tulingxueyuan.mall.modules.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.dto.HomeGoodsSaleDTO;
import com.tulingxueyuan.mall.modules.sms.entity.HomeCategory;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fyl
 * @since 2021-11-26
 */
public interface HomeCategoryService extends IService<HomeCategory> {

    List<HomeGoodsSaleDTO> goodsSale();
}

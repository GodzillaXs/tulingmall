package com.tulingxueyuan.mall.modules.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tulingxueyuan.mall.dto.HomeGoodsSaleDTO;
import com.tulingxueyuan.mall.modules.sms.entity.HomeCategory;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fyl
 * @since 2021-11-26
 */
public interface HomeCategoryMapper extends BaseMapper<HomeCategory> {

    List<HomeGoodsSaleDTO> getHomeCategoryWithProduct();
}

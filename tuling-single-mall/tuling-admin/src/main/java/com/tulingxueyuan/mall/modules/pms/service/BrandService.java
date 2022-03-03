package com.tulingxueyuan.mall.modules.pms.service;


import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.modules.pms.entity.Brand;

import java.util.List;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author fyl
 * @since 2021-11-15
 */
public interface BrandService extends IService<Brand> {

    Page list(String keyword, Integer pageNum, Integer pageSize);

    boolean creat(Brand brand);


    boolean updateStatus(List<Long> ids, Integer showStatus, SFunction<Brand,?> getShowStatus);

}

package com.tulingxueyuan.mall.modules.pms.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.modules.pms.entity.Brand;
import com.tulingxueyuan.mall.modules.pms.mapper.BrandMapper;
import com.tulingxueyuan.mall.modules.pms.service.BrandService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author fyl
 * @since 2021-11-15
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    @Override
    public Page list(String keyword, Integer pageNum, Integer pageSize) {
        Page page=new Page(pageNum,pageSize);
        QueryWrapper<Brand> queryWrapper= new QueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)){
            queryWrapper.lambda().like(Brand::getName,keyword);
        }
        queryWrapper.lambda().orderByAsc(Brand::getSort);
        return this.page(page,queryWrapper);
    }

    @Override
    public boolean creat(Brand brand) {
        return this.save(brand);
    }

    @Override
    public boolean updateStatus(List<Long> ids, Integer showStatus, SFunction<Brand, ?> getShowStatus) {
        UpdateWrapper<Brand> updateWrapper=new UpdateWrapper<>();
        updateWrapper.lambda()
                .set(getShowStatus,showStatus)
                .in(Brand::getId,ids);
        return this.update(updateWrapper);
    }

}

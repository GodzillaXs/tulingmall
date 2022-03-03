package com.tulingxueyuan.mall.modules.ums.service.impl;

import com.alibaba.druid.wall.violation.ErrorCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.common.api.ResultCode;
import com.tulingxueyuan.mall.common.exception.Asserts;
import com.tulingxueyuan.mall.modules.ums.entity.Member;
import com.tulingxueyuan.mall.modules.ums.entity.MemberReceiveAddress;
import com.tulingxueyuan.mall.modules.ums.mapper.MemberReceiveAddressMapper;
import com.tulingxueyuan.mall.modules.ums.service.MemberReceiveAddressService;
import com.tulingxueyuan.mall.modules.ums.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 会员收货地址表 服务实现类
 * </p>
 *
 * @author fyl
 * @since 2021-11-27
 */
@Service
public class MemberReceiveAddressServiceImpl extends ServiceImpl<MemberReceiveAddressMapper, MemberReceiveAddress> implements MemberReceiveAddressService {

    @Autowired
    MemberService memberService;

    @Override
    public boolean add(MemberReceiveAddress memberReceiveAddress) {
        Long id = memberService.getCurrentMember().getId();
        memberReceiveAddress.setMemberId(id);
        return this.save(memberReceiveAddress);
    }

    @Override
    public boolean update(MemberReceiveAddress memberReceiveAddress) {
        return this.updateById(memberReceiveAddress);
    }

    @Override
    public boolean delete(Long id) {
        return this.removeById(id);
    }

    @Override
    public List<MemberReceiveAddress> getList() {
        Long id = memberService.getCurrentMember().getId();
        QueryWrapper<MemberReceiveAddress> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(MemberReceiveAddress::getMemberId,id);
        return list(queryWrapper);
    }
}

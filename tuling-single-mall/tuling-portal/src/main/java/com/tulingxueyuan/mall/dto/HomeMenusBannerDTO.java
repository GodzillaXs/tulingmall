package com.tulingxueyuan.mall.dto;

import com.tulingxueyuan.mall.modules.sms.entity.HomeAdvertise;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/11/26 14:19
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "首页导航栏和banner组合数据传输对象",description = "在HomeMenusDTO基础上再次封装了一层的DTO")
public class HomeMenusBannerDTO {
    //首页菜单导航栏数据
    private List<HomeMenusDTO> homeMenusDTOList;
    //广告横幅数据
    private List<HomeAdvertise> homeAdvertiseList;
}

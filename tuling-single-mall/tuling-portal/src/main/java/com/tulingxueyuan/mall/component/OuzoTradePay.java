package com.tulingxueyuan.mall.component;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/12/2 19:52
 * @Version 1.0
 */
@Data
@EqualsAndHashCode
@ApiModel(value = "与支付宝之间的数据路径",description = "与支付宝之间的数据路径")
@Component
@ConfigurationProperties("ouzo.trade.qrcode")
public class OuzoTradePay {
    @ApiModelProperty(value = "支付成功后的回调方法请求路径")
    private String paySuccessCallBack;
    @ApiModelProperty(value = "二维码生成后的磁盘保存路径")
    private String storePath;
    @ApiModelProperty(value = "对应磁盘物理路径的映射路径")
    private String httpBasePath;

}

package com.tulingxueyuan.mall;

import com.tulingxueyuan.mall.modules.oms.entity.Order;
import com.tulingxueyuan.mall.modules.oms.service.OrderService;
import com.tulingxueyuan.mall.modules.oms.service.TradeService;
import com.tulingxueyuan.mall.modules.oms.service.impl.OrderServiceImpl;
import com.tulingxueyuan.mall.modules.sms.entity.HomeAdvertise;
import com.tulingxueyuan.mall.modules.sms.service.HomeAdvertiseService;
import com.tulingxueyuan.mall.modules.ums.entity.Member;
import com.tulingxueyuan.mall.modules.ums.service.MemberCacheService;
import com.tulingxueyuan.mall.modules.ums.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/11/26 14:54
 * @Version 1.0
 */
@SpringBootTest
public class PorTest {
    @Autowired
    private HomeAdvertiseService homeAdvertiseService;

    @Test
    void test1(){
        List<HomeAdvertise> homeBanners = homeAdvertiseService.getHomeBanners();
        System.out.println(homeBanners);
    }

    @Test
    void test2(){
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("localDateTime+"+localDateTime);
        Date date = new Date();
        System.out.println("date+"+date);
    }

    @Autowired
    MemberCacheService memberCacheService;

    @Autowired
    MemberService memberService;

    @Test
    void test3(){
//        Member ouzo = memberCacheService.getMember("Ouzo");
//        Member ouzo = memberService.getMemberByUsername("Ouzo");
        Member ouzo = memberService.login("Ouzo", "123456");
        System.out.println("Ouzo的账号:"+ouzo);
    }

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void string3() {
////        测试localDate以及Date在redis的存入取出
        LocalDateTime now = LocalDateTime.now();
        redisTemplate.opsForValue().set("demo1:key",now);
//        redisTemplate.opsForValue().set("demo1:key",new Date());
//        测试对象在redis的存入取出
//        DemoTest demoTest = new DemoTest();
//        demoTest.setId(8820);
//        demoTest.setName("卡卡西");
//        redisTemplate.opsForValue().set("demo1:key",demoTest);
        Object king = redisTemplate.opsForValue().get("demo1:key");
        System.out.println(king);
    }

    @Autowired
    OrderServiceImpl orderService;

    @Test
    void test4(){
        Order order=new Order();
        order.setSourceType(1);
        String orderSn = orderService.getOrderSn(order);
        System.out.println("-------orderSn="+orderSn);
    }

    @Autowired
    TradeService tradeService;

    @Test
    void test5(){
        tradeService.tradeQrCode(33L,1);
    }

}

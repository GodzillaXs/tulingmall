import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.StartApp;
import com.tulingxueyuan.mall.dto.ProductThreeDTO;
import com.tulingxueyuan.mall.modules.pms.mapper.ProductAttributeCategoryMapper;
import com.tulingxueyuan.mall.modules.pms.service.BrandService;
import com.tulingxueyuan.mall.modules.pms.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sound.midi.Soundbank;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/11/19 13:34
 * @Version 1.0
 */
@SpringBootTest(classes = {StartApp.class})
public class ProductTest {
    @Autowired
    ProductAttributeCategoryMapper productAttributeCategoryMapper;

    @Test
    void test1(){
        System.out.println("so-------");
        System.out.println(productAttributeCategoryMapper.listWithAttr());
    }

    @Autowired
    BrandService brandService;

    @Test
    void test2(){
        Page list = brandService.list(null, 1, 5);
        System.out.println("size:-----------"+list.getSize());
        System.out.println("size:-----------"+list.getCurrent());
        System.out.println("size:-----------"+list.getTotal());
    }

    @Autowired
    ProductService productService;

    @Test
    void test3(){
        ProductThreeDTO productThreeDTO = productService.updateInfoById((long) 37);
        System.out.println("toString:-----------"+productThreeDTO.toString());
        System.out.println("-------------------------间隔37号数据-----------:-----------");
        System.out.println("getProductAttributeValueList():-----------"+productThreeDTO.getProductAttributeValueList());
    }

    @Test
    void test4(){
        //用正则表达式验证字符串是不是纯数字
        Pattern pattern = Pattern.compile("[0-9]*");
        if(pattern.matcher("1234").matches()){
            System.out.println("123456789");
        }
    }
}

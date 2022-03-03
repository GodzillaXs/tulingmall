package com.boot;

/**
 * @Description:
 * @Author 86131
 * @Date 2021/11/14 16:52
 * @Version 1.0
 */

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个项目是在学习图灵商城时创建
 * 用来测试springboot框架下使用mybatisplus的代码生成器功能
 * 以后可以把这个作为固定的代码生成文件在项目中使用，只需要修改一些表名和路径即可
 * @Author 86131
 * @Date 2021/11/12 18:19
 * @Version 1.0
 */
public class MyGenerator {
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //System.getProperty("user.dir")获得当前项目所在路径,但是不包括项目名fyl-mp-generator
        // 比如此项目的路径是C:/1501/javaei/javanewtest/Idea/TuLing/tuling-single-mall，但是我们肯定是要让生成的文件存放在项目下面的，所以得再在路径加上项目名
        String projectPath = System.getProperty("user.dir")+"/fyl-mp-generator";
        System.out.println(projectPath);
        //生成代码的存放位置
        gc.setOutputDir(projectPath + "/src/main/java");
        //作者
        gc.setAuthor("fyl");
        // 代码生成是不是要打开所在文件夹
        gc.setOpen(false);
        // 在mapper.xml文件中生成一个基础的<ResultMap> 映射所有的字段
        gc.setBaseResultMap(true);
        // 同文件生成覆盖
        gc.setFileOverride(true);
        // 实体类名：直接用表名 (%s=表名)
        gc.setEntityName("%s");
        // mapper接口名
        gc.setMapperName("%sMapper");
        // mapper.xml 文件名
        gc.setXmlName("%sMapper");
        // 业务逻辑类接口名
        gc.setServiceName("%sService");
        // 业务逻辑类实现类名
        gc.setServiceImplName("%sServiceImpl");
        // *生成Swagger2注解,目前还是一知半解，这个里面的注解可以用来当注释
        gc.setSwagger2(true);
        // 将全局配置设置到AutoGenerator
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://192.168.23.9:3306/tuling");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("1234");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        //模块名
        pc.setModuleName("oms");
        //包名
//        pc.setParent("com.boot");
        pc.setParent("com.tulingxueyuan.mall.modules");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //类名的生成策略：下划线转驼峰命名（NamingStrategy.underline_to_camel）
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //属性名的生成策略：下划线转驼峰命名
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //是否生成使用Lombok的bean
        strategy.setEntityLombokModel(true);
        //是否生成使用rest风格的controller
        strategy.setRestControllerStyle(true);
        //声明用来生成代码的表，多表之间用,间隔
        strategy.setInclude("oms_order_setting");
//        strategy.setInclude("tbl_employee","tbl_user");
        //声明用于生成代码的表的表名前缀,数据库中凡是带了此前缀的表都会生成对应的类，如果数据库内的表前缀都相同，建议使用此方法
//        strategy.setLikeTable(new LikeTable("pms_"));
        //声明表前缀，使用后在生成类名的时候就不会再加上这个前缀
        strategy.setTablePrefix("oms_");
        //生成的controller中的RequestMapper中的映射路径是否使用驼峰路径：如："/fyl/tblEmployee",true则使用驼峰，false则使用下划线
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);

        //-------------------start-----------------
        // 以下这堆代码的唯一目的就是为了设置生成的mapper映射xml文件的存放位置，因为系统默认把xml和Mapper接口放一块去了
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 如果模板引擎是 velocity（默认）
        String templatePath = "/templates/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        // 配置模板,配合自定义配置使用，取消默认的xml文件存放位置
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);
        //--------------------end-----------------

        //执行，生成代码
        mpg.execute();
    }
}
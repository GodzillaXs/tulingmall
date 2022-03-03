package com.boot;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class TulingTestApplicationTests {

	@Test
	void LoadOss() {
		// yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
		String endpoint = "https://oss-cn-shenzhen.aliyuncs.com";
		// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
		String accessKeyId = "LTAI5tBzGtgBB5fATqx8AQMQ";
		String accessKeySecret = "4URzSduTkBTW6u9nqoURtFfuapsUQU";

		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

		// 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("C:\\Users\\86131\\Pictures\\Saved Pictures\\alp.png");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//个人新加内容：格式化日期并动态创建日期路径，为了便于管理，可以在上传文件前加上一个日期文件夹
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dir = simpleDateFormat.format(new Date())+"/";

		// 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
		ossClient.putObject("tulingmall-fyl", dir+"alp.png", inputStream);

		// 关闭OSSClient。
		ossClient.shutdown();

		System.out.println("-------------------上传成功-------------------");
	}

}

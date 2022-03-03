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

		System.out.println("-------------------上传成功-------------------");
	}

}

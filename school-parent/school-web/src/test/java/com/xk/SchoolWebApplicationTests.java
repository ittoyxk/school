package com.xk;

import com.xk.service.SmsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class SchoolWebApplicationTests {

	@Autowired
	SmsService smsService;

	@Test
	public void contextLoads() throws InterruptedException
	{

		long l = System.currentTimeMillis();
		for (int i=0;i<10;i++)
		{
//			smsService.sendSMS("","");
		}

		long l1 = System.currentTimeMillis();
		System.out.println(l1-l);

		System.out.println("sss::aaaaa".substring("sss::aaaaa".indexOf("::")+2));
	
	}

}

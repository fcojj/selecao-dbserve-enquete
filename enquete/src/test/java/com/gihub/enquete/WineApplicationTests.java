package com.gihub.enquete;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.enquete.EnqueteApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EnqueteApplication.class)
@WebAppConfiguration
public class WineApplicationTests {

	@Test
	public void contextLoads() {
	}

}

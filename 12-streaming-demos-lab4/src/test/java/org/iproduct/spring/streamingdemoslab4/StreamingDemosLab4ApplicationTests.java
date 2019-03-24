package org.iproduct.spring.streamingdemoslab4;

import org.iproduct.spring.streamingdemoslab4.service.QuotesGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StreamingDemosLab4ApplicationTests {
	@Autowired
	QuotesGenerator generator;

	@Test
	public void contextLoads() throws InterruptedException {
		generator.getQuotesStream(Duration.ofSeconds(1))
				.subscribe(System.out::println);

		Thread.sleep(12000);
}

}

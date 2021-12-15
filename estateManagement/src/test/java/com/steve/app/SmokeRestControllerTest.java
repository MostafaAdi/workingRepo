package com.steve.app;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmokeRestControllerTest {

	@Autowired
	private HomeController homeController;
	
	@Test
    void contextLoads() {
        assertThat(homeController).isNotNull();
    }
}

package com.email.Sys;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("Requires external Postgres/Redis infraestructure not available in CI")
class SysApplicationTests {

	@Test
	void contextLoads() {
	}

}

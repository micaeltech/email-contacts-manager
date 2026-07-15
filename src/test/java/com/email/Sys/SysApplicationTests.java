package com.email.Sys;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disable("Requires external Postgres/Redis infraestructure not available in CI")
class SysApplicationTests {

	@Test
	void contextLoads() {
	}

}

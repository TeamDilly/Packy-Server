package com.dilly.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;


import jakarta.transaction.Transactional;

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
	properties = "spring.profiles.active=test"
)
@TestPropertySource(locations = {"classpath:application-test.yml", "classpath:data.sql"})
@Transactional
public abstract class IntegrationTestSupport {

	@LocalServerPort
	protected int port;

}

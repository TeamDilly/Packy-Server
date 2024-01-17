package com.dilly.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.dilly.admin.api.AdminController;
import com.dilly.admin.application.AdminService;
import com.dilly.global.exception.ErrorCodeController;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(
	controllers = {
		AdminController.class,
		ErrorCodeController.class
	}
)
@AutoConfigureRestDocs
public abstract class ControllerTestSupport {
	static {
		System.setProperty("spring.config.name", "application-test");
	}

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@MockBean
	protected AdminService adminService;
}

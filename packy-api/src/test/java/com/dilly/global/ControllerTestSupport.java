package com.dilly.global;

import com.dilly.admin.api.AdminController;
import com.dilly.admin.application.AdminService;
import com.dilly.application.YoutubeService;
import com.dilly.gift.api.GiftBoxController;
import com.dilly.gift.api.GiftController;
import com.dilly.gift.application.GiftBoxService;
import com.dilly.gift.application.GiftService;
import com.dilly.mypage.api.MyPageController;
import com.dilly.mypage.application.MyPageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(
	controllers = {
		AdminController.class,
		GiftBoxController.class,
		GiftController.class,
        MyPageController.class
	}
)
public abstract class ControllerTestSupport {
	static {
		System.setProperty("spring.config.name", "application-test");
	}

	protected final String baseUrl = "/api/v1";

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@MockBean
	protected AdminService adminService;

	@MockBean
	protected GiftBoxService giftBoxService;

    @MockBean
    protected MyPageService myPageService;

	@MockBean
	protected YoutubeService youtubeService;

	@MockBean
	protected GiftService giftService;
}

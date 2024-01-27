package com.dilly.global;

import com.dilly.admin.application.AdminService;
import com.dilly.gift.application.GiftService;
import com.dilly.gift.dao.BoxRepository;
import com.dilly.gift.dao.EnvelopeRepository;
import com.dilly.gift.dao.GiftBoxRepository;
import com.dilly.gift.dao.LetterRepository;
import com.dilly.gift.dao.MemberGiftBoxRepository;
import com.dilly.gift.dao.MusicRepository;
import com.dilly.gift.dao.PhotoRepository;
import com.dilly.gift.dao.ProfileImageRepository;
import com.dilly.member.MemberRepository;
import com.dilly.mypage.application.MyPageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = "spring.profiles.active=test"
)
@TestPropertySource(locations = {"classpath:application-test.yml"})
@Transactional
public abstract class IntegrationTestSupport {

    @LocalServerPort
    protected int port;

    @Autowired
    protected AdminService adminService;

    @Autowired
    protected GiftService giftService;

    @Autowired
    protected MyPageService myPageService;

    @Autowired
    protected ProfileImageRepository profileImageRepository;

    @Autowired
    protected BoxRepository boxRepository;

    @Autowired
    protected EnvelopeRepository envelopeRepository;

    @Autowired
    protected LetterRepository letterRepository;

    @Autowired
    protected MusicRepository musicRepository;

    @Autowired
    protected PhotoRepository photoRepository;

    @Autowired
    protected GiftBoxRepository giftBoxRepository;

    @Autowired
    protected MemberGiftBoxRepository memberGiftBoxRepository;

    @Autowired
    protected MemberRepository memberRepository;
}

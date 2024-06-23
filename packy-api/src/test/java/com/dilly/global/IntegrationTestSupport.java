package com.dilly.global;

import com.dilly.admin.adaptor.SettingReader;
import com.dilly.admin.application.AdminService;
import com.dilly.gift.adaptor.BoxReader;
import com.dilly.gift.adaptor.BoxWriter;
import com.dilly.gift.adaptor.EnvelopeReader;
import com.dilly.gift.adaptor.GiftBoxReader;
import com.dilly.gift.adaptor.GiftBoxStickerReader;
import com.dilly.gift.adaptor.GiftBoxWriter;
import com.dilly.gift.adaptor.LetterReader;
import com.dilly.gift.adaptor.MusicReader;
import com.dilly.gift.adaptor.PhotoReader;
import com.dilly.gift.adaptor.PhotoWriter;
import com.dilly.gift.adaptor.ReceiverReader;
import com.dilly.gift.adaptor.ReceiverWriter;
import com.dilly.gift.application.GiftBoxService;
import com.dilly.member.adaptor.MemberReader;
import com.dilly.member.adaptor.MemberWriter;
import com.dilly.member.adaptor.ProfileImageReader;
import com.dilly.member.application.MyPageService;
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
    protected GiftBoxService giftBoxService;

    @Autowired
    protected MyPageService myPageService;

    @Autowired
    protected ProfileImageReader profileImageReader;

    @Autowired
    protected BoxWriter boxWriter;

    @Autowired
    protected BoxReader boxReader;

    @Autowired
    protected EnvelopeReader envelopeReader;

    @Autowired
    protected LetterReader letterReader;

    @Autowired
    protected MusicReader musicReader;

    @Autowired
    protected PhotoWriter photoWriter;

    @Autowired
    protected PhotoReader photoReader;

    @Autowired
    protected GiftBoxStickerReader giftBoxStickerReader;

    @Autowired
    protected GiftBoxWriter giftBoxWriter;

    @Autowired
    protected GiftBoxReader giftBoxReader;

    @Autowired
    protected ReceiverWriter receiverWriter;

    @Autowired
    protected ReceiverReader receiverReader;

    @Autowired
    protected ReceiverReader receiver;

    @Autowired
    protected MemberReader memberReader;

    @Autowired
    protected MemberWriter memberWriter;

    @Autowired
    protected SettingReader settingReader;
}

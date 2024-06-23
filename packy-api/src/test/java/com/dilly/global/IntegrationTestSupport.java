package com.dilly.global;

import com.dilly.admin.application.AdminService;
import com.dilly.admin.dao.SettingRepository;
import com.dilly.application.FileService;
import com.dilly.gift.adaptor.BoxReader;
import com.dilly.gift.adaptor.GiftBoxReader;
import com.dilly.gift.adaptor.GiftBoxStickerReader;
import com.dilly.gift.adaptor.GiftBoxWriter;
import com.dilly.gift.adaptor.LetterReader;
import com.dilly.gift.adaptor.PhotoReader;
import com.dilly.gift.adaptor.PhotoWriter;
import com.dilly.gift.adaptor.ReceiverReader;
import com.dilly.gift.adaptor.ReceiverWriter;
import com.dilly.gift.application.GiftBoxService;
import com.dilly.gift.dao.BoxRepository;
import com.dilly.gift.dao.EnvelopeRepository;
import com.dilly.gift.dao.GiftBoxRepository;
import com.dilly.gift.dao.GiftBoxStickerRepository;
import com.dilly.gift.dao.LetterRepository;
import com.dilly.gift.dao.MusicRepository;
import com.dilly.gift.dao.PhotoRepository;
import com.dilly.gift.dao.ProfileImageRepository;
import com.dilly.gift.dao.ReceiverRepository;
import com.dilly.gift.dao.StickerRepository;
import com.dilly.member.MemberRepository;
import com.dilly.member.adaptor.MemberReader;
import com.dilly.member.adaptor.MemberWriter;
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

    // TODO: Repository 대신 Reader, Writer 사용

    @Autowired
    protected ProfileImageRepository profileImageRepository;

    @Autowired
    protected BoxRepository boxRepository;

    @Autowired
    protected BoxReader boxReader;

    @Autowired
    protected EnvelopeRepository envelopeRepository;

    @Autowired
    protected LetterRepository letterRepository;

    @Autowired
    protected LetterReader letterReader;

    @Autowired
    protected MusicRepository musicRepository;

    @Autowired
    protected PhotoRepository photoRepository;

    @Autowired
    protected PhotoWriter photoWriter;

    @Autowired
    protected PhotoReader photoReader;

    @Autowired
    protected StickerRepository stickerRepository;

    @Autowired
    protected GiftBoxStickerRepository giftBoxStickerRepository;

    @Autowired
    protected GiftBoxStickerReader giftBoxStickerReader;

    @Autowired
    protected GiftBoxRepository giftBoxRepository;

    @Autowired
    protected GiftBoxWriter giftBoxWriter;

    @Autowired
    protected GiftBoxReader giftBoxReader;

    @Autowired
    protected ReceiverWriter receiverWriter;

    @Autowired
    protected ReceiverReader receiverReader;

    @Autowired
    protected ReceiverRepository receiverRepository;

    @Autowired
    protected ReceiverReader receiver;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected MemberReader memberReader;

    @Autowired
    protected MemberWriter memberWriter;

    @Autowired
    protected SettingRepository settingRepository;
}

package com.dilly.global;

import com.dilly.admin.application.AdminService;
import com.dilly.admin.dao.SettingRepository;
import com.dilly.gift.adaptor.GiftBoxWriter;
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
import com.dilly.gift.domain.Box;
import com.dilly.gift.domain.Photo;
import com.dilly.gift.domain.gift.Gift;
import com.dilly.gift.domain.gift.GiftType;
import com.dilly.gift.domain.giftbox.DeliverStatus;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.gift.domain.letter.Envelope;
import com.dilly.gift.domain.letter.Letter;
import com.dilly.gift.domain.receiver.Receiver;
import com.dilly.gift.domain.sticker.GiftBoxSticker;
import com.dilly.gift.domain.sticker.Sticker;
import com.dilly.member.MemberRepository;
import com.dilly.member.application.MyPageService;
import com.dilly.member.domain.Member;
import jakarta.transaction.Transactional;
import java.util.List;
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
    protected PhotoWriter photoWriter;

    @Autowired
    protected StickerRepository stickerRepository;

    @Autowired
    protected GiftBoxStickerRepository giftBoxStickerRepository;

    @Autowired
    protected GiftBoxRepository giftBoxRepository;

    @Autowired
    protected GiftBoxWriter giftBoxWriter;

    @Autowired
    protected ReceiverWriter receiverWriter;

    @Autowired
    protected ReceiverRepository receiverRepository;

    @Autowired
    protected ReceiverReader receiver;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected SettingRepository settingRepository;

    protected GiftBox createMockGiftBoxWithGift(Member member, DeliverStatus deliverStatus) {
        Box box = boxRepository.findById(1L).orElseThrow();
        Envelope envelope = envelopeRepository.findById(1L).orElseThrow();
        Letter letter = letterRepository.save(Letter.of("test", envelope));
        Gift gift = Gift.of(GiftType.PHOTO, "www.test.com");

        GiftBox giftBox = giftBoxRepository.save(GiftBox.builder()
            .box(box)
            .letter(letter)
            .gift(gift)
            .sender(member)
            .name("test")
            .youtubeUrl("www.youtube.com")
            .senderName("sender")
            .receiverName("receiver")
            .deliverStatus(deliverStatus)
            .build());

        photoRepository.save(Photo.of(giftBox, "www.test.com", "test", 1));
        for (long i = 1; i <= 2; i++) {
            Sticker sticker = stickerRepository.findById(i).orElseThrow();
            giftBoxStickerRepository.save(GiftBoxSticker.of(giftBox, sticker, (int) i));
        }

        return giftBox;
    }

    protected GiftBox createMockGiftBoxWithoutGift(Member member, DeliverStatus deliverStatus) {
        Box box = boxRepository.findById(1L).orElseThrow();
        Envelope envelope = envelopeRepository.findById(1L).orElseThrow();
        Letter letter = letterRepository.save(Letter.of("test", envelope));

        GiftBox giftBox = giftBoxRepository.save(GiftBox.builder()
            .box(box)
            .letter(letter)
            .sender(member)
            .name("test")
            .youtubeUrl("www.youtube.com")
            .senderName("sender")
            .receiverName("receiver")
            .deliverStatus(deliverStatus)
            .build());

        photoRepository.save(Photo.of(giftBox, "www.test.com", "test", 1));
        for (long i = 1; i <= 2; i++) {
            Sticker sticker = stickerRepository.findById(i).orElseThrow();
            giftBoxStickerRepository.save(GiftBoxSticker.of(giftBox, sticker, (int) i));
        }

        return giftBox;
    }

    protected void openGiftBox(Member member, GiftBox giftBox) {
        List<Member> receivers = receiver.findByGiftBox(giftBox).stream()
            .map(Receiver::getMember)
            .toList();

        if (!receivers.contains(member)) {
            receiverWriter.save(member, giftBox);
        }
    }
}

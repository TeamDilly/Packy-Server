package com.dilly.global;

import com.dilly.gift.dao.BoxRepository;
import com.dilly.gift.dao.EnvelopeRepository;
import com.dilly.gift.dao.GiftBoxRepository;
import com.dilly.gift.dao.GiftBoxStickerRepository;
import com.dilly.gift.dao.LetterRepository;
import com.dilly.gift.dao.PhotoRepository;
import com.dilly.gift.dao.ProfileImageRepository;
import com.dilly.gift.dao.StickerRepository;
import com.dilly.gift.dao.querydsl.GiftBoxQueryRepository;
import com.dilly.gift.domain.Box;
import com.dilly.gift.domain.Envelope;
import com.dilly.gift.domain.Gift;
import com.dilly.gift.domain.GiftBox;
import com.dilly.gift.domain.GiftBoxSticker;
import com.dilly.gift.domain.GiftType;
import com.dilly.gift.domain.Letter;
import com.dilly.gift.domain.Photo;
import com.dilly.gift.domain.Sticker;
import com.dilly.global.config.P6SpyFormatter;
import com.dilly.global.config.TestQueryDslConfig;
import com.dilly.member.MemberRepository;
import com.dilly.member.domain.Member;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(locations = {"classpath:application-test.yml"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({
    TestQueryDslConfig.class,
    P6SpyFormatter.class
})
public abstract class RepositoryTestSupport {

    @Autowired
    protected GiftBoxRepository giftBoxRepository;

    @Autowired
    protected GiftBoxQueryRepository giftBoxQueryRepository;

    @Autowired
    protected BoxRepository boxRepository;

    @Autowired
    protected EnvelopeRepository envelopeRepository;

    @Autowired
    protected LetterRepository letterRepository;

    @Autowired
    protected PhotoRepository photoRepository;

    @Autowired
    protected StickerRepository stickerRepository;

    @Autowired
    protected GiftBoxStickerRepository giftBoxStickerRepository;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected ProfileImageRepository profileImageRepository;

    protected void createMockGiftBoxWithGift(Member member) {
        Box box = boxRepository.findById(1L).orElseThrow();
        Envelope envelope = envelopeRepository.findById(1L).orElseThrow();
        Letter letter = letterRepository.save(Letter.of("test", envelope));

        GiftBox giftBox = giftBoxRepository.save(GiftBox.builder()
            .uuid(UUID.randomUUID().toString())
            .name("test")
            .sender(member)
            .box(box)
            .letter(letter)
            .youtubeUrl("www.test.com")
            .senderName("테스트유저")
            .receiverName("테스트유저2")
            .gift(Gift.of(GiftType.PHOTO, "www.test.com"))
            .build());

        photoRepository.save(Photo.of(giftBox, "www.test.com", "test", 1));
        for (long i = 1; i <= 2; i++) {
            Sticker sticker = stickerRepository.findById(i).orElseThrow();
            giftBoxStickerRepository.save(GiftBoxSticker.of(giftBox, sticker, (int) i));
        }

    }
}

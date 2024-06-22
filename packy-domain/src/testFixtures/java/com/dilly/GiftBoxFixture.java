package com.dilly;

import com.dilly.gift.domain.Box;
import com.dilly.gift.domain.gift.Gift;
import com.dilly.gift.domain.gift.GiftType;
import com.dilly.gift.domain.giftbox.DeliverStatus;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.gift.domain.letter.Envelope;
import com.dilly.gift.domain.letter.EnvelopePaper;
import com.dilly.gift.domain.letter.Letter;
import com.dilly.gift.domain.letter.LetterPaper;
import com.dilly.member.domain.Member;

public class GiftBoxFixture {

    public static GiftBox sendGiftBoxFixtureWithGift(Member member) {
        return GiftBox.builder()
            .box(createBox())
            .letter(createLetter())
            .gift(createGift())
            .sender(member)
            .name("선물박스 이름")
            .youtubeUrl("www.youtube.com")
            .senderName("보내는 사람")
            .receiverName("받는 사람")
            .deliverStatus(DeliverStatus.DELIVERED)
            .build();
    }

    public static GiftBox sendGiftBoxFixtureWithoutGift(Member member) {
        return GiftBox.builder()
            .box(createBox())
            .letter(createLetter())
            .sender(member)
            .name("선물박스 이름")
            .youtubeUrl("www.youtube.com")
            .senderName("보내는 사람")
            .receiverName("받는 사람")
            .deliverStatus(DeliverStatus.DELIVERED)
            .build();
    }

    // TODO: data.sql 의존성 제거 후 www.test.com으로 변경
    private static Box createBox() {
        return Box.builder()
            .id(1L)
            .sequence(1L)
            .normalImgUrl("www.example.com")
            .smallImgUrl("www.example.com")
            .setImgUrl("www.example.com")
            .topImgUrl("www.example.com")
            .kakaoMessageImgUrl("www.example.com")
            .lottieMakeUrl("www.example.com")
            .lottieArrivedUrl("www.example.com")
            .build();
    }

    private static Letter createLetter() {
        return Letter.builder()
            .id(1L)
            .content("test")
            .envelope(createEnvelope())
            .build();
    }

    private static Envelope createEnvelope() {
        return Envelope.builder()
            .id(1L)
            .sequence(1L)
            .envelopePaper(EnvelopePaper.of("AAAAA", 100))
            .letterPaper(LetterPaper.of("BBBBB", 100))
            .imgUrl("www.test.com")
            .build();
    }

    private static Gift createGift() {
        return Gift.of(GiftType.PHOTO, "www.test.com");
    }
}

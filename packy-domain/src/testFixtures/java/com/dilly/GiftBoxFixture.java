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

    public static GiftBox createGiftBoxFixtureWithGift(Member member) {
        return GiftBox.builder()
            .box(createBox())
            .letter(createLetter())
            .gift(createGift())
            .sender(member)
            .name("선물박스 이름")
            .youtubeUrl("www.youtube.com")
            .senderName("보내는 사람")
            .receiverName("받는 사람")
            .deliverStatus(DeliverStatus.WAITING)
            .build();
    }

    public static GiftBox createGiftBoxFixtureWithoutGift(Member member) {
        return GiftBox.builder()
            .box(createBox())
            .letter(createLetter())
            .sender(member)
            .name("선물박스 이름")
            .youtubeUrl("www.youtube.com")
            .senderName("보내는 사람")
            .receiverName("받는 사람")
            .deliverStatus(DeliverStatus.WAITING)
            .build();
    }

    private static Box createBox() {
        return Box.builder()
            .id(1L)
            .sequence(1L)
            .normalImgUrl("www.test.com")
            .smallImgUrl("www.test.com")
            .setImgUrl("www.test.com")
            .topImgUrl("www.test.com")
            .kakaoMessageImgUrl("www.test.com")
            .lottieMakeUrl("www.test.com")
            .lottieArrivedUrl("www.test.com")
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

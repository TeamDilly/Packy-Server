package com.dilly;

import com.dilly.gift.domain.Box;
import com.dilly.gift.domain.gift.Gift;
import com.dilly.gift.domain.gift.GiftType;
import com.dilly.gift.domain.giftbox.DeliverStatus;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.gift.domain.letter.Letter;
import com.dilly.member.domain.Member;
import java.util.UUID;

public class GiftBoxFixture {

    public static GiftBox createGiftBoxFixture(Member member, Letter letter) {
        return GiftBox.builder()
            .uuid(UUID.randomUUID().toString())
            .box(createBox())
            .letter(letter)
            .gift(createGift())
            .sender(member)
            .name("선물박스 이름")
            .youtubeUrl("www.youtube.com")
            .senderName("보내는 사람")
            .receiverName("받는 사람")
            .build();
    }

    public static GiftBox sendGiftBoxFixtureWithGift(Member member, Letter letter) {
        return GiftBox.builder()
            .uuid(UUID.randomUUID().toString())
            .box(createBox())
            .letter(letter)
            .gift(createGift())
            .sender(member)
            .name("선물박스 이름")
            .youtubeUrl("www.youtube.com")
            .senderName("보내는 사람")
            .receiverName("받는 사람")
            .deliverStatus(DeliverStatus.DELIVERED)
            .build();
    }

    public static GiftBox sendGiftBoxFixtureWithoutGift(Member member, Letter letter) {
        return GiftBox.builder()
            .uuid(UUID.randomUUID().toString())
            .box(createBox())
            .letter(letter)
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

    private static Gift createGift() {
        return Gift.of(GiftType.PHOTO, "www.test.com");
    }
}

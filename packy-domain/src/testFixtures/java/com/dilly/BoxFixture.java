package com.dilly;

import com.dilly.gift.domain.Box;

public class BoxFixture {

    public static Box createBox(Long id, Long sequence) {
        return Box.builder()
            .id(id)
            .sequence(sequence)
            .normalImgUrl("www.example" + id + ".com")
            .smallImgUrl("www.example" + id + ".com")
            .setImgUrl("www.example" + id + ".com")
            .topImgUrl("www.example" + id + ".com")
            .kakaoMessageImgUrl("www.example" + id + ".com")
            .lottieMakeUrl("www.example" + id + ".com")
            .lottieArrivedUrl("www.example" + id + ".com")
            .build();
    }
}

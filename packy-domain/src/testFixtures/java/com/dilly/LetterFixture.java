package com.dilly;

import com.dilly.gift.domain.letter.Envelope;
import com.dilly.gift.domain.letter.EnvelopePaper;
import com.dilly.gift.domain.letter.Letter;
import com.dilly.gift.domain.letter.LetterPaper;

public class LetterFixture {

    public static Letter createLetterFixture() {
        return Letter.of("test", createEnvelopeFixture());
    }

    private static Envelope createEnvelopeFixture() {
        return Envelope.builder()
            .id(1L)
            .sequence(1L)
            .envelopePaper(EnvelopePaper.of("AAAAA", 100))
            .letterPaper(LetterPaper.of("BBBBB", 100))
            .imgUrl("www.test.com")
            .build();
    }
}

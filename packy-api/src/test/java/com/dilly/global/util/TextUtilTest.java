package com.dilly.global.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TextUtilTest {

    @DisplayName("이모지를 1글자로 센다")
    @ParameterizedTest
    @CsvSource({
        "\uD83D\uDE01, 1",
        "\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67\u200D\uD83D\uDC66, 1",
        "\uD83D\uDE46\uD83C\uDFFB\u200D♀\uFE0F, 1",
        "test, 4",
        "test\uD83D\uDE01, 5"
    })
    void countEmojiInOneLetter(String text, int expectedLength) {
        // when
        int length = TextUtil.countGraphemeClusters(text);

        // then
        assertThat(length).isEqualTo(expectedLength);
    }
}

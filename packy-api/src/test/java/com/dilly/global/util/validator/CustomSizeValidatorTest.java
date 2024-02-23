package com.dilly.global.util.validator;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Payload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomSizeValidatorTest {

    CustomSizeValidator customSizeValidator;

    @BeforeEach
    void setUp() {
        customSizeValidator = new CustomSizeValidator();
        CustomSize customSize = new CustomSize() {
            @Override
            public Class<? extends jakarta.validation.Constraint> annotationType() {
                return null;
            }

            @Override
            public String message() {
                return null;
            }

            @Override
            public Class<?>[] groups() {
                return new Class[0];
            }

            @Override
            public Class<? extends Payload>[] payload() {
                return new Class[0];
            }

            @Override
            public int min() {
                return 1;
            }

            @Override
            public int max() {
                return 200;
            }
        };

        customSizeValidator.initialize(customSize);
    }

    @DisplayName("이모지를 1글자로 센다")
    @Test
    void countEmojiInOneLetter() {
        // given
        String textWithEmoji = "안녕하세요\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67\u200D\uD83D\uDC66";

        // when
        boolean result = customSizeValidator.isValid(textWithEmoji, null);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("200글자를 넘을 경우, false를 반환한다")
    @Test
    void returnFalseOver200Letter() {
        // given
        String stringOver200Letter = "안녕하세요".repeat(41);

        // when
        boolean result = customSizeValidator.isValid(stringOver200Letter, null);

        // then
        assertThat(result).isFalse();
    }
}

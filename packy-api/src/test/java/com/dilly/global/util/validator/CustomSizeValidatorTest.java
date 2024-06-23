package com.dilly.global.util.validator;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Payload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CustomSizeValidatorTest {

    CustomSizeValidator customSizeValidator;

    private final int minSize = 1;
    private final int maxSize = 200;

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
                return minSize;
            }

            @Override
            public int max() {
                return maxSize;
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

    @Nested
    @DisplayName("글자 수에 따라")
    class Temp {

        @DisplayName("min을 넘을 경우, true를 반환한다")
        @Test
        void returnFalseUnderMin() {
            // given
            String stringUnderValue = "얍".repeat(minSize - 1);

            // when
            boolean result = customSizeValidator.isValid(stringUnderValue, null);

            // then
            assertThat(result).isFalse();
        }

        @DisplayName("max를 넘을 경우, false를 반환한다")
        @Test
        void returnFalseOverMax() {
            // given
            String stringOverValue = "얍".repeat(maxSize + 1);

            // when
            boolean result = customSizeValidator.isValid(stringOverValue, null);

            // then
            assertThat(result).isFalse();
        }

        @DisplayName("min과 max 사이의 길이일 경우, true를 반환한다")
        @Test
        void returnTrueBetweenMinAndMax() {
            // given
            int betweenSize = (minSize + maxSize) / 2;
            String stringBetweenValue = "얍".repeat(betweenSize);

            // when
            boolean result = customSizeValidator.isValid(stringBetweenValue, null);

            // then
            assertThat(result).isTrue();
        }
    }
}

package com.dilly.global.util.validator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class UuidValidatorTest {

    @DisplayName("유효한 UUID면 true, 그렇지 않다면 false를 반환한다.")
    @ParameterizedTest
    @MethodSource("provideStrings")
    void isValidUUID(String value, boolean expected) {
        // when
        boolean result = UuidValidator.isValidUUID(value);

        // then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideStrings() {
        return Stream.of(
            Arguments.of("550e8400-e29b-41d4-a716-446655440000", true),
            Arguments.of("test", false),
            Arguments.of(null, false)
        );
    }
}

package com.dilly.global.util.validator;

import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UuidValidator {

    public static boolean isValidUUID(String value) {
        final Pattern uuidPattern =
            Pattern.compile(
                "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
        return value != null && uuidPattern.matcher(value).matches();
    }
}

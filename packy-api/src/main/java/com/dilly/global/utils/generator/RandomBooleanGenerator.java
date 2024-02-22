package com.dilly.global.utils.generator;

import com.dilly.exception.ErrorCode;
import com.dilly.exception.internalserver.InternalServerException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RandomBooleanGenerator {

    private static final SecureRandom random;

    static {
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new InternalServerException(ErrorCode.INTERNAL_ERROR);
        }
    }

    public static boolean generate() {
        return random.nextBoolean();
    }
}

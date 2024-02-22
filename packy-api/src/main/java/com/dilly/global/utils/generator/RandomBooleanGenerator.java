package com.dilly.global.utils.generator;

import java.util.Random;

public class RandomBooleanGenerator {

    private static Random random = new Random();

    public static boolean generate() {
        return random.nextBoolean();
    }
}

package com.strongduanmu;

@SuppressWarnings("unused")
public final class StringReverser {
    
    public static String reverse(final String input) {
        return new StringBuilder(input).reverse().toString();
    }
}

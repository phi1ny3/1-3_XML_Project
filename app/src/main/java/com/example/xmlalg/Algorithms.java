package com.example.xmlalg;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Objects;

/**
 * Pure functions only—keeps algorithms testable and reusable.
 * No Android/UI dependencies and no global state.
 */
public final class Algorithms {
    private Algorithms() {}

    public static String reverseString(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    /** avoid overflow (BigInteger) and guard inputs explicitly. */
    public static BigInteger factorialBig(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be non-negative");
        BigInteger acc = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            acc = acc.multiply(BigInteger.valueOf(i));
        }
        return acc;
    }

    /** normalize to alphanumeric lowercase; users type punctuation/spaces. */
    public static boolean isPalindromeNormalized(String s) {
        final StringBuilder filtered = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            final char ch = s.charAt(i);
            if (Character.isLetterOrDigit(ch)) {
                filtered.append(Character.toLowerCase(ch));
            }
        }
        int i = 0, j = filtered.length() - 1;
        while (i < j) {
            if (filtered.charAt(i) != filtered.charAt(j)) return false;
            i++; j--;
        }
        return true;
    }

    /** functional-chain analog using streams; ignores non-numbers gracefully. */
    public static Double findMaxCsv(String csv) {
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .map(Algorithms::tryParseDouble)   // null if not a number
                .filter(Objects::nonNull)
                .max(Double::compareTo)
                .orElse(null);
    }

    private static Double tryParseDouble(String s) {
        try { return Double.valueOf(s); } catch (NumberFormatException e) { return null; }
    }

    /** single pass count; mirrors Kotlin's count { ... }. */
    public static int countVowels(String s) {
        return (int) s.chars()
                .map(Character::toLowerCase)
                .filter(c -> "aeiou".indexOf(c) >= 0)
                .count();
    }

    /** standard iterative Fibonacci with explicit negative guard. */
    public static long fibonacciNth(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be non-negative");
        if (n < 2) return n;
        long a = 0L, b = 1L;
        for (int i = 2; i <= n; i++) {
            final long next = a + b;
            a = b;
            b = next;
        }
        return b;
    }

    /** O(1) closed form with guard; consistent with other input checks. */
    public static long sumToN(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be non-negative");
        return (long) n * (n + 1L) / 2L;
    }

    /** bit test mirrors Kotlin's (n and 1) == 0 idiom. */
    public static String evenOrOdd(int n) {
        return ((n & 1) == 0) ? "Even" : "Odd";
    }
}

package com.example.xmlalg;

import java.math.BigInteger;

/**
 * Pure functions only
 * Less dependencies, minimal
 */
public final class Algorithms {
    private Algorithms() {}

    public static String reverseString(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    /** avoid overflow (BigInteger) mentioned in feedback from Discord and guard inputs explicitly. */
    public static BigInteger factorialBig(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be non-negative");
        BigInteger acc = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            acc = acc.multiply(BigInteger.valueOf(i));
        }
        return acc;
    }

    /** normalize to alphanumeric lowercase */
    public static boolean isPalindromeNormalized(String s) {
        StringBuilder filtered = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (Character.isLetterOrDigit(ch)) {
                filtered.append(Character.toLowerCase(ch));
            }
        }
        int length = filtered.length();
        for (int i = 0; i < length / 2; i++) {
            if (filtered.charAt(i) != filtered.charAt(length - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    public static Double findMaxCsv(String csv) {
        String[] parts = csv.split(",");
        double max = -Double.MAX_VALUE;
        boolean hasValid = false;
        for (String part : parts) {
            String trimmed = part.trim();
            try {
                double value = Double.parseDouble(trimmed);
                if (value > max) {
                    max = value;
                    hasValid = true;
                }
            } catch (NumberFormatException e) {
                // ignore invalid
            }
        }
        return hasValid ? max : null;
    }

    public static int countVowels(String s) {
        String lower = s.toLowerCase();
        int count = 0;
        for (int i = 0; i < lower.length(); i++) {
            char c = lower.charAt(i);
            if ("aeiou".indexOf(c) >= 0) {
                count++;
            }
        }
        return count;
    }

    /** Iterative Fibonacci with explicit negative guard. */
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

    /** O(1) closed form with guard */
    public static long sumToN(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be non-negative");
        return (long) n * (n + 1L) / 2L;
    }

    /** bit test for even or odd */
    public static String evenOrOdd(int n) {
        return ((n & 1) == 0) ? "Even" : "Odd";
    }
}

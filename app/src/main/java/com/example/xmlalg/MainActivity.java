package com.example.xmlalg;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xmlalg.databinding.ActivityMainBinding;

import java.util.function.IntFunction;

/**
 * Why: Keep UI glue thin and obvious. Use ViewBinding for type-safe view access,
 * and a tiny helper to avoid repeating the parse/validate pattern for integer inputs.
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding; // Why: safer than findViewById; fewer null mistakes.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        wireEvents();
    }

    private void wireEvents() {
        // Reverse
        binding.btnReverse.setOnClickListener(v -> {
            final String s = safeText(binding.etReverse);
            binding.tvReverseOut.setText(
                    s.isEmpty() ? "Please enter some text." : Algorithms.reverseString(s)
            );
        });

        // Factorial
        binding.btnFactorial.setOnClickListener(v ->
                runIntTask(binding.etFactorial, binding.tvFactorialOut, n -> {
                    if (n < 0) return "Enter a non-negative integer.";
                    return Algorithms.factorialBig(n).toString();
                })
        );

        // Palindrome
        binding.btnPalindrome.setOnClickListener(v -> {
            final String s = safeText(binding.etPalindrome);
            binding.tvPalindromeOut.setText(
                    s.isEmpty()
                            ? "Please enter some text."
                            : (Algorithms.isPalindromeNormalized(s)
                            ? "Yes, it's a palindrome!"
                            : "No, not a palindrome.")
            );
        });

        // Find Max CSV
        binding.btnMaxCsv.setOnClickListener(v -> {
            final String csv = safeText(binding.etMaxCsv);
            final Double d = Algorithms.findMaxCsv(csv);
            binding.tvMaxCsvOut.setText(
                    d != null ? d.toString()
                            : "Please enter a valid comma-separated list of numbers."
            );
        });

        // Count Vowels
        binding.btnVowels.setOnClickListener(v -> {
            final String s = safeText(binding.etVowels);
            binding.tvVowelsOut.setText(
                    s.isEmpty() ? "Please enter some text."
                            : String.valueOf(Algorithms.countVowels(s))
            );
        });

        // Fibonacci nth
        binding.btnFib.setOnClickListener(v ->
                runIntTask(binding.etFib, binding.tvFibOut, n -> {
                    if (n < 0) return "Enter a non-negative integer.";
                    return String.valueOf(Algorithms.fibonacciNth(n));
                })
        );

        // Sum 1..N
        binding.btnSumToN.setOnClickListener(v ->
                runIntTask(binding.etSumToN, binding.tvSumToNOut, n -> {
                    if (n < 0) return "Enter a non-negative integer.";
                    return String.valueOf(Algorithms.sumToN(n));
                })
        );

        // Even / Odd
        binding.btnEvenOdd.setOnClickListener(v ->
                runIntTask(binding.etEvenOdd, binding.tvEvenOddOut, Algorithms::evenOrOdd)
        );
    }

    /** Why: de-duplicate the common parse/validate/write pattern for integer tasks. */
    private void runIntTask(EditText input, TextView out, IntFunction<String> task) {
        final Integer n = parseInt(safeText(input));
        final String msg = (n == null) ? "Enter a whole number (integer)." : task.apply(n);
        out.setText(msg);
    }

    private static String safeText(EditText et) {
        final CharSequence cs = et.getText();
        return cs == null ? "" : cs.toString().trim();
    }

    private static Integer parseInt(String s) {
        if (TextUtils.isEmpty(s)) return null;
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

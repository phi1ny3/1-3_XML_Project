package com.example.xmlalg;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

/*
 * Main Activity that displays algorithm cards and wires up their UI interactions.
 */
public class MainActivity extends Activity {

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Wire up reverse string
        EditText etReverse = findViewById(R.id.etReverse);
        Button btnReverse = findViewById(R.id.btnReverse);
        TextView tvReverse = findViewById(R.id.tvReverseOut);
        btnReverse.setOnClickListener(v -> {
            String text = getSafeText(etReverse);
            if (text.isEmpty()) {
                tvReverse.setText(getString(R.string.error_enter_text));
            } else {
                tvReverse.setText(Algorithms.reverseString(text));
            }
        });

        // Wire up palindrome
        EditText etPalindrome = findViewById(R.id.etPalindrome);
        Button btnPalindrome = findViewById(R.id.btnPalindrome);
        TextView tvPalindrome = findViewById(R.id.tvPalindromeOut);
        btnPalindrome.setOnClickListener(v -> {
            String text = getSafeText(etPalindrome);
            if (text.isEmpty()) {
                tvPalindrome.setText(getString(R.string.error_enter_text));
            } else {
                boolean isPal = Algorithms.isPalindromeNormalized(text);
                tvPalindrome.setText(isPal ? getString(R.string.yes_palindrome) : getString(R.string.no_palindrome));
            }
        });

        // Wire up max CSV
        EditText etMaxCsv = findViewById(R.id.etMaxCsv);
        Button btnMaxCsv = findViewById(R.id.btnMaxCsv);
        TextView tvMaxCsv = findViewById(R.id.tvMaxCsvOut);
        btnMaxCsv.setOnClickListener(v -> {
            String text = getSafeText(etMaxCsv);
            Double max = Algorithms.findMaxCsv(text);
            tvMaxCsv.setText(max == null ? getString(R.string.error_invalid_csv) : String.format(Locale.getDefault(), "%g", max));
        });

        // Wire up vowels
        EditText etVowels = findViewById(R.id.etVowels);
        Button btnVowels = findViewById(R.id.btnVowels);
        TextView tvVowels = findViewById(R.id.tvVowelsOut);
        btnVowels.setOnClickListener(v -> {
            String text = getSafeText(etVowels);
            if (text.isEmpty()) {
                tvVowels.setText(getString(R.string.error_enter_text));
            } else {
                tvVowels.setText(String.format(Locale.getDefault(), "%d", Algorithms.countVowels(text)));
            }
        });

        // Wire up factorial
        EditText etFactorial = findViewById(R.id.etFactorial);
        Button btnFactorial = findViewById(R.id.btnFactorial);
        TextView tvFactorial = findViewById(R.id.tvFactorialOut);
        btnFactorial.setOnClickListener(v -> {
            Integer num = parseIntSafe(getSafeText(etFactorial));
            if (num == null) {
                tvFactorial.setText(getString(R.string.error_invalid_number));
            } else if (num < 0) {
                tvFactorial.setText(getString(R.string.error_nonnegative));
            } else {
                tvFactorial.setText(String.format(Locale.getDefault(), "%s", Algorithms.factorialBig(num)));
            }
        });

        // Wire up fibonacci
        EditText etFib = findViewById(R.id.etFib);
        Button btnFib = findViewById(R.id.btnFib);
        TextView tvFib = findViewById(R.id.tvFibOut);
        btnFib.setOnClickListener(v -> {
            Integer num = parseIntSafe(getSafeText(etFib));
            if (num == null) {
                tvFib.setText(getString(R.string.error_invalid_number));
            } else if (num < 0) {
                tvFib.setText(getString(R.string.error_nonnegative));
            } else {
                tvFib.setText(String.format(Locale.getDefault(), "%d", Algorithms.fibonacciNth(num)));
            }
        });

        // Wire up sum to N
        EditText etSumToN = findViewById(R.id.etSumToN);
        Button btnSumToN = findViewById(R.id.btnSumToN);
        TextView tvSumToN = findViewById(R.id.tvSumToNOut);
        btnSumToN.setOnClickListener(v -> {
            Integer num = parseIntSafe(getSafeText(etSumToN));
            if (num == null) {
                tvSumToN.setText(getString(R.string.error_invalid_number));
            } else if (num < 0) {
                tvSumToN.setText(getString(R.string.error_nonnegative));
            } else {
                tvSumToN.setText(String.format(Locale.getDefault(), "%d", Algorithms.sumToN(num)));
            }
        });

        // Wire up even odd
        EditText etEvenOdd = findViewById(R.id.etEvenOdd);
        Button btnEvenOdd = findViewById(R.id.btnEvenOdd);
        TextView tvEvenOdd = findViewById(R.id.tvEvenOddOut);
        btnEvenOdd.setOnClickListener(v -> {
            Integer num = parseIntSafe(getSafeText(etEvenOdd));
            if (num == null) {
                tvEvenOdd.setText(getString(R.string.error_invalid_number));
            } else {
                tvEvenOdd.setText(Algorithms.evenOrOdd(num));
            }
        });
    }

    /* Get trimmed text from EditText, no nulls */
    private String getSafeText(EditText et) {
        CharSequence cs = et.getText();
        if (cs == null) {
            return "";
        }
        return cs.toString().trim();
    }

    /* Parse an integer, returns null if invalid. */
    private Integer parseIntSafe(String s) {
        if (TextUtils.isEmpty(s)) {
            return null;
        }
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

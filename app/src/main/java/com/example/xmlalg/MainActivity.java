package com.example.xmlalg;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.Optional;

/*
 * Main Activity that displays algorithm cards and wires up their UI interactions.
 * Each card follows a consistent pattern: input -> algorithm -> output.
 */
public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sorted by type of algorithm for easier navigation
        wireTextAlgorithm(
                R.id.etReverse, R.id.btnReverse, R.id.tvReverseOut,
                text -> Optional.of(text)
                        .filter(s -> !s.isEmpty())
                        .map(Algorithms::reverseString)
                        .orElse(getString(R.string.error_enter_text))
        );

        wireTextAlgorithm(
                R.id.etPalindrome, R.id.btnPalindrome, R.id.tvPalindromeOut,
                text -> Optional.of(text)
                        .filter(s -> !s.isEmpty())
                        .map(s -> Algorithms.isPalindromeNormalized(s)
                                ? getString(R.string.yes_palindrome)
                                : getString(R.string.no_palindrome))
                        .orElse(getString(R.string.error_enter_text))
        );

        wireTextAlgorithm(
                R.id.etMaxCsv, R.id.btnMaxCsv, R.id.tvMaxCsvOut,
                text -> Optional.ofNullable(Algorithms.findMaxCsv(text))
                        .map(Object::toString)
                        .orElse(getString(R.string.error_invalid_csv))
        );

        wireTextAlgorithm(
                R.id.etVowels, R.id.btnVowels, R.id.tvVowelsOut,
                text -> Optional.of(text)
                        .filter(s -> !s.isEmpty())
                        .map(s -> String.valueOf(Algorithms.countVowels(s)))
                        .orElse(getString(R.string.error_enter_text))
        );

        wireIntAlgorithm(
                R.id.etFactorial, R.id.btnFactorial, R.id.tvFactorialOut,
                num -> Optional.of(num)
                        .filter(n -> n >= 0)
                        .map(n -> Algorithms.factorialBig(n).toString())
                        .orElse(getString(R.string.error_nonnegative))
        );

        wireIntAlgorithm(
                R.id.etFib, R.id.btnFib, R.id.tvFibOut,
                num -> Optional.of(num)
                        .filter(n -> n >= 0)
                        .map(n -> String.valueOf(Algorithms.fibonacciNth(n)))
                        .orElse(getString(R.string.error_nonnegative))
        );

        wireIntAlgorithm(
                R.id.etSumToN, R.id.btnSumToN, R.id.tvSumToNOut,
                num -> Optional.of(num)
                        .filter(n -> n >= 0)
                        .map(n -> String.valueOf(Algorithms.sumToN(n)))
                        .orElse(getString(R.string.error_nonnegative))
        );

        wireIntAlgorithm(
                R.id.etEvenOdd, R.id.btnEvenOdd, R.id.tvEvenOddOut,
                Algorithms::evenOrOdd
        );
    }

    // Helpers

    /* Wire up a text-based algorithm card's UI components. */
    private void wireTextAlgorithm(final int etId, final int btnId, final int tvId, final Function<String, String> logic) {
        final EditText input = findViewById(etId);
        final Button run = findViewById(btnId);
        final TextView out = findViewById(tvId);

        run.setOnClickListener(v -> out.setText(logic.apply(safeText(input))));
        setImeDoneAction(input, run);
        if (!setImeDoneAction(input, run)) {
            android.util.Log.w("MainActivity", "Failed to set IME done action for " + etId);
        }
    }

    /* Wire up an integer-based algorithm card's UI components. */
    private void wireIntAlgorithm(final int etId, final int btnId, final int tvId, final IntFunction<String> logic) {
        final EditText input = findViewById(etId);
        final Button run = findViewById(btnId);
        final TextView out = findViewById(tvId);

        run.setOnClickListener(v -> {
            String result = Optional.ofNullable(parseInt(safeText(input)))
                    .map(logic::apply)
                    .orElse(getString(R.string.error_invalid_number));
            out.setText(result);
        });
        setImeDoneAction(input, run);
        if (!setImeDoneAction(input, run)) {
            android.util.Log.w("MainActivity", "Failed to set IME done action for " + etId);
        }
    }

    /* Make "Done" on keyboard click behave like pressing the Run button. */
    private static boolean setImeDoneAction(final EditText input, final Button runBtn) {
        input.setOnEditorActionListener((v, actionId, event) ->
                actionId == EditorInfo.IME_ACTION_DONE && runBtn.performClick());
        return true;
    }

    /* Get trimmed text from EditText, never null. */
    private static String safeText(final EditText et) {
        return Optional.ofNullable(et.getText())
                .map(Object::toString)
                .map(String::trim)
                .orElse("");
    }

    /* Try to parse an integer, returns Optional.empty() if invalid. */
    private static Integer parseInt(final String s) {
        if (TextUtils.isEmpty(s)) return null;
        try {
            return Integer.parseInt(s);
        } catch (final NumberFormatException e) {
            return null;
        }
    }
}

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

/**
 * Why: keep UI glue simple; no viewbinding. Each card wires input -> algorithm -> output.
 * Why: split helpers by input type to avoid nullability & parsing mistakes.
 */
public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ---------- String-based algorithms ----------
        wireTextAlgorithm(
                R.id.etReverse, R.id.btnReverse, R.id.tvReverseOut,
                s -> s.isEmpty() ? getString(R.string.error_enter_text) : Algorithms.reverseString(s)
        );

        wireTextAlgorithm(
                R.id.etPalindrome, R.id.btnPalindrome, R.id.tvPalindromeOut,
                s -> s.isEmpty()
                        ? getString(R.string.error_enter_text)
                        : (Algorithms.isPalindromeNormalized(s)
                        ? getString(R.string.yes_palindrome)
                        : getString(R.string.no_palindrome))
        );

        wireTextAlgorithm(
                R.id.etMaxCsv, R.id.btnMaxCsv, R.id.tvMaxCsvOut,
                s -> {
                    Double d = Algorithms.findMaxCsv(s);
                    return d != null ? d.toString() : getString(R.string.error_invalid_csv);
                }
        );

        wireTextAlgorithm(
                R.id.etVowels, R.id.btnVowels, R.id.tvVowelsOut,
                s -> s.isEmpty() ? getString(R.string.error_enter_text)
                        : String.valueOf(Algorithms.countVowels(s))
        );

        // ---------- Integer-based algorithms ----------
        wireIntAlgorithm(
                R.id.etFactorial, R.id.btnFactorial, R.id.tvFactorialOut,
                n -> {
                    if (n < 0) return getString(R.string.error_nonnegative);
                    return Algorithms.factorialBig(n).toString();
                }
        );

        wireIntAlgorithm(
                R.id.etFib, R.id.btnFib, R.id.tvFibOut,
                n -> {
                    if (n < 0) return getString(R.string.error_nonnegative);
                    return String.valueOf(Algorithms.fibonacciNth(n));
                }
        );

        wireIntAlgorithm(
                R.id.etSumToN, R.id.btnSumToN, R.id.tvSumToNOut,
                n -> {
                    if (n < 0) return getString(R.string.error_nonnegative);
                    return String.valueOf(Algorithms.sumToN(n));
                }
        );

        wireIntAlgorithm(
                R.id.etEvenOdd, R.id.btnEvenOdd, R.id.tvEvenOddOut,
                Algorithms::evenOrOdd   // ← replace n -> Algorithms.evenOrOdd(n)
        );
    }

    // ---------- Helpers ----------

    /** Why: for text algorithms (no parsing). */
    private void wireTextAlgorithm(int etId, int btnId, int tvId, Function<String, String> logic) {
        EditText input = findViewById(etId);
        Button   run   = findViewById(btnId);
        TextView out   = findViewById(tvId);

        run.setOnClickListener(v -> out.setText(logic.apply(safeText(input))));
        // replace inline lambda with helper (clears "lambda can be replaced..." lint)
        setImeDoneAction(input, run);
    }

    /** Why: for integer algorithms with consistent validation UX. */
    private void wireIntAlgorithm(int etId, int btnId, int tvId, IntFunction<String> logic) {
        EditText input = findViewById(etId);
        Button   run   = findViewById(btnId);
        TextView out   = findViewById(tvId);

        run.setOnClickListener(v -> {
            Integer n = parseInt(safeText(input));
            out.setText(n == null ? getString(R.string.error_invalid_number) : logic.apply(n));
        });
        setImeDoneAction(input, run);
    }


    // Helper to make "Done" on keyboard click the same as pressing the Run button
    private static void setImeDoneAction(EditText input, Button runBtn) {
        input.setOnEditorActionListener((v, actionId, event) ->
                actionId == EditorInfo.IME_ACTION_DONE && runBtn.performClick());
    }

    private static String safeText(EditText et) {
        CharSequence cs = et.getText();
        return cs == null ? "" : cs.toString().trim();
    }
    private static Integer parseInt(String s) {
        if (TextUtils.isEmpty(s)) return null;
        try { return Integer.parseInt(s); } catch (NumberFormatException e) { return null; }
    }
}

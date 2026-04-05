package com.example.terabox;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.terabox.databinding.ActivityLoginBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupLegalText();
        View.OnClickListener goDashboard = v -> openDashboard();

        binding.buttonApple.setOnClickListener(goDashboard);
        binding.buttonGoogle.setOnClickListener(goDashboard);
        binding.buttonFacebook.setOnClickListener(goDashboard);
        binding.buttonEmail.setOnClickListener(goDashboard);
        binding.buttonPhone.setOnClickListener(goDashboard);

        binding.buttonHelp.setOnClickListener(v ->
                Toast.makeText(this, R.string.content_desc_help, Toast.LENGTH_SHORT).show());
    }

    private void setupLegalText() {
        String full = getString(R.string.login_legal);
        String terms = getString(R.string.terms_of_service);
        String privacy = getString(R.string.privacy_policy);
        SpannableString ss = new SpannableString(full);

        int termsStart = full.indexOf(terms);
        int privacyStart = full.indexOf(privacy);
        int linkColor = ContextCompat.getColor(this, R.color.terabox_link);

        if (termsStart >= 0) {
            ss.setSpan(new StyleSpan(Typeface.BOLD), termsStart, termsStart + terms.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new LinkSpan(linkColor, () -> showStub(terms)), termsStart,
                    termsStart + terms.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (privacyStart >= 0) {
            ss.setSpan(new StyleSpan(Typeface.BOLD), privacyStart, privacyStart + privacy.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new LinkSpan(linkColor, () -> showStub(privacy)), privacyStart,
                    privacyStart + privacy.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        binding.textLegal.setText(ss);
        binding.textLegal.setMovementMethod(LinkMovementMethod.getInstance());
        binding.textLegal.setHighlightColor(android.graphics.Color.TRANSPARENT);
    }

    private void showStub(String label) {
        Toast.makeText(this, label, Toast.LENGTH_SHORT).show();
    }

    private void openDashboard() {
        startActivity(new Intent(this, DashboardActivity.class));
    }

    private static final class LinkSpan extends ClickableSpan {
        private final int color;
        private final Runnable action;

        LinkSpan(int color, Runnable action) {
            this.color = color;
            this.action = action;
        }

        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(color);
            ds.setUnderlineText(true);
            ds.bgColor = android.graphics.Color.TRANSPARENT;
        }

        @Override
        public void onClick(@NonNull View widget) {
            action.run();
        }
    }
}

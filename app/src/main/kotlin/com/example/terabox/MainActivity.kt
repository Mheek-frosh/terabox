package com.example.terabox

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.terabox.databinding.ActivityLoginBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLegalText()

        val goDashboard = View.OnClickListener { openDashboard() }
        binding.buttonApple.setOnClickListener(goDashboard)
        binding.buttonGoogle.setOnClickListener(goDashboard)
        binding.buttonFacebook.setOnClickListener(goDashboard)
        binding.buttonEmail.setOnClickListener(goDashboard)
        binding.buttonPhone.setOnClickListener(goDashboard)

        binding.buttonHelp.setOnClickListener {
            Toast.makeText(this, R.string.content_desc_help, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupLegalText() {
        val full = getString(R.string.login_legal)
        val terms = getString(R.string.terms_of_service)
        val privacy = getString(R.string.privacy_policy)
        val ss = SpannableString(full)
        val termsStart = full.indexOf(terms)
        val privacyStart = full.indexOf(privacy)
        val linkColor = ContextCompat.getColor(this, R.color.terabox_link)

        if (termsStart >= 0) {
            ss.setSpan(
                StyleSpan(Typeface.BOLD),
                termsStart,
                termsStart + terms.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            ss.setSpan(
                LinkSpan(linkColor) { showStub(terms) },
                termsStart,
                termsStart + terms.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        if (privacyStart >= 0) {
            ss.setSpan(
                StyleSpan(Typeface.BOLD),
                privacyStart,
                privacyStart + privacy.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            ss.setSpan(
                LinkSpan(linkColor) { showStub(privacy) },
                privacyStart,
                privacyStart + privacy.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        binding.textLegal.text = ss
        binding.textLegal.movementMethod = LinkMovementMethod.getInstance()
        binding.textLegal.highlightColor = Color.TRANSPARENT
    }

    private fun showStub(label: String) {
        Toast.makeText(this, label, Toast.LENGTH_SHORT).show()
    }

    private fun openDashboard() {
        startActivity(Intent(this, DashboardActivity::class.java))
    }

    private class LinkSpan(
        private val color: Int,
        private val action: () -> Unit
    ) : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = color
            ds.isUnderlineText = true
            ds.bgColor = Color.TRANSPARENT
        }

        override fun onClick(widget: View) {
            action()
        }
    }
}

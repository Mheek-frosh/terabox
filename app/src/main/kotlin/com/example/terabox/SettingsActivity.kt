package com.example.terabox

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.terabox.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    private val prefs by lazy {
        getSharedPreferences(TeraboxApplication.PREFS_NAME, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonBack.setOnClickListener { finish() }

        binding.switchNightMode.setOnCheckedChangeListener(null)
        binding.switchNightMode.isChecked =
            prefs.getBoolean(TeraboxApplication.KEY_NIGHT_MODE, false)
        binding.switchNightMode.setOnCheckedChangeListener { _, checked ->
            prefs.edit().putBoolean(TeraboxApplication.KEY_NIGHT_MODE, checked).apply()
            AppCompatDelegate.setDefaultNightMode(
                if (checked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        binding.switchAdVoice.setOnCheckedChangeListener(null)
        binding.switchAdVoice.isChecked =
            prefs.getBoolean(TeraboxApplication.KEY_AD_VOICE, true)
        binding.switchAdVoice.setOnCheckedChangeListener { _, checked ->
            prefs.edit().putBoolean(TeraboxApplication.KEY_AD_VOICE, checked).apply()
        }

        binding.rowBindEmail.setOnClickListener {
            toast(R.string.settings_bind_email)
        }
        binding.rowSecurity.setOnClickListener {
            toast(R.string.settings_security)
        }
        binding.rowAboutTerabox.setOnClickListener {
            toast(R.string.settings_about_terabox)
        }
        binding.rowAboutUs.setOnClickListener {
            toast(R.string.settings_about_us)
        }
        binding.rowClearCache.setOnClickListener {
            toast(R.string.settings_clear_cache)
        }

        binding.buttonSwitchAccount.setOnClickListener {
            toast(R.string.settings_switch_account)
        }
        binding.buttonLogOut.setOnClickListener {
            toast(R.string.settings_log_out)
        }
    }

    private fun toast(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }
}

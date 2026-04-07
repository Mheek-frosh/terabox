package com.example.terabox

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.updateLayoutParams
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.terabox.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    private var homeFragment: HomeFragment? = null
    private var filesFragment: FilesFragment? = null
    private var shareFragment: ShareFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDrawerWidth()
        setupDrawerBehavior()
        setupDrawerActions()
        setupMainFragments(savedInstanceState)
        setupBottomNavigation()

        binding.bottomNav.selectedItemId =
            savedInstanceState?.getInt(STATE_SELECTED_NAV, R.id.nav_home) ?: R.id.nav_home

        binding.fabAdd.setOnClickListener {
            Toast.makeText(this, R.string.content_desc_add, Toast.LENGTH_SHORT).show()
        }
    }

    fun openMainDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    fun openMessageCenter() {
        if (supportFragmentManager.findFragmentByTag(TAG_MESSAGE) != null) return
        supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.fragment_fade_in,
                R.anim.fragment_fade_out,
                R.anim.fragment_fade_in,
                R.anim.fragment_fade_out
            )
            add(R.id.fragmentContainer, MessageFragment(), TAG_MESSAGE)
            hide(homeFragment!!)
            hide(filesFragment!!)
            hide(shareFragment!!)
            addToBackStack(TAG_MESSAGE)
        }
    }

    private fun popTopOverlayIfPresent() {
        supportFragmentManager.executePendingTransactions()
        if (supportFragmentManager.findFragmentByTag(TAG_MESSAGE) != null ||
            supportFragmentManager.findFragmentByTag(TAG_TRANSFER) != null
        ) {
            supportFragmentManager.popBackStackImmediate()
        }
    }

    /**
     * Closes message / transfer overlay and returns to the Home tab on the main dashboard.
     */
    fun navigateHomeClosingOverlay() {
        popTopOverlayIfPresent()
        showMainFragment(homeFragment)
        binding.bottomNav.selectedItemId = R.id.nav_home
    }

    fun openTransferList() {
        if (supportFragmentManager.findFragmentByTag(TAG_TRANSFER) != null) return
        supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.fragment_fade_in,
                R.anim.fragment_fade_out,
                R.anim.fragment_fade_in,
                R.anim.fragment_fade_out
            )
            add(R.id.fragmentContainer, TransferListFragment(), TAG_TRANSFER)
            hide(homeFragment!!)
            hide(filesFragment!!)
            hide(shareFragment!!)
            addToBackStack(TAG_TRANSFER)
        }
    }

    private fun setupMainFragments(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            homeFragment = HomeFragment()
            filesFragment = FilesFragment()
            shareFragment = ShareFragment()
            supportFragmentManager.commit {
                add(R.id.fragmentContainer, homeFragment!!, TAG_HOME)
                add(R.id.fragmentContainer, filesFragment!!, TAG_FILES)
                add(R.id.fragmentContainer, shareFragment!!, TAG_SHARE)
                hide(filesFragment!!)
                hide(shareFragment!!)
            }
        } else {
            homeFragment = supportFragmentManager.findFragmentByTag(TAG_HOME) as? HomeFragment
            filesFragment = supportFragmentManager.findFragmentByTag(TAG_FILES) as? FilesFragment
            shareFragment = supportFragmentManager.findFragmentByTag(TAG_SHARE) as? ShareFragment
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(STATE_SELECTED_NAV, binding.bottomNav.selectedItemId)
    }

    private fun setupBottomNavigation() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            popTopOverlayIfPresent()
            when (item.itemId) {
                R.id.nav_home -> {
                    showMainFragment(homeFragment)
                    true
                }
                R.id.nav_files -> {
                    showMainFragment(filesFragment)
                    true
                }
                R.id.nav_share -> {
                    showMainFragment(shareFragment)
                    true
                }
                else -> {
                    Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
                    false
                }
            }
        }
    }

    private fun showMainFragment(target: Fragment?) {
        if (target == null) return
        val all = listOfNotNull(homeFragment, filesFragment, shareFragment)
        supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.fragment_fade_in,
                R.anim.fragment_fade_out,
                R.anim.fragment_fade_in,
                R.anim.fragment_fade_out
            )
            all.forEach { f ->
                if (f == target) show(f) else hide(f)
            }
        }
    }

    private fun setupDrawerWidth() {
        val w = (resources.displayMetrics.widthPixels * 0.85f).toInt()
        binding.navDrawerScroll.updateLayoutParams<DrawerLayout.LayoutParams> {
            width = w
        }
    }

    private fun setupDrawerBehavior() {
        val backCallback = object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
        onBackPressedDispatcher.addCallback(this, backCallback)

        binding.drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerOpened(drawerView: View) {
                backCallback.isEnabled = true
            }

            override fun onDrawerClosed(drawerView: View) {
                backCallback.isEnabled = false
            }
        })
    }

    private fun setupDrawerActions() {
        val d = binding.navDrawerContent

        d.drawerGetFreeSpace.setOnClickListener { toast(R.string.drawer_get_free_space) }
        d.drawerBannerUpgrade.setOnClickListener { toast(R.string.upgrade) }
        d.drawerButtonQr.setOnClickListener { toast(R.string.drawer_qr) }

        d.drawerQuickShared.setOnClickListener { toast(R.string.drawer_shared) }
        d.drawerQuickTrash.setOnClickListener { toast(R.string.drawer_trash) }

        d.drawerRowAutoBackup.setOnClickListener { toast(R.string.drawer_auto_backup) }
        d.drawerRowScan.setOnClickListener { toast(R.string.drawer_scan) }
        d.drawerRowSpaceAnalyzer.setOnClickListener { toast(R.string.drawer_space_analyzer) }
        d.drawerRowVault.setOnClickListener { toast(R.string.drawer_personal_vault) }
        d.drawerRowBenefits.setOnClickListener { toast(R.string.drawer_benefits) }
        d.drawerRowSettings.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        d.drawerRowWebmaster.setOnClickListener { toast(R.string.drawer_webmaster) }
        d.drawerRowHelp.setOnClickListener { toast(R.string.drawer_help_center) }
        d.drawerRowAbout.setOnClickListener { toast(R.string.drawer_about) }
    }

    private fun toast(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG_HOME = "home"
        private const val TAG_FILES = "files"
        private const val TAG_SHARE = "share"
        private const val TAG_MESSAGE = "message"
        private const val TAG_TRANSFER = "transfer"
        private const val STATE_SELECTED_NAV = "selected_nav"
    }
}

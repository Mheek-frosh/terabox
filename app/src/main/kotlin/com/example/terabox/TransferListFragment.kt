package com.example.terabox

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.terabox.databinding.FragmentTransferListBinding

class TransferListFragment : Fragment() {

    private var _binding: FragmentTransferListBinding? = null
    private val binding get() = _binding!!

    private val black by lazy { ContextCompat.getColor(requireContext(), R.color.black) }
    private val muted by lazy { ContextCompat.getColor(requireContext(), R.color.files_tab_muted) }

    private var bannerVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bannerVisible = savedInstanceState?.getBoolean(KEY_BANNER_VISIBLE, true) ?: true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransferListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val host = activity as? DashboardActivity
        binding.transferButtonBack.setOnClickListener {
            host?.navigateHomeClosingOverlay()
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    host?.navigateHomeClosingOverlay()
                }
            }
        )

        binding.transferBannerClose.setOnClickListener {
            bannerVisible = false
            binding.transferBanner.visibility = View.GONE
        }
        binding.transferBanner.visibility =
            if (bannerVisible) View.VISIBLE else View.GONE

        binding.transferLinkDetails.setOnClickListener {
            Toast.makeText(requireContext(), R.string.transfer_view_details, Toast.LENGTH_SHORT).show()
        }

        binding.tabDownloadsContainer.setOnClickListener { selectTab(TransferTab.DOWNLOADS) }
        binding.tabUploadsContainer.setOnClickListener { selectTab(TransferTab.UPLOADS) }

        selectTab(TransferTab.DOWNLOADS)
    }

    private enum class TransferTab { DOWNLOADS, UPLOADS }

    private fun selectTab(tab: TransferTab) {
        val downloadsSelected = tab == TransferTab.DOWNLOADS

        binding.tabDownloadsLabel.setTextColor(if (downloadsSelected) black else muted)
        binding.tabDownloadsLabel.setTypeface(null, if (downloadsSelected) Typeface.BOLD else Typeface.NORMAL)
        binding.tabDownloadsIndicator.setBackgroundColor(if (downloadsSelected) black else Color.TRANSPARENT)

        binding.tabUploadsLabel.setTextColor(if (!downloadsSelected) black else muted)
        binding.tabUploadsLabel.setTypeface(null, if (!downloadsSelected) Typeface.BOLD else Typeface.NORMAL)
        binding.tabUploadsIndicator.setBackgroundColor(if (!downloadsSelected) black else Color.TRANSPARENT)

        if (downloadsSelected) {
            binding.transferEmptyIcon.setImageResource(R.drawable.ic_transfer_empty_download)
            binding.transferEmptyMessage.setText(R.string.transfer_empty_downloads)
        } else {
            binding.transferEmptyIcon.setImageResource(R.drawable.ic_transfer_empty_upload)
            binding.transferEmptyMessage.setText(R.string.transfer_empty_uploads)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_BANNER_VISIBLE, bannerVisible)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val KEY_BANNER_VISIBLE = "transfer_banner_visible"
    }
}

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
import com.example.terabox.databinding.FragmentMessageBinding

class MessageFragment : Fragment() {

    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!

    private val black by lazy { ContextCompat.getColor(requireContext(), R.color.black) }
    private val muted by lazy { ContextCompat.getColor(requireContext(), R.color.files_tab_muted) }
    private val tabBlue by lazy { ContextCompat.getColor(requireContext(), R.color.terabox_blue) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val host = activity as? DashboardActivity
        binding.messageButtonBack.setOnClickListener {
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
        binding.messageButtonAddFriend.setOnClickListener {
            Toast.makeText(requireContext(), R.string.content_desc_add_friend, Toast.LENGTH_SHORT).show()
        }

        binding.tabMessageContainer.setOnClickListener { selectTab(MessageTab.MESSAGE) }
        binding.tabFriendsContainer.setOnClickListener { selectTab(MessageTab.FRIENDS) }

        selectTab(MessageTab.MESSAGE)
    }

    private enum class MessageTab { MESSAGE, FRIENDS }

    private fun selectTab(tab: MessageTab) {
        val messageSelected = tab == MessageTab.MESSAGE

        binding.tabMessageLabel.setTextColor(if (messageSelected) black else muted)
        binding.tabMessageLabel.setTypeface(null, if (messageSelected) Typeface.BOLD else Typeface.NORMAL)
        binding.tabMessageIndicator.setBackgroundColor(if (messageSelected) tabBlue else Color.TRANSPARENT)

        binding.tabFriendsLabel.setTextColor(if (!messageSelected) black else muted)
        binding.tabFriendsLabel.setTypeface(null, if (!messageSelected) Typeface.BOLD else Typeface.NORMAL)
        binding.tabFriendsIndicator.setBackgroundColor(if (!messageSelected) tabBlue else Color.TRANSPARENT)

        binding.messageScrollContent.visibility =
            if (messageSelected) View.VISIBLE else View.GONE
        binding.friendsEmptySection.visibility =
            if (messageSelected) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

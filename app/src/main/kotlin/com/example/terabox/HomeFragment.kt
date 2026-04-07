package com.example.terabox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.terabox.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val host = activity as? DashboardActivity

        binding.buttonMenu.setOnClickListener { host?.openMainDrawer() }

        binding.buttonCalendar.setOnClickListener {
            Toast.makeText(requireContext(), R.string.content_desc_calendar, Toast.LENGTH_SHORT).show()
        }
        binding.buttonChat.setOnClickListener {
            host?.openMessageCenter()
        }
        binding.buttonSort.setOnClickListener {
            host?.openTransferList()
        }
        binding.buttonCameraSearch.setOnClickListener {
            Toast.makeText(requireContext(), R.string.content_desc_camera, Toast.LENGTH_SHORT).show()
        }
        binding.buttonUpgrade.setOnClickListener {
            Toast.makeText(requireContext(), R.string.upgrade, Toast.LENGTH_SHORT).show()
        }
        binding.buttonFilter.setOnClickListener {
            Toast.makeText(requireContext(), R.string.content_desc_filter, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

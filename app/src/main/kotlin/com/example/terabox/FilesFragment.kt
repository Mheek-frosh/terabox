package com.example.terabox

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.terabox.databinding.FragmentFilesBinding

class FilesFragment : Fragment() {

    private var _binding: FragmentFilesBinding? = null
    private val binding get() = _binding!!

    private val muted by lazy { ContextCompat.getColor(requireContext(), R.color.files_tab_muted) }
    private val black by lazy { ContextCompat.getColor(requireContext(), R.color.black) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val host = activity as? DashboardActivity

        binding.filesButtonMenu.setOnClickListener { host?.openMainDrawer() }
        binding.filesButtonSort.setOnClickListener {
            Toast.makeText(requireContext(), R.string.content_desc_sort, Toast.LENGTH_SHORT).show()
        }

        binding.tabAllContainer.setOnClickListener { selectTab(FilesTab.ALL) }
        binding.tabOfflineContainer.setOnClickListener { selectTab(FilesTab.OFFLINE) }
        binding.tabStarredContainer.setOnClickListener { selectTab(FilesTab.STARRED) }

        val adapter = FilesListAdapter(
            listOf(
                FilesListItem.Vault,
                FilesListItem.PdfDocument(
                    getString(R.string.files_sample_pdf_title),
                    getString(R.string.files_sample_pdf_meta)
                )
            )
        )
        binding.filesRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.filesRecycler.adapter = adapter

        selectTab(FilesTab.ALL)
    }

    private enum class FilesTab { ALL, OFFLINE, STARRED }

    private fun selectTab(tab: FilesTab) {
        val active = black
        val inactive = muted

        fun style(
            label: TextView,
            indicator: View,
            selected: Boolean
        ) {
            label.setTextColor(if (selected) active else inactive)
            label.setTypeface(null, if (selected) Typeface.BOLD else Typeface.NORMAL)
            indicator.setBackgroundColor(if (selected) black else Color.TRANSPARENT)
        }

        style(binding.tabAll, binding.tabIndicatorAll, tab == FilesTab.ALL)
        style(binding.tabOffline, binding.tabIndicatorOffline, tab == FilesTab.OFFLINE)
        style(binding.tabStarred, binding.tabIndicatorStarred, tab == FilesTab.STARRED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

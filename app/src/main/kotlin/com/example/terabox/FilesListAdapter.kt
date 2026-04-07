package com.example.terabox

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.terabox.databinding.ItemFilesPdfBinding
import com.example.terabox.databinding.ItemFilesVaultBinding

sealed class FilesListItem {
    data object Vault : FilesListItem()
    data class PdfDocument(val title: String, val meta: String) : FilesListItem()
}

class FilesListAdapter(
    private val items: List<FilesListItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is FilesListItem.Vault -> VIEW_TYPE_VAULT
        is FilesListItem.PdfDocument -> VIEW_TYPE_PDF
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_VAULT -> VaultVH(ItemFilesVaultBinding.inflate(inflater, parent, false))
            else -> PdfVH(ItemFilesPdfBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is FilesListItem.Vault -> {
                // Static layout only; no fields to bind.
            }
            is FilesListItem.PdfDocument -> (holder as PdfVH).bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    private class VaultVH(binding: ItemFilesVaultBinding) : RecyclerView.ViewHolder(binding.root)

    private class PdfVH(private val binding: ItemFilesPdfBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FilesListItem.PdfDocument) {
            binding.textPdfTitle.text = item.title
            binding.textPdfMeta.text = item.meta
        }
    }

    companion object {
        private const val VIEW_TYPE_VAULT = 0
        private const val VIEW_TYPE_PDF = 1
    }
}

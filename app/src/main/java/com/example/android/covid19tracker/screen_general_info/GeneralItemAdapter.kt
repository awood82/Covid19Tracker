package com.example.android.covid19tracker.screen_general_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.covid19tracker.R
import com.example.android.covid19tracker.databinding.GeneralItemBinding
import com.example.android.covid19tracker.domain.GeneralItemCard

class GeneralItemAdapter : RecyclerView.Adapter<GeneralItemViewHolder>() {

    // List of stats for general info
    var infoStats: List<GeneralItemCard> = emptyList()
        set(value) {
            field = value
            // Since the data set changes infrequently,
            // just update the whole list if something changes
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return infoStats.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralItemViewHolder {
        val withDataBinding: GeneralItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            GeneralItemViewHolder.LAYOUT,
            parent,
            false)
        return GeneralItemViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: GeneralItemViewHolder, position: Int) {
        holder.viewDataBinding.let {
            it.general = infoStats[position]
        }
    }
}


// general_item.xml binding automatically becomes GeneralItemBinding
class GeneralItemViewHolder(val viewDataBinding: GeneralItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.general_item
    }
}
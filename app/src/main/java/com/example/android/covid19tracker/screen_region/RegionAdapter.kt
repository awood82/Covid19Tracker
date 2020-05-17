package com.example.android.covid19tracker.screen_region

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.covid19tracker.R
import com.example.android.covid19tracker.databinding.RegionItemBinding
import com.example.android.covid19tracker.domain.RegionalStats

class RegionAdapter(val click: RegionClickListener) : RecyclerView.Adapter<RegionalStatsViewHolder>() {

    // List of stats for each country
    var countryStats: List<RegionalStats> = emptyList()
        set(value) {
            field = value
            // Since the data set changes infrequently,
            // just update the whole list if something changes
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return countryStats.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionalStatsViewHolder {
        val withDataBinding: RegionItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            RegionalStatsViewHolder.LAYOUT,
            parent,
            false)
        return RegionalStatsViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: RegionalStatsViewHolder, position: Int) {
        holder.viewDataBinding.let {
            it.region = countryStats[position]
            it.clickListener = click
        }
    }
}


// region_item.xml binding automatically becomes RegionItemBinding
class RegionalStatsViewHolder(val viewDataBinding: RegionItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.region_item
    }
}

class RegionClickListener(val item: (RegionalStats) -> Unit) {
    fun onClick(region: RegionalStats) = item(region)
}
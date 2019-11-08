package com.lcmobile.kotlinbestpractices.app

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MainAdapter : RecyclerView.Adapter<MagicNumberHolder>() {

    var data: List<MagicNumber> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MagicNumberHolder(parent)

    override fun onBindViewHolder(holder: MagicNumberHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

}
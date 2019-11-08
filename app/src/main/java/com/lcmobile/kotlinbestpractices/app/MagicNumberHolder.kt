
package com.lcmobile.kotlinbestpractices.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lcmobile.kotlinbestpractices.R
import kotlinx.android.synthetic.main.simple_list_item_1.view.*

class MagicNumberHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.simple_list_item_1, parent, false)
) {

    fun bind(item: MagicNumber) {
        with(itemView) {
            magicNumber.text = item.number
        }
    }
}
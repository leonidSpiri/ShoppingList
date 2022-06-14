package spiridonov.shoppinglist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import spiridonov.shoppinglist.R

class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val txtName: TextView = view.findViewById(R.id.txt_name)
    val txtCount: TextView = view.findViewById(R.id.txt_count)
}
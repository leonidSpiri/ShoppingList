package spiridonov.shoppinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import spiridonov.shoppinglist.R
import spiridonov.shoppinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {
    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.txt_name)
        val txtCount: TextView = view.findViewById(R.id.txt_count)
    }

    var count = 1
    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onShopItemLongClickListener: ((ShopItem) ->Unit)? = null
    var onShopItemClickListener: ((ShopItem) ->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        Log.d("hphh", "${count++}")
        val layoutID =
            when (viewType) {
                SHOP_ITEM_ENABLED -> R.layout.item_shop_enabled
                SHOP_ITEM_DISABLED -> R.layout.item_shop_disabled
                else -> throw RuntimeException("Unknown view type: $viewType")
            }
        return ShopItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(layoutID, parent, false)
        )
    }

    override fun getItemViewType(position: Int): Int =
        if (shopList[position].isEnabled) SHOP_ITEM_ENABLED
        else SHOP_ITEM_DISABLED


    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        with(holder) {
            txtName.text = shopItem.name
            txtCount.text = shopItem.count.toString()
            view.setOnLongClickListener {
                onShopItemLongClickListener?.invoke(shopItem)
                true
            }
            view.setOnClickListener {
                onShopItemClickListener?.invoke(shopItem)
            }
        }
    }

    override fun getItemCount(): Int = shopList.size

    companion object {
        const val SHOP_ITEM_ENABLED = 1
        const val SHOP_ITEM_DISABLED = 0
        const val MAX_PULL_SIZE = 10
    }
}
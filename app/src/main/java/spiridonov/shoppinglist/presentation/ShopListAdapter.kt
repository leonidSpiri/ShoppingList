package spiridonov.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import spiridonov.shoppinglist.R
import spiridonov.shoppinglist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
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
        if (getItem(position).isEnabled) SHOP_ITEM_ENABLED
        else SHOP_ITEM_DISABLED


    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
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

    companion object {
        const val SHOP_ITEM_ENABLED = 1
        const val SHOP_ITEM_DISABLED = 0
        const val MAX_PULL_SIZE = 10
    }
}
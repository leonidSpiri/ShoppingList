package spiridonov.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import spiridonov.shoppinglist.R
import spiridonov.shoppinglist.databinding.ItemShopDisabledBinding
import spiridonov.shoppinglist.databinding.ItemShopEnabledBinding
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
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layoutID,
            parent,
            false
        )
        return ShopItemViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int =
        if (getItem(position).isEnabled) SHOP_ITEM_ENABLED
        else SHOP_ITEM_DISABLED


    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = holder.binding
        when (binding) {
            is ItemShopDisabledBinding -> {
                binding.shopItem = shopItem
            }
            is ItemShopEnabledBinding -> {
                binding.shopItem = shopItem
            }
        }
        with(binding.root) {
            setOnLongClickListener {
                onShopItemLongClickListener?.invoke(shopItem)
                true
            }
            setOnClickListener {
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
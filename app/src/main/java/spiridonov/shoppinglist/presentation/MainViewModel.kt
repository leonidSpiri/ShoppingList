package spiridonov.shoppinglist.presentation

import android.app.Application
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import spiridonov.shoppinglist.domain.DeleteShopItemUseCase
import spiridonov.shoppinglist.domain.EditShopItemUseCase
import spiridonov.shoppinglist.domain.GetShopListUseCase
import spiridonov.shoppinglist.domain.ShopItem
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val application: Application,
    getShopListUseCase: GetShopListUseCase,
    private val deleteShopItemUseCase: DeleteShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase
) : ViewModel() {

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        CoroutineScope(Dispatchers.Default).launch {
            //deleteShopItemUseCase.deleteShopItem(shopItem)
            application.contentResolver.delete(
                Uri.parse("content://spiridonov.shoppinglist/shop_items"),
                null,
                arrayOf(shopItem.id.toString())
            )
        }
    }

    fun changeEnableState(shopItem: ShopItem) {
        viewModelScope.launch {
            val newItem = shopItem.copy(isEnabled = !shopItem.isEnabled)
            editShopItemUseCase.editShopItem(newItem)
        }
    }
}
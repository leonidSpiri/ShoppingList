package spiridonov.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import spiridonov.shoppinglist.data.ShopListRepositoryImpl
import spiridonov.shoppinglist.domain.DeleteShopItemUseCase
import spiridonov.shoppinglist.domain.EditShopItemUseCase
import spiridonov.shoppinglist.domain.GetShopListUseCase
import spiridonov.shoppinglist.domain.ShopItem

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ShopListRepositoryImpl(application)
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val scope  = CoroutineScope(Dispatchers.Default)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
           scope.launch {
        deleteShopItemUseCase.deleteShopItem(shopItem)
          }
    }

    fun changeEnableState(shopItem: ShopItem) {
          scope.launch {
        val newItem = shopItem.copy(isEnabled = !shopItem.isEnabled)
        editShopItemUseCase.editShopItem(newItem)
         }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}
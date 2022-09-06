package spiridonov.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
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

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
           viewModelScope.launch {
        deleteShopItemUseCase.deleteShopItem(shopItem)
          }
    }

    fun changeEnableState(shopItem: ShopItem) {
        viewModelScope.launch {
        val newItem = shopItem.copy(isEnabled = !shopItem.isEnabled)
        editShopItemUseCase.editShopItem(newItem)
         }
    }
}
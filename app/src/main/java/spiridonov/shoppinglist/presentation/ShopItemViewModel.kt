package spiridonov.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import spiridonov.shoppinglist.data.ShopListRepositoryImpl
import spiridonov.shoppinglist.domain.AddShopItemUseCase
import spiridonov.shoppinglist.domain.EditShopItemUseCase
import spiridonov.shoppinglist.domain.GetShopItemUseCase
import spiridonov.shoppinglist.domain.ShopItem

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ShopListRepositoryImpl(application)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val addShopListUseCase = AddShopItemUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val scope  = CoroutineScope(Dispatchers.IO)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getShopItem(shopItemId: Int) {
        scope.launch {
        val shopItem = getShopItemUseCase.getShopItem(shopItemId)
        _shopItem.value = shopItem
         }

    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseInt(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
               scope.launch{
            val shopItem = ShopItem(name, count, true)
            addShopListUseCase.addShopItem(shopItem)
            finishWork()
               }
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseInt(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            _shopItem.value?.let {
                   scope.launch {
                val newItem = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(newItem)
                finishWork()
                 }
            }
        }
    }

    private fun parseName(inputName: String?): String = inputName?.trim() ?: ""
    private fun parseInt(inputCount: String?): Int = try {
        inputCount?.trim()?.toInt() ?: 0
    } catch (e: Exception) {
        0
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}
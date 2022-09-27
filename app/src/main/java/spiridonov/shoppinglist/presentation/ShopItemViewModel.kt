package spiridonov.shoppinglist.presentation

import android.app.Application
import android.content.ContentValues
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import spiridonov.shoppinglist.domain.AddShopItemUseCase
import spiridonov.shoppinglist.domain.EditShopItemUseCase
import spiridonov.shoppinglist.domain.GetShopItemUseCase
import spiridonov.shoppinglist.domain.ShopItem
import javax.inject.Inject
import kotlin.concurrent.thread

class ShopItemViewModel @Inject constructor(
    private val application: Application,
    private val editShopItemUseCase: EditShopItemUseCase,
    private val addShopListUseCase: AddShopItemUseCase,
    private val getShopItemUseCase: GetShopItemUseCase
) : ViewModel() {

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
        viewModelScope.launch {
            val shopItem = getShopItemUseCase.getShopItem(shopItemId)
            _shopItem.value = shopItem
        }
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseInt(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            viewModelScope.launch {
                thread {
                    application.contentResolver?.insert(
                        Uri.parse("content://spiridonov.shoppinglist/shop_items"),
                        ContentValues().apply {
                            put("id", 0)
                            put("name", name)
                            put("count", count)
                            put("isEnabled", true)
                        }
                    )
                }
                //val shopItem = ShopItem(name, count, true)
                // addShopListUseCase.addShopItem(shopItem)
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
                viewModelScope.launch {
                    val newItem = it.copy(name = name, count = count)
                    editShopItemUseCase.editShopItem(newItem)
                    finishWork()
                }
            }
        }
    }

    private fun parseName(inputName: String?) = inputName?.trim() ?: ""
    private fun parseInt(inputCount: String?) = try {
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
}
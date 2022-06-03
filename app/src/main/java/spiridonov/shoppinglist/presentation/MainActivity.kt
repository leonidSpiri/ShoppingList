package spiridonov.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import spiridonov.shoppinglist.R
import spiridonov.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        var list: List<ShopItem>
        viewModel.shopList.observe(this) {
            Log.d("MainActivityTest", it.toString())
            list = viewModel.shopList.value!!
        }
        list = viewModel.shopList.value!!
        viewModel.deleteShopItem(list[0])
        list = viewModel.shopList.value!!
        viewModel.changeEnableState(list[0])

    }
}
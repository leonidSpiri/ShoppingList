package spiridonov.shoppinglist

import android.app.Application
import spiridonov.shoppinglist.di.DaggerApplicationComponent

class ShopApp : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }
}
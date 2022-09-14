package spiridonov.shoppinglist.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import spiridonov.shoppinglist.ShopApp
import spiridonov.shoppinglist.presentation.MainActivity
import spiridonov.shoppinglist.presentation.ShopItemFragment

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: ShopItemFragment)
    fun inject(application: ShopApp)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}
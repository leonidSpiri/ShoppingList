package spiridonov.shoppinglist.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import spiridonov.shoppinglist.data.AppDatabase
import spiridonov.shoppinglist.data.ShopListDao
import spiridonov.shoppinglist.data.ShopListRepositoryImpl
import spiridonov.shoppinglist.domain.ShopListRepository

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindCurrencyRepository(impl: ShopListRepositoryImpl): ShopListRepository

    companion object {
        @Provides
        @ApplicationScope
        fun provideCurrListDao(
            application: Application
        ): ShopListDao {
            return AppDatabase.getInstance(application).shopListDao()
        }
    }
}
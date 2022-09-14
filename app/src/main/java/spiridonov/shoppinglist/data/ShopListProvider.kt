package spiridonov.shoppinglist.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import javax.inject.Inject

class ShopListProvider: ContentProvider() {
    @Inject
    lateinit var shopListDao: ShopListDao
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("spiridonov.shoppinglist", "shop_items", GET_SHOP_ITEMS_QUERY)
        addURI("spiridonov.shoppinglist", "shop_items/*", GET_SHOP_ITEM_BY_ID_QUERY)
    }

    override fun onCreate() = true

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        return when (val code = uriMatcher.match(p0)){
            GET_SHOP_ITEMS_QUERY -> {
                Log.d("ShopListProvider", "query uri: $p0 code $code" )
                //val db = ShopListDatabase.getInstance(context!!)
                null
            }
            GET_SHOP_ITEM_BY_ID_QUERY -> {
                Log.d("ShopListProvider", "query uri: $p0 code $code" )
                //val db = ShopListDatabase.getInstance(context!!)
                null
            }
            else -> null
        }
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    companion object{
        private const val GET_SHOP_ITEMS_QUERY = 1
        private const val GET_SHOP_ITEM_BY_ID_QUERY = 2
    }
}
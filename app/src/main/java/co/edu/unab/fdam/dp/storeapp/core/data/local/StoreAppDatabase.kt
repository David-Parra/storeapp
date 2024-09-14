package co.edu.unab.fdam.dp.storeapp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import co.edu.unab.fdam.dp.storeapp.core.data.local.dao.ProductDAO
import co.edu.unab.fdam.dp.storeapp.core.data.local.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class StoreAppDatabase : RoomDatabase() {
    abstract fun productDAO(): ProductDAO
}
package co.edu.unab.fdam.dp.storeapp.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = System.currentTimeMillis().hashCode(),
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "description") val description: String = "without Description",
    @ColumnInfo(name = "image") val image: String = "https://www.libreriahuequito.com/public/images/productos/default.png",
) {
    //empty constructor
    constructor() : this(0, "", 0)
}
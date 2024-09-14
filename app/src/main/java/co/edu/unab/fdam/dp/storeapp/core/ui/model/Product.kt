package co.edu.unab.fdam.dp.storeapp.core.ui.model

import co.edu.unab.fdam.dp.storeapp.core.data.local.entity.ProductEntity


//@Parcelize
//@Serializable
open class Product(
    var id: Int,  // Unique identifier for each product. In a real-world scenario, this would be a database ID.
    var name: String,
    var price: Int,
    var description: String = "Sin descripción",
    var image: String = "https://www.libreriahuequito.com/public/images/productos/default.png"
)
//  : Parcelable
{

    //empty constructor
    constructor() : this(0, "", 0)

    override fun toString(): String {
        return "id: $id name: $name, price: $price, description: $description and image: $image"
    }

}

//extension
fun ProductEntity.toProduct(): Product = Product(
    id = this.id ?: 0,
    name = this.name,
    price = this.price,
    image = this.image,
    description = this.description,
)

fun Product.toProductEntity(): ProductEntity = ProductEntity(
    id = this.id,
    name = this.name,
    price = this.price,
    image = this.image,
    description = this.description,
)
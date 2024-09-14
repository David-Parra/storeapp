package co.edu.unab.fdam.dp.storeapp.core.ui.model

class ProductDiscount(id: Int, name: String, price: Int, var discount: Int) :
    Product(id, name, price) {
}
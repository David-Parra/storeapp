package co.edu.unab.fdam.dp.storeapp.home.data.repository

import co.edu.unab.fdam.dp.storeapp.core.data.local.dao.ProductDAO
import co.edu.unab.fdam.dp.storeapp.core.data.network.FirebaseClient
import co.edu.unab.fdam.dp.storeapp.core.ui.model.Product
import co.edu.unab.fdam.dp.storeapp.core.ui.model.toProduct
import co.edu.unab.fdam.dp.storeapp.core.ui.model.toProductEntity
import co.edu.unab.fdam.dp.storeapp.product.datasource.ProductAPIDatasource
import co.edu.unab.fdam.dp.storeapp.product.datasource.ProductFirestoreDatasource
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val COLLECTION_NAME_PRODUCTS = "products"

class HomeRepository @Inject constructor(
    private val productDAO: ProductDAO,
    private val productFirestoreDatasource: ProductFirestoreDatasource,
    private val productAPIDatasource: ProductAPIDatasource,
) {
    val products: Flow<List<Product>> = productDAO.getAllProducts().map { items ->
        items.map {
            it.toProduct()
        }
    }

    //Firestore
    fun productsFirestore(): Flow<List<Product>> {
        return productFirestoreDatasource.getAll()
    }

    fun saveProductFirestore(product: Product) {
        productFirestoreDatasource.add(product)
    }

    fun deleteProductFirestore(product: Product) {
        productFirestoreDatasource.delete(product)
    }

    //API
    fun productsAPI(): Flow<List<Product>> {
        return productAPIDatasource.getAll()
    }

    suspend fun saveProductAPI(product: Product) {
        productAPIDatasource.add(product)
    }

    suspend fun deleteProductAPI(product: Product) {
        productAPIDatasource.delete(product)
    }

    suspend fun saveProduct(product: Product) {
        productDAO.addProduct(product.toProductEntity())
    }

    suspend fun saveProducts(products: List<Product>) {
        productDAO.addProducts(products.map { it.toProductEntity() })
    }

    suspend fun deleteProduct(product: Product) {
        productDAO.deleteProduct(product.toProductEntity())
    }

    suspend fun deleteAllProducts() {
        productDAO.deleteAllProducts()
    }
}
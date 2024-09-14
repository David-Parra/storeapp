package co.edu.unab.fdam.dp.storeapp.product.data.repository

import androidx.lifecycle.map
import co.edu.unab.fdam.dp.storeapp.core.data.local.dao.ProductDAO
import co.edu.unab.fdam.dp.storeapp.core.ui.model.toProduct
import co.edu.unab.fdam.dp.storeapp.product.datasource.ProductAPIDatasource
import co.edu.unab.fdam.dp.storeapp.product.datasource.ProductFirestoreDatasource
import javax.inject.Inject

class ProductDetailRepository @Inject constructor(
    private val productDAO: ProductDAO,
    private val firestoreDatasource: ProductFirestoreDatasource,
    private val productAPIDataSource: ProductAPIDatasource,
) {
    fun getProductById(productId: Int) = productDAO.getProductById(productId = productId)
        .map { item -> item.toProduct() }

    fun getProductByIdFirestore(productId: Int) = firestoreDatasource.getById(productId)

    fun getProductByIdAPI(productId: Int) = productAPIDataSource.getById(productId)

}
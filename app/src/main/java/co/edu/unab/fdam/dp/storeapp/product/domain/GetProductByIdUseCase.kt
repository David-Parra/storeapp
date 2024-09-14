package co.edu.unab.fdam.dp.storeapp.product.domain

import androidx.lifecycle.LiveData
import co.edu.unab.fdam.dp.storeapp.core.ui.model.Product
import co.edu.unab.fdam.dp.storeapp.product.data.repository.ProductDetailRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val productRepository: ProductDetailRepository
) {
    operator fun invoke(id: Int): LiveData<Product> =
        productRepository.getProductByIdFirestore(id)
    //productRepository.getProductByIdAPI(id)
}
package co.edu.unab.fdam.dp.storeapp.updateproduct.domain

import co.edu.unab.fdam.dp.storeapp.core.ui.model.Product
import co.edu.unab.fdam.dp.storeapp.updateproduct.data.repository.UpdateProductRepository
import javax.inject.Inject

class ProductUpdateUseCase @Inject constructor(
    private val productRepository: UpdateProductRepository
) {
    suspend operator fun invoke(product: Product) {
        productRepository.updateProductFirestore(product)
    }
}
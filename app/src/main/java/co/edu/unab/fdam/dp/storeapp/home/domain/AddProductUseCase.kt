package co.edu.unab.fdam.dp.storeapp.home.domain

import co.edu.unab.fdam.dp.storeapp.home.data.repository.HomeRepository
import co.edu.unab.fdam.dp.storeapp.core.ui.model.Product
import javax.inject.Inject

class AddProductUseCase @Inject constructor(
    private val productRepository: HomeRepository
) {
    suspend operator fun invoke(product: Product) {
        //productRepository.saveProductFirestore(product)
        //productRepository.saveProduct(product)
        productRepository.saveProductAPI(product)
    }
}
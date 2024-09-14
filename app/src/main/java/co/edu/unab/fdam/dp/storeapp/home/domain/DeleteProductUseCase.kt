package co.edu.unab.fdam.dp.storeapp.home.domain

import co.edu.unab.fdam.dp.storeapp.home.data.repository.HomeRepository
import co.edu.unab.fdam.dp.storeapp.core.ui.model.Product
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(product: Product) =
    //homeRepository.deleteProduct(product)
        //homeRepository.deleteProductFirestore(product)
        homeRepository.deleteProductAPI(product)
}
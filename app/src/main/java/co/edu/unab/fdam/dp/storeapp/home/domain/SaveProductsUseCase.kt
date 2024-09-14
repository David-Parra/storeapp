package co.edu.unab.fdam.dp.storeapp.home.domain

import co.edu.unab.fdam.dp.storeapp.home.data.repository.HomeRepository
import co.edu.unab.fdam.dp.storeapp.core.ui.model.Product
import javax.inject.Inject

class SaveProductsUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(products: List<Product>) {
        homeRepository.saveProducts(products)
    }
}
package co.edu.unab.fdam.dp.storeapp.home.domain

import co.edu.unab.fdam.dp.storeapp.home.data.repository.HomeRepository
import javax.inject.Inject

class DeleteAllProductsUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    suspend operator fun invoke() {
        homeRepository.deleteAllProducts()
    }
}
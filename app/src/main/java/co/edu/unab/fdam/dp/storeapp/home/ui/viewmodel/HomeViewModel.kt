package co.edu.unab.fdam.dp.storeapp.home.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.unab.fdam.dp.storeapp.home.domain.DeleteAllProductsUseCase
import co.edu.unab.fdam.dp.storeapp.home.domain.DeleteProductUseCase
import co.edu.unab.fdam.dp.storeapp.home.domain.GetProductsUseCase
import co.edu.unab.fdam.dp.storeapp.home.ui.ProductsUIState
import co.edu.unab.fdam.dp.storeapp.home.ui.ProductsUIState.Success
import co.edu.unab.fdam.dp.storeapp.core.ui.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val deleteAllProductsUseCase: DeleteAllProductsUseCase,
) : ViewModel() {

    //val productList: Flow<List<Product>> = getProductsUseCase()

    val uiState: StateFlow<ProductsUIState> = getProductsUseCase().map(::Success)
        .catch { throwable ->
            ProductsUIState.Error(throwable)
        }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000),
            ProductsUIState.Loading
        )

    fun deleteProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteProductUseCase(product)
        }
    }

    fun deleteAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAllProductsUseCase()
        }
    }
}
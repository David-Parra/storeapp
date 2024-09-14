package co.edu.unab.fdam.dp.storeapp.updateproduct.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.unab.fdam.dp.storeapp.core.ui.model.Product
import co.edu.unab.fdam.dp.storeapp.product.domain.GetProductByIdUseCase
import co.edu.unab.fdam.dp.storeapp.updateproduct.domain.ProductUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductUpdateViewModel @Inject constructor(
    private val updateProductUseCase: ProductUpdateUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
) : ViewModel() {
    fun updateProduct(
        id: Int,
        name: String,
        price: String,
        description: String,
        image: String
    ) {
        val updateProduct: Product = Product(
            id = id,
            name = name,
            description = description,
            image = image,
            price = price.toInt(),
        )
        viewModelScope.launch(Dispatchers.IO) {
            updateProductUseCase(updateProduct)
        }
    }

    fun getProductById(id: Int): LiveData<Product> = getProductByIdUseCase(id)
}
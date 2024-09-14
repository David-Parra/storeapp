package co.edu.unab.fdam.dp.storeapp.product.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.unab.fdam.dp.storeapp.core.ui.model.Product
import co.edu.unab.fdam.dp.storeapp.home.domain.AddProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductRegisterViewModel @Inject constructor(private val addProductUseCase: AddProductUseCase) :
    ViewModel() {

    fun addProduct(name: String, price: String, description: String, image: String) {
        val product = Product(
            id = System.currentTimeMillis()
                .hashCode(),  // auto generated id, replace with actual id when saving to the database.
            name = name,
            price = price.toInt(),
            description = description,
            image = image,
        )
        viewModelScope.launch(Dispatchers.IO) {
            addProductUseCase(product)
        }
    }

}
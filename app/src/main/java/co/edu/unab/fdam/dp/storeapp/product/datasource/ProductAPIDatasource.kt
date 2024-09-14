package co.edu.unab.fdam.dp.storeapp.product.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.edu.unab.fdam.dp.storeapp.core.data.local.entity.ProductEntity
import co.edu.unab.fdam.dp.storeapp.core.data.network.service.ProductService
import co.edu.unab.fdam.dp.storeapp.core.ui.model.Product
import co.edu.unab.fdam.dp.storeapp.core.ui.model.toProduct
import co.edu.unab.fdam.dp.storeapp.core.ui.model.toProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.await
import javax.inject.Inject

class ProductAPIDatasource @Inject constructor(private val productService: ProductService) {
    fun getAll(): Flow<List<Product>> {
        return flow {
            try {
                val products: MutableList<Product> = arrayListOf()
                val data = productService.getAll().await()
                data.forEach { (_, productEntity) -> products.add(productEntity.toProduct()) }
                emit(products)
            } catch (e: Exception) {
                println("Error fetching products from API ${e.message}")
                emit(emptyList())
            }
        }
    }

    //get by id
    fun getById(id: Int): LiveData<Product> {
        val product = MutableLiveData<Product>()
        productService.getById(id).enqueue(object : retrofit2.Callback<ProductEntity> {
            override fun onResponse(
                call: retrofit2.Call<ProductEntity>,
                response: retrofit2.Response<ProductEntity>
            ) {
                if (response.isSuccessful) {
                    val productEntity = response.body()
                    if (productEntity != null) {
                        product.postValue(productEntity.toProduct())
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<ProductEntity>, t: Throwable) {
                println("Error fetching product by ID from API ${t.message}")
                //product.postValue(null)
            }
        })
        return product
        /*        return flow {
                try {
                    val productEntity = productService.getById(id).await()
                    emit(productEntity.toProduct())
                } catch (e: Exception) {
                    println("Error fetching product by ID from API ${e.message}")
                    emit(null)
                }
            }*/
    }

    //add
    suspend fun add(product: Product) {
        try {
            val addProduct = product.toProductEntity()
            productService.add(addProduct.id, addProduct).await()
        } catch (e: Exception) {
            println("Error adding product to API ${e.message}")
        }
    }

    //update
    suspend fun update(product: Product) {
        try {
            val updateProduct = product.toProductEntity()
            productService.update(updateProduct.id, updateProduct).await()
        } catch (e: Exception) {
            println("Error updating product to API ${e.message}")
        }
    }

    //delete
    suspend fun delete(product: Product) {
        try {
            val deleteProduct = product.toProductEntity()
            productService.delete(deleteProduct.id).await()
        } catch (e: Exception) {
            println("Error deleting product from API ${e.message}")
        }
    }

}
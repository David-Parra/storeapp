package co.edu.unab.fdam.dp.storeapp.product.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.edu.unab.fdam.dp.storeapp.core.data.local.entity.ProductEntity
import co.edu.unab.fdam.dp.storeapp.core.data.network.FirebaseClient
import co.edu.unab.fdam.dp.storeapp.core.ui.model.Product
import co.edu.unab.fdam.dp.storeapp.core.ui.model.toProduct
import co.edu.unab.fdam.dp.storeapp.core.ui.model.toProductEntity
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

private const val COLLECTION_NAME_PRODUCTS = "products"

class ProductFirestoreDatasource @Inject constructor(private val firebaseClient: FirebaseClient) {
    fun getAll(): Flow<List<Product>> {
        return callbackFlow {
            val query = firebaseClient.firestoreDB.collection(COLLECTION_NAME_PRODUCTS)
            val subscription = query.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val documents = snapshot.documents.map { document ->
                        document.toObject(ProductEntity::class.java)!!
                    }
                    trySend(documents.map { productEntity -> productEntity.toProduct() })
                }
            }
            awaitClose {
                subscription.remove()
            }
        }
    }

    fun getById(id: Int): LiveData<Product> {
        val product = MutableLiveData<Product>()
        firebaseClient.firestoreDB.collection(COLLECTION_NAME_PRODUCTS)
            .document(id.toString())
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    //product.postValue()
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    val productEntity = snapshot.toObject(ProductEntity::class.java)!!.toProduct()
                    productEntity.id = id
                    product.postValue(productEntity)
                }
            }
        return product
    }

    fun add(product: Product) {
        val productEntity = product.toProductEntity()
        firebaseClient.firestoreDB.collection(COLLECTION_NAME_PRODUCTS)
            .document(productEntity.id.toString())
            .set(productEntity)
    }

    fun update(product: Product) {
        val productEntity = product.toProductEntity().copy(id = product.id)
        firebaseClient.firestoreDB.collection(COLLECTION_NAME_PRODUCTS)
            .document(productEntity.id.toString())
            .set(productEntity)
    }

    fun delete(product: Product) {
        val productEntity = product.toProductEntity()
        firebaseClient.firestoreDB.collection(COLLECTION_NAME_PRODUCTS)
            .document(productEntity.id.toString())
            .delete()
    }
}
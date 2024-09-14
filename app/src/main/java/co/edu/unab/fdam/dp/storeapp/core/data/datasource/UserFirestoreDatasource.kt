package co.edu.unab.fdam.dp.storeapp.core.data.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.edu.unab.fdam.dp.storeapp.core.data.network.FirebaseClient
import co.edu.unab.fdam.dp.storeapp.core.data.network.entity.UserEntity
import co.edu.unab.fdam.dp.storeapp.core.ui.model.User
import co.edu.unab.fdam.dp.storeapp.core.ui.model.toUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

private const val COLLECTION_NAME_USERS = "users"

class UserFirestoreDatasource @Inject constructor(private val firebaseClient: FirebaseClient) {
    fun getAll(): Flow<List<User>> {
        return callbackFlow {
            val query = firebaseClient.firestoreDB.collection(COLLECTION_NAME_USERS)
            val subscription = query.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val users = snapshot.documents.map { user ->
                        user.toObject(UserEntity::class.java)!!
                    }
                    trySend(users.map { userEntity -> userEntity.toUser() })
                }
            }
            awaitClose {
                subscription.remove()
            }
        }
    }

    /*    fun getUserById(userId: String): Flow<User> {
            try {
                return callbackFlow {
                    val query =
                        firebaseClient.firestoreDB.collection(COLLECTION_NAME_USERS).document(userId)
                    val subscription = query.addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            close(error)
                            return@addSnapshotListener
                        }
                        if (snapshot != null && snapshot.exists()) {
                            val userEntity = snapshot.toObject(UserEntity::class.java)!!
                            trySend(userEntity.toUser())
                        }
                    }
                    awaitClose {
                        subscription.remove()
                    }
                }
            } catch (e: Exception) {
                println("Error getting user by id: ${e.message}")
                return emptyFlow()
            }

        }*/

    //getUserById with LiveData
    fun getUserById(userId: String): LiveData<User> {
        val user = MutableLiveData<User>()
        firebaseClient.firestoreDB.collection(COLLECTION_NAME_USERS)
            .document(userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    //user.postValue()
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    val userEntity = snapshot.toObject(UserEntity::class.java)!!
                    userEntity.id = userId
                    user.postValue(userEntity.toUser())
                }
            }
        return user
    }
}
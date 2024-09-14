package co.edu.unab.fdam.dp.storeapp.login.model.repository

import co.edu.unab.fdam.dp.storeapp.core.data.network.FirebaseClient
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepository @Inject constructor(private val firebaseClient: FirebaseClient) {
    suspend operator fun invoke(email: String, password: String): AuthResult? {
        var result: AuthResult? = null
        try {
            result = firebaseClient.auth.signInWithEmailAndPassword(email, password).await()
        } catch (e: Exception) {
            println("Error signing in with email and password ${e.message}")
        }
        return result
    }
}
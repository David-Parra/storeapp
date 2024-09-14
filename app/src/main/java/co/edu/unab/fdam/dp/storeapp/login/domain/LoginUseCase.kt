package co.edu.unab.fdam.dp.storeapp.login.domain

import co.edu.unab.fdam.dp.storeapp.login.model.repository.LoginRepository
import co.edu.unab.fdam.dp.storeapp.login.ui.LoginUIState
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(email: String, password: String): AuthResult? {
        val loginResult = loginRepository(email, password)
        return loginResult
    }
}
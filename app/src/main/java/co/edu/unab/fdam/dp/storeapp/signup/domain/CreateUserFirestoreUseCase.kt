package co.edu.unab.fdam.dp.storeapp.signup.domain

import co.edu.unab.fdam.dp.storeapp.core.ui.model.User
import co.edu.unab.fdam.dp.storeapp.signup.data.repository.SignUpRepository
import javax.inject.Inject

class CreateUserFirestoreUseCase @Inject constructor(private val signUpRepository: SignUpRepository) {
    suspend operator fun invoke(user: User): Boolean {
        return signUpRepository.createUserFirestore(user)
    }
}
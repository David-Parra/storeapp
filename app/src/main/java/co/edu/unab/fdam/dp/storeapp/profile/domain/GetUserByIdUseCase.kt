package co.edu.unab.fdam.dp.storeapp.profile.domain

import androidx.lifecycle.LiveData
import co.edu.unab.fdam.dp.storeapp.core.ui.model.User
import co.edu.unab.fdam.dp.storeapp.user.data.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(private val repository: UsersRepository) {
    operator fun invoke(userId: String): LiveData<User> =
        repository.getUserByIdFirestore(userId)
}
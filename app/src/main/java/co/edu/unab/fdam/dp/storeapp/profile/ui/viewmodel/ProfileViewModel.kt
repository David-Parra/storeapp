package co.edu.unab.fdam.dp.storeapp.profile.ui.viewmodel

import android.content.Context
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.unab.fdam.dp.storeapp.R
import co.edu.unab.fdam.dp.storeapp.core.ui.model.User
import co.edu.unab.fdam.dp.storeapp.home.ui.ProductsUIState
import co.edu.unab.fdam.dp.storeapp.login.ui.LoginUIState
import co.edu.unab.fdam.dp.storeapp.profile.domain.GetUserByIdUseCase
import co.edu.unab.fdam.dp.storeapp.profile.ui.ProfileUIState
import co.edu.unab.fdam.dp.storeapp.profile.ui.ProfileUIState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val getUserByIdUseCase: GetUserByIdUseCase) :
    ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun getUserById(id: String): LiveData<User> = getUserByIdUseCase(id)


    /*    fun getUserById(id: String) {
            val userFlow: Flow<User> = getUserByIdUseCase(id)
            userFlow.map {
                Success(it)
            }.catch { throwable ->
                _uiState.emit(ProfileUIState.Error(throwable))
            }.stateIn(
                viewModelScope, SharingStarted.WhileSubscribed(5000),
                ProfileUIState.Loading
            )
        }*/

}
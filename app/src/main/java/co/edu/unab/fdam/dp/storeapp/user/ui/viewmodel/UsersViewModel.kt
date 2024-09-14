package co.edu.unab.fdam.dp.storeapp.user.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.unab.fdam.dp.storeapp.core.ui.model.User
import co.edu.unab.fdam.dp.storeapp.home.ui.ProductsUIState
import co.edu.unab.fdam.dp.storeapp.user.domain.GetUsersUseCase
import co.edu.unab.fdam.dp.storeapp.user.ui.UsersUIState
import co.edu.unab.fdam.dp.storeapp.user.ui.UsersUIState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
) : ViewModel() {
    val uiState: StateFlow<UsersUIState> = getUsersUseCase().map(::Success)
        .catch { throwable ->
            UsersUIState.Error(throwable)
        }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000),
            UsersUIState.Loading
        )
}
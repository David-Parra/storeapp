package co.edu.unab.fdam.dp.storeapp.profile.ui

import co.edu.unab.fdam.dp.storeapp.core.ui.model.User

sealed interface ProfileUIState {
    //extras
    data object Idle : ProfileUIState
    data object Empty : ProfileUIState

    //commons
    data object Loading : ProfileUIState
    data class Success(val user: User) : ProfileUIState
    data class Error(val throwable: Throwable) : ProfileUIState
}
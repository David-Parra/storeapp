package co.edu.unab.fdam.dp.storeapp.login.ui

sealed interface LoginUIState {
    //extras
    data object Idle : LoginUIState
    data object Empty : LoginUIState

    //commons
    data object Default : LoginUIState
    data object Loading : LoginUIState
    data class Success(val uid: String) : LoginUIState
    data class Error(val throwable: Throwable) : LoginUIState
}
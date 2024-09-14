package co.edu.unab.fdam.dp.storeapp.login.ui.viewmodel

import co.edu.unab.fdam.dp.storeapp.core.ui.model.User
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.unab.fdam.dp.storeapp.login.domain.LoginUseCase
import co.edu.unab.fdam.dp.storeapp.login.ui.LoginUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _uiState = MutableStateFlow<LoginUIState>(LoginUIState.Default)
    val uiState: StateFlow<LoginUIState> = _uiState

    init {
        _email.value = ""
        _password.value = ""
    }

    fun onEmailChanged(email: String) {
        _email.value = email
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
    }

    fun onResetState() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.emit(LoginUIState.Default)
        }
    }


    fun verifyLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            val authResult = loginUseCase(
                email = email.value!!,
                password = password.value!!,
            )
            if (authResult != null && authResult.user != null) {
                val uid = authResult.user!!.uid
                _uiState.emit(LoginUIState.Success(uid))
            } else {
                _uiState.emit(LoginUIState.Error(Throwable(message = "Invalid email or password")))
            }
        }
    }

}
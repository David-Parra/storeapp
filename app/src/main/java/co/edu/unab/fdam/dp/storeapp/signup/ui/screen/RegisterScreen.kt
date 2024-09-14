package co.edu.unab.fdam.dp.storeapp.signup.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import co.edu.unab.fdam.dp.storeapp.R
import co.edu.unab.fdam.dp.storeapp.signup.ui.viewmodel.SignUpViewModel
import coil.compose.AsyncImage

@Composable
fun RegisterScreen(
    navController: NavController, viewModel: SignUpViewModel
) {
    var name: String by rememberSaveable {
        mutableStateOf("")
    }
    var document: String by rememberSaveable {
        mutableStateOf("")
    }
    var email: String by rememberSaveable {
        mutableStateOf("")
    }
    var password: String by rememberSaveable {
        mutableStateOf("")
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<Boolean>(
        initialValue = false, key1 = lifecycle, key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.onCreateAccount.collect { isCreated -> value = isCreated }
        }
    }

    when (uiState) {
        true -> {
            LaunchedEffect(Unit) {
                navController.popBackStack()
            }
        }

        false -> {
            ConstraintLayout(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {
                val (img, tfName, tfDocument, tfEmail, tfPassword, btnAdd, btnCancel) = createRefs()
                AsyncImage(model = "https://cengage.my.site.com/resource/1607465003000/loginIcon",
                    contentDescription = "Logo",
                    Modifier
                        .constrainAs(img) {
                            top.linkTo(parent.top, margin = 120.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .size(100.dp))
                TextField(
                    value = name,
                    onValueChange = { productName -> name = productName },
                    label = { Text(text = "Name:") },
                    placeholder = { Text(text = "Username") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(tfName) {
                            top.linkTo(img.bottom, margin = 32.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                            width = Dimension.fillToConstraints
                        },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent
                    ),
                    singleLine = true
                )
                TextField(
                    value = document,
                    onValueChange = { userDocument -> document = userDocument },
                    label = { Text(text = "Document:") },
                    placeholder = { Text(text = "1") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(tfDocument) {
                            top.linkTo(tfName.bottom, margin = 16.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                            width = Dimension.fillToConstraints
                        },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
                TextField(
                    value = email,
                    onValueChange = { userEmail -> email = userEmail },
                    label = { Text(text = "Email:") },
                    placeholder = { Text(text = "User Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(tfEmail) {
                            top.linkTo(tfDocument.bottom, margin = 32.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                            width = Dimension.fillToConstraints
                        },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true
                )
                TextField(
                    value = password,
                    onValueChange = { userPassword -> password = userPassword },
                    label = { Text(text = "Password:") },
                    placeholder = { Text(text = "User password") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(tfPassword) {
                            top.linkTo(tfEmail.bottom, margin = 32.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                            width = Dimension.fillToConstraints
                        },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent
                    ),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )
                Button(onClick = {
                    viewModel.createAccount(
                        name = name,
                        document = document,
                        email = email,
                        password = password,
                    )
                }, modifier = Modifier.constrainAs(btnAdd) {
                    top.linkTo(tfPassword.bottom, margin = 32.dp)
                    start.linkTo(btnCancel.end, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF574A))
                ) {
                    Text(text = stringResource(R.string.register_screen_register_button_value))
                }
                OutlinedButton(onClick = {
                    navController.popBackStack()
                }, modifier = Modifier
                    .constrainAs(btnCancel) {
                        top.linkTo(tfPassword.bottom, margin = 32.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(btnAdd.start, margin = 16.dp)
                        width = Dimension.fillToConstraints
                    }
                    .padding(end = 8.dp), colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent, contentColor = Color(0xFFFF574A)
                ), border = BorderStroke(2.dp, Color(0xFFFF574A))) {
                    Text(text = stringResource(R.string.register_screen_back_button_value))
                }
                createHorizontalChain(btnCancel, btnAdd, chainStyle = ChainStyle.Packed)
            }
        }
    }
}



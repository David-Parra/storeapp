package co.edu.unab.fdam.dp.storeapp.login.ui.screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import co.edu.unab.fdam.dp.storeapp.R
import co.edu.unab.fdam.dp.storeapp.StoreAppDestinations
import co.edu.unab.fdam.dp.storeapp.core.ui.activity.MainActivity
import co.edu.unab.fdam.dp.storeapp.login.ui.LoginUIState
import co.edu.unab.fdam.dp.storeapp.login.ui.viewmodel.LoginViewModel
import co.edu.unab.fdam.dp.storeapp.user.ui.UsersUIState
import coil.compose.AsyncImage

//@Preview(showBackground = true)
@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val context: Context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<LoginUIState>(
        initialValue = LoginUIState.Default, key1 = lifecycle, key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { isLogged -> value = isLogged }
        }
    }

    when (uiState) {
        LoginUIState.Default -> {
            println("default state")
        }

        is LoginUIState.Error -> {
            val throwable = (uiState as LoginUIState.Error).throwable
            Toast.makeText(context, "Login Failure: ${throwable.message}", Toast.LENGTH_SHORT)
                .show()
            viewModel.onResetState()
        }

        LoginUIState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }

        is LoginUIState.Success -> {
            val uid = (uiState as LoginUIState.Success).uid
            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
            //create shared preferences
            val sharedPreferences = context.getSharedPreferences(
                stringResource(R.string.prefs_name), Context.MODE_PRIVATE
            ).edit()
            sharedPreferences.putBoolean("isLogged", true).apply()
            sharedPreferences.putString("uid", uid).apply()

            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
            (context as Activity).finish()
            viewModel.onResetState()
        }

        LoginUIState.Empty -> {

        }

        LoginUIState.Idle -> {}
    }

    Column(modifier = Modifier.fillMaxSize()) {
        HeaderScreen()
        BodyLogin(
            navController = navController,
            viewModel = viewModel,
            keyboardController = keyboardController
        )
        Spacer(modifier = Modifier.weight(1f))
        FooterLogin(navController = navController)
    }
}

@Composable
fun HeaderScreen() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(width = 2.dp, color = Color.Black))
    ) {
        val (imgLogin, txtLogin) = createRefs()
        AsyncImage(model = "https://unired.edu.co/images/instituciones/UNAB/2017/junio/unab.jpg",
            contentDescription = "Logo",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .constrainAs(imgLogin) {
                    top.linkTo(parent.top, margin = 32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .height(150.dp))
        Text(text = stringResource(R.string.txt_login_title),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(txtLogin) {
                top.linkTo(imgLogin.bottom, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
    }
}

@Composable
fun BodyLogin(
    navController: NavController,
    viewModel: LoginViewModel,
    keyboardController: SoftwareKeyboardController?
) {
    //observeasstate
    val emailValue: String by viewModel.email.observeAsState(initial = "")
    val passwordValue: String by viewModel.password.observeAsState(initial = "")

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    ) {
        val (tfEmail, tfPass, btnLogin) = createRefs()

        TextField(value = emailValue,
            onValueChange = { viewModel.onEmailChanged(it) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "mail",
                    tint = Color.Black
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(tfEmail) {
                    top.linkTo(parent.top, margin = 64.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent),
            placeholder = { Text(text = stringResource(R.string.txt_placeholder_email_login)) })

        TextField(
            value = passwordValue,
            onValueChange = { viewModel.onPasswordChanged(it) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "lock password",
                    tint = Color.Black
                )
            },
            trailingIcon = {
                Icon(imageVector = Icons.Filled.Lock, contentDescription = "lock password")
            },
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(tfPass) {
                    top.linkTo(tfEmail.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent),
            placeholder = { Text(text = stringResource(R.string.txt_placeholder_password_login)) },
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            onClick = {
                keyboardController?.hide()
                viewModel.verifyLogin()
            },
            modifier = Modifier.constrainAs(btnLogin) {
                top.linkTo(tfPass.bottom, margin = 32.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF574A))
        ) {
            Text(text = stringResource(R.string.txt_login_title))
        }

    }

}


//@Preview(showBackground = true)
@Composable
fun FooterLogin(navController: NavController) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .size(100.dp)
    ) {
        val (divider, textSignUp, linkSignUp) = createRefs()
        Divider(modifier = Modifier.constrainAs(divider) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
        Text(text = stringResource(R.string.txt_message_register),
            modifier = Modifier
                .constrainAs(textSignUp) {
                    top.linkTo(divider.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(end = 8.dp))
        Text(
            text = stringResource(R.string.txt_register_here),
            modifier = Modifier
                .constrainAs(linkSignUp) {
                    top.linkTo(divider.bottom)
                    start.linkTo(textSignUp.end)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .clickable { navController.navigate(StoreAppDestinations.RegisterDestination.route) },
            color = Color(0xFFFF574A),
            textDecoration = TextDecoration.Underline,
        )
        createHorizontalChain(textSignUp, linkSignUp, chainStyle = ChainStyle.Packed)
    }

}








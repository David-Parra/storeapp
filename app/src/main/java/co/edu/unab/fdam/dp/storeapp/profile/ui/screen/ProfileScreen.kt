package co.edu.unab.fdam.dp.storeapp.profile.ui.screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import co.edu.unab.fdam.dp.storeapp.R
import co.edu.unab.fdam.dp.storeapp.core.ui.activity.LoginActivity
import co.edu.unab.fdam.dp.storeapp.core.ui.model.User
import co.edu.unab.fdam.dp.storeapp.profile.ui.viewmodel.ProfileViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier, viewModel: ProfileViewModel
) {
    val context = LocalContext.current
    val uid = context.getSharedPreferences(
        context.getString(R.string.prefs_name), Context.MODE_PRIVATE
    ).getString("uid", null)

    if (uid == null) {
        redirectToLogin(context)
        return
    }

    val user by viewModel.getUserById(uid)
        .observeAsState(initial = User(document = 0, email = "", name = ""))

    if (user.id.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            CircularProgressIndicator(
                color = Color(0xFFFF574A),
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(48.dp)
            )
        }
    } else {
        ProfileContent(user = user, modifier = modifier)
    }
}

@Composable
fun ProfileContent(user: User, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(user.image)
                    .size(Size.ORIGINAL).error(R.drawable.ic_error).build(),
                loading = { CircularProgressIndicator() },
                contentDescription = user.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .border(2.dp, Color(0xFFFF574A), CircleShape)
                    .size(150.dp)
                    .clip(CircleShape)
            )


            Text(
                text = user.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp),
                textAlign = TextAlign.Center
            )


            UserInfoRow(
                label = "Document:", value = user.document.toString(), icon = Icons.Default.Person
            )


            UserInfoRow(
                label = "Email:", value = user.email, icon = Icons.Default.Email
            )
        }


        Button(
            onClick = { logout(context) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF574A))
        ) {
            Text(text = "Logout")
        }
    }
}

@Composable
fun UserInfoRow(label: String, value: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .padding(end = 8.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(end = 8.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold
        )
    }
}


fun logout(context: Context) {
    val sharedPreferences =
        context.getSharedPreferences(context.getString(R.string.prefs_name), Context.MODE_PRIVATE)
            .edit()
    sharedPreferences.remove("isLogged").apply()
    sharedPreferences.remove("uid").apply()
    Firebase.auth.signOut()
    redirectToLogin(context)
}

fun redirectToLogin(context: Context) {
    val intent = Intent(context, LoginActivity::class.java)
    context.startActivity(intent)
    (context as Activity).finish()
}

package co.edu.unab.fdam.dp.storeapp.user.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import co.edu.unab.fdam.dp.storeapp.R
import co.edu.unab.fdam.dp.storeapp.core.ui.model.User
import co.edu.unab.fdam.dp.storeapp.user.ui.UsersUIState
import co.edu.unab.fdam.dp.storeapp.user.ui.viewmodel.UsersViewModel

@Composable
fun UsersScreen(
    navController: NavHostController,
    modifier: Modifier,
    viewModel: UsersViewModel,
) {
    val lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<UsersUIState>(
        initialValue = UsersUIState.Loading, key1 = lifecycle, key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { state -> value = state }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {

            UsersUIState.Idle -> {
            }

            is UsersUIState.Empty -> Text(
                text = "No users found", modifier = Modifier.align(Alignment.Center)
            )

            UsersUIState.Loading -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color(0xFFFF574A),
            )

            is UsersUIState.Success -> {
                val users = (uiState as UsersUIState.Success).users
                UserGrid(users = users, modifier = modifier)
            }

            is UsersUIState.Error -> {
                val error = (uiState as UsersUIState.Error).throwable.message ?: "Unknown error"
                Text(text = "Error: $error", modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun UserGrid(users: List<User>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(users.size) { index ->
            UserCard(user = users[index])
        }
    }
}

@Composable
fun UserCard(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFFF574A), RoundedCornerShape(10.dp))
            .padding(8.dp)
            .size(200.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(user.image)
                        .size(Size.ORIGINAL)
                        .error(R.drawable.ic_error)
                        .build(),
                    loading = { CircularProgressIndicator() },
                    contentDescription = "User Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFF574A), CircleShape)
                )

                Text(
                    text = user.name,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 12.dp)
                )

                Text(
                    text = user.email,
                    textAlign = TextAlign.Center,
                    fontSize = 11.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}




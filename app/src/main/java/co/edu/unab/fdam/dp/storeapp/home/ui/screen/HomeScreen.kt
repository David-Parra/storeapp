package co.edu.unab.fdam.dp.storeapp.home.ui.screen

import co.edu.unab.fdam.dp.storeapp.core.ui.model.Product
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import co.edu.unab.fdam.dp.storeapp.R
import co.edu.unab.fdam.dp.storeapp.StoreAppDestinations
import co.edu.unab.fdam.dp.storeapp.extension.navigateOnce
import co.edu.unab.fdam.dp.storeapp.home.ui.ProductsUIState
import co.edu.unab.fdam.dp.storeapp.home.ui.viewmodel.HomeViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun HomeScreen(
    navController: NavHostController, modifier: Modifier, viewModel: HomeViewModel
) {
    val lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle
    val context: Context = LocalContext.current
    //handler UI states
    val uiState by produceState<ProductsUIState>(
        initialValue = ProductsUIState.Loading, key1 = lifecycle, key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { state -> value = state }
        }
    }

    when (uiState) {
        ProductsUIState.Empty -> {
            Text(text = "Data not found", modifier = Modifier.fillMaxWidth())
        }

        is ProductsUIState.Error -> {
            val errorMessage = (uiState as ProductsUIState.Error).throwable
            Toast.makeText(context, "Error: ${errorMessage.message}", Toast.LENGTH_SHORT).show()
        }

        ProductsUIState.Idle -> {
        }

        ProductsUIState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }

        is ProductsUIState.Success -> {
            val products = (uiState as ProductsUIState.Success).products
            LazyColumn(
                modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(products.size) { index ->
                    ProductItem(
                        product = products[index],
                        onLongPressItem = { productValue ->
                            Toast.makeText(
                                context,
                                "long press $index Item: ${productValue}",
                                Toast.LENGTH_SHORT
                            ).show()
                            viewModel.deleteProduct(productValue)
                        },
                        onSelected = { productValue ->
                            Toast.makeText(
                                context, "on press $index Item: ${productValue}", Toast.LENGTH_SHORT
                            ).show()
                            //navigate to productDetailScreen pass productId
                            products[index].id?.let {
                                StoreAppDestinations.ProductDetailDestination.createRoute(
                                    it
                                )
                            }?.let {
                                navController.navigateOnce(
                                    it
                                ) {}
                            }
                        },
                    )
                }
            }
        }
    }
}


//productItem UI composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductItem(
    product: Product, onSelected: (Product) -> Unit, onLongPressItem: (Product) -> Unit
) {
    val context: Context = LocalContext.current
    Card(modifier = Modifier
        .fillMaxWidth()
        .size(200.dp)
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                //Toast.makeText(context, product.name, Toast.LENGTH_SHORT).show()
                onSelected(product)
            }, onLongPress = {
                //Toast.makeText(context, "Long press ${product.name}", Toast.LENGTH_SHORT).show()
                onLongPressItem(product)
            })
        }) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (imgProduct, nameProduct, priceProduct) = createRefs()
            SubcomposeAsyncImage(model = ImageRequest.Builder(LocalContext.current)
                .data(product.image)
                .size(Size.ORIGINAL) // Set the target size to load the image at.
                .error(R.drawable.ic_error).build(),
                loading = {
                    CircularProgressIndicator()
                },
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .border(2.dp, Color.Transparent)
                    .size(100.dp)
                    .constrainAs(imgProduct) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start, 32.dp)
                        bottom.linkTo(parent.bottom)
                    })
            Text(text = product.name, modifier = Modifier.constrainAs(nameProduct) {
                top.linkTo(imgProduct.top, margin = 8.dp)
                start.linkTo(imgProduct.end, margin = 32.dp)
                end.linkTo(parent.end)
                bottom.linkTo(priceProduct.top)
            })
            Text(text = "$ ${product.price}", modifier = Modifier.constrainAs(priceProduct) {
                bottom.linkTo(imgProduct.bottom, margin = 8.dp)
                start.linkTo(imgProduct.end, margin = 32.dp)
                end.linkTo(parent.end)
                top.linkTo(nameProduct.bottom)
            })
        }
    }
}

//ProductList UI composable


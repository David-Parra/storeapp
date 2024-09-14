package co.edu.unab.fdam.dp.storeapp.product.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import co.edu.unab.fdam.dp.storeapp.core.ui.model.Product
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import co.edu.unab.fdam.dp.storeapp.R
import co.edu.unab.fdam.dp.storeapp.StoreAppDestinations
import co.edu.unab.fdam.dp.storeapp.product.ui.viewmodel.ProductDetailViewModel
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun ProductDetailScreen(
    productId: Int,
    product: Product?,
    navController: NavHostController,
    modifier: Modifier,
    viewModel: ProductDetailViewModel
) {
    val productItem: Product by viewModel.getProductById(productId).observeAsState(
        initial = Product(
            id = 0, name = "", price = 0
        )
    )

    val context: Context = LocalContext.current

    if (productItem.id != 0) {/*        LaunchedEffect(Unit) {
                  Toast.makeText(context, "$productItem", Toast.LENGTH_SHORT).show()
                }*/
    } else {
        //viewModel.loadProduct(productId)
    }

    ConstraintLayout(
        modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        val (imgProduct, tfName, tfPrice, tfDescription, btnEdit, btnCancel) = createRefs()
        AsyncImage(
            model = productItem.image,
            contentDescription = productItem.name,
            modifier = Modifier
                .size(100.dp)
                .constrainAs(imgProduct) {
                    top.linkTo(parent.top, margin = 32.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)

                },
            contentScale = ContentScale.Crop
        )
        Text(
            text = productItem.name,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(tfName) {
                    top.linkTo(imgProduct.bottom, margin = 32.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                },
        )
        Text(
            text = "$${productItem.price}",
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(tfPrice) {
                    top.linkTo(tfName.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                },
        )
        Text(
            text = productItem.description,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(tfDescription) {
                    top.linkTo(tfPrice.bottom, margin = 32.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                },
        )

        Button(onClick = {
            //navigate to productDetailScreen pass productId
            navController.navigate(
                StoreAppDestinations.ProductUpdateDestination.createRoute(productId)
            ) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = false
            }
        }, modifier = Modifier.constrainAs(btnEdit) {
            top.linkTo(tfDescription.bottom, margin = 32.dp)
            start.linkTo(btnCancel.end, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
            width = Dimension.fillToConstraints
        }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF574A))
        ) {
            Text(text = "Edit Product")
        }
        OutlinedButton(onClick = {
            navController.popBackStack()
        }, modifier = Modifier
            .constrainAs(btnCancel) {
                top.linkTo(tfDescription.bottom, margin = 32.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(btnEdit.start, margin = 16.dp)
                width = Dimension.fillToConstraints
            }
            .padding(end = 8.dp), colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent, contentColor = Color(0xFFFF574A)
        ), border = BorderStroke(2.dp, Color(0xFFFF574A))) {
            Text(text = "Back")
        }
        createHorizontalChain(btnCancel, btnEdit, chainStyle = ChainStyle.Packed)
    }
}
//picture carousel row lazy
//description
//reviews
//name
//add to cart button
//back button to product list
//edit button to product details(count) cart

@Composable
fun ProductImages(modifier: Modifier, product: Product) {
    val productImages = getFakeProductsImages()
    //images
    //lazy row of images
    LazyRow(
        modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(productImages.size) { index ->
            ImageItem(productImages[index])
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageItem(urlImage: String) {
    val context: Context = LocalContext.current
    return Card(
        onClick = {
            Toast.makeText(context, urlImage, Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier.size(200.dp),

        ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (imgProduct) = createRefs()
            SubcomposeAsyncImage(model = ImageRequest.Builder(LocalContext.current).data(urlImage)
                .size(Size.ORIGINAL) // Set the target size to load the image at.
                .error(R.drawable.ic_error).build(),
                loading = {
                    CircularProgressIndicator()
                },
                contentDescription = "description",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .border(2.dp, Color.Transparent)
                    .size(100.dp)
                    .constrainAs(imgProduct) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start, 32.dp)
                        bottom.linkTo(parent.bottom)
                    })
        }
    }
}

fun getFakeProductsImages(): List<String> {
    return listOf(
        "https://my-media.apjonlinecdn.com/catalog/product/cache/b3b166914d87ce343d4dc5ec5117b502/4/P/4P4F6AA-1_T1678954122.png",
        "https://my-media.apjonlinecdn.com/catalog/product/cache/b3b166914d87ce343d4dc5ec5117b502/4/P/4P4F6AA-1_T1678954122.png",
        "https://my-media.apjonlinecdn.com/catalog/product/cache/b3b166914d87ce343d4dc5ec5117b502/4/P/4P4F6AA-1_T1678954122.png",
    )
}

//composable column with text with title lazy row  text description
/*   @Composable
   fun MyComposable(items: List<co.edu.unab.fdam.dp.storeapp.product.model.Product>) {
       // ... código similar al anterior ...
       LazyColumn {
           items(items.size) { item ->
               Column {
                   Text(text = item.name)
                   Text(text = item.description)
               }
           }
       }
   }*/

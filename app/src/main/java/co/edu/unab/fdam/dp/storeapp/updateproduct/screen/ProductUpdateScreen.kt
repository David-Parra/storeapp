package co.edu.unab.fdam.dp.storeapp.updateproduct.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import co.edu.unab.fdam.dp.storeapp.core.ui.model.Product
import co.edu.unab.fdam.dp.storeapp.updateproduct.viewmodel.ProductUpdateViewModel
import coil.compose.AsyncImage

@Composable
fun ProductUpdateScreen(
    productId: Int, product: Product?,
    modifier: Modifier,
    navController: NavController,
    viewModel: ProductUpdateViewModel,
) {
    val context: Context = LocalContext.current
    val productItem: Product by viewModel.getProductById(productId).observeAsState(
        initial = Product(
            id = 0, name = "hola", price = 0
        )
    )

    var name: String by rememberSaveable {

        mutableStateOf("")
    }
    var price: String by rememberSaveable {

        mutableStateOf("")
    }
    var description: String by rememberSaveable {

        mutableStateOf("")
    }

    var image: String by rememberSaveable {

        mutableStateOf("https://cdn-icons-png.freepik.com/256/12313/12313717.png?semt=ais_hybrid")
    }

    if (productItem.id != 0) {
        LaunchedEffect(Unit) {
            name = productItem.name
            price = productItem.price.toString()
            description = productItem.description
            image = productItem.image
        }

        ConstraintLayout(
            modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            val (imgProduct, tfName, tfPrice, tfDescription, tfImage, btnAdd, btnCancel) = createRefs()
            AsyncImage(
                model = image,
                contentDescription = name,
                modifier = Modifier
                    .size(100.dp)
                    .constrainAs(imgProduct) {
                        top.linkTo(parent.top, margin = 32.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)

                    },
                contentScale = ContentScale.Crop
            )
            TextField(value = name,
                onValueChange = { productName -> name = productName },
                label = { Text(text = "Nombre:") },
                placeholder = { Text(text = "Nombre del producto") },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(tfName) {
                        top.linkTo(imgProduct.bottom, margin = 32.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                        width = Dimension.fillToConstraints
                    },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent
                ),
                singleLine = true
            )
            TextField(value = price,
                onValueChange = { productPrice -> price = productPrice },
                label = { Text(text = "Precio:") },
                placeholder = { Text(text = "30000") },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(tfPrice) {
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
                value = description,
                onValueChange = { productDescription -> description = productDescription },
                label = { Text(text = "Descripción:") },
                placeholder = { Text(text = "Descripción del producto") },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(tfDescription) {
                        top.linkTo(tfPrice.bottom, margin = 32.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                        width = Dimension.fillToConstraints
                    },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent
                ),
            )
            TextField(value = image,
                onValueChange = { productImageUrl -> image = productImageUrl },
                label = { Text(text = "URL Imágen:") },
                placeholder = { Text(text = "URL del producto") },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(tfImage) {
                        top.linkTo(tfDescription.bottom, margin = 32.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                        width = Dimension.fillToConstraints
                    },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent
                ),
                singleLine = true
            )
            Button(onClick = {
                viewModel.updateProduct(
                    id = productId,
                    name = name,
                    price = price,
                    description = description,
                    image = image,
                )
                Toast.makeText(context, "product updated", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }, modifier = Modifier.constrainAs(btnAdd) {
                top.linkTo(tfImage.bottom, margin = 32.dp)
                start.linkTo(btnCancel.end, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
            }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF574A))
            ) {
                Text(text = "Edit product")
            }
            OutlinedButton(onClick = {
                navController.popBackStack()
            }, modifier = Modifier
                .constrainAs(btnCancel) {
                    top.linkTo(tfImage.bottom, margin = 32.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(btnAdd.start, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .padding(end = 8.dp), colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent, contentColor = Color(0xFFFF574A)
            ), border = BorderStroke(2.dp, Color(0xFFFF574A))) {
                Text(text = "Back")
            }
            createHorizontalChain(btnCancel, btnAdd, chainStyle = ChainStyle.Packed)
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }

}
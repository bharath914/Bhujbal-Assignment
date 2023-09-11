package com.bharath.bhujbalassignment.presentation

import android.content.Intent
import android.graphics.Paint.Align
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bharath.bhujbalassignment.R
import com.bharath.bhujbalassignment.data.entities.Products
import com.bharath.bhujbalassignment.data.entities.ShopDetail
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlin.math.round

// to avoid recomposition we use immutable tag.

@Immutable
class ProductListCls(val list: List<Products>)

@Composable
fun HomePage(
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    Surface {


        val shopDetail = homeViewModel.ShopDetail.collectAsState()
        val loading = homeViewModel.loadingShop.collectAsState()

        Column(
            modifier = Modifier

                .height(IntrinsicSize.Max)
                .fillMaxWidth()
        ) {
            if (loading.value) {
                Box(modifier = Modifier.fillMaxSize()) {
                    LinearProgressIndicator()
                }
            } else {
                Spacer(modifier = Modifier.height(15.dp))

                Box(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth()
                ) {


                    shopDetail.value.let {
                        if (it.isNotEmpty()) {
                            StoreDetails(
                                shopDetail = it.first(),
                                paddingValues = PaddingValues(10.dp)
                            )
                        }
                    }


                }
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBox() {
    var text by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
//            .height(40.dp),
        ,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Here",

                )
        },
        placeholder = {
            Text(
                text = "Search Product from Shubham Stores ",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    )
}

@Composable
fun ScrollableRow() {
    var tabIndex = remember {
        mutableStateOf(0)
    }
    val tabs = listOf(
        "Marathi NewsPaper", "Hindi NewsPaper", "Telugu NewsPaper", "Tamil NewsPaper"
    )
    val homeViewModel: HomeViewModel = hiltViewModel()

    Column(modifier = Modifier.fillMaxWidth()) {
        ScrollableTabRow(
            selectedTabIndex = tabIndex.value,
            modifier = Modifier
                .fillMaxWidth(),
            tabs = {
                tabs.forEachIndexed { index, s ->
                    Tab(selected = index == tabIndex.value, onClick = {
                        tabIndex.value = index
                        homeViewModel.saveNewsPaper(tabs[index])

                    }, modifier = Modifier.padding(10.dp)) {
                        Text(text = s)
                    }
                }
            },
            edgePadding = 10.dp,
            contentColor = MaterialTheme.colorScheme.onBackground

        )


    }
}

@Composable
fun TopBar() {
    val context = LocalContext.current
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "Hire Me !")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(start = 15.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painterResource(id = R.drawable.arrowback), contentDescription = "")
            }

        }
        val favIcon = remember {
            mutableStateOf(Icons.Default.FavoriteBorder)
        }
        val clicked = remember {
            mutableStateOf(false)
        }
        if (clicked.value) {
            favIcon.value = Icons.Default.Favorite
        } else {
            favIcon.value = Icons.Default.FavoriteBorder
        }
        Box {
            Row {


                IconButton(onClick = { clicked.value = !clicked.value }) {
                    Icon(imageVector = favIcon.value, contentDescription = "")
                }
                IconButton(onClick = {
                    context.startActivity(shareIntent)
                }) {
                    Icon(imageVector = Icons.Rounded.Send, contentDescription = "")
                }
            }
        }
    }
}

// The top of the UI as shown in the assignment.
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun StoreDetails(shopDetail: ShopDetail, paddingValues: PaddingValues) {

    val context = LocalContext.current
    val number = Uri.parse("tel:" + "123456")
    val intent = Intent(Intent.ACTION_DIAL, number)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(start = 15.dp, end = 10.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = shopDetail.ShopImage,
            contentDescription = "Shop Image",
            modifier = Modifier
                .size(85.dp)
                .clip(shape = RoundedCornerShape(15))
        ) {
            it.load(shopDetail.ShopImage)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.connectionerror)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        }

        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(10f)) {


            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(shopDetail.title + " " + shopDetail.location)
                }
            }, maxLines = 2)


            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Light)) {
                        append(shopDetail.Address)


                    }
                },
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = shopDetail.Type,
                maxLines = 1,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )

        }
        Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier.weight(1f)) {

            IconButton(onClick = {
                try {
                    context.startActivity(intent)

                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
            }) {
                Icon(painterResource(id = R.drawable.call), contentDescription = "")
            }

        }
    }
}


@Composable
fun ProductsList(
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val list = ProductListCls(homeViewModel.productList.collectAsState().value)
    val loadingProducts = homeViewModel.loadingProducts.collectAsState()
    val newSPaper = homeViewModel.currentPaper.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {

        Text(text = newSPaper.value, modifier = Modifier.padding(start = 15.dp))

        if (loadingProducts.value) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                LinearProgressIndicator()
            }
        } else {
            LazyColumn(content = {
                itemsIndexed(list.list) { ind, item ->
                    ProductItem(product = item)
                    Spacer(modifier = Modifier.height(10.dp))
                    if (ind == list.list.lastIndex) {
                        Disclaimer()
                    }
                }
            }, contentPadding = PaddingValues(15.dp))
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductItem(product: Products) {
    val homeViewModel: HomeViewModel = hiltViewModel()


    val showBtn = remember {
        mutableStateOf(false)
    }
    val cartMap = homeViewModel.cartItems.collectAsState()
    showBtn.value = cartMap.value.getOrDefault(product.id, 0) > 0

    var noOfItems = remember {

        mutableStateOf(cartMap.value.getOrDefault(product.id, 0))
    }


    Card(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier
                .size(85.dp)
                .padding(start = 5.dp)
                .clip(RoundedCornerShape(10))){

            GlideImage(
                model = product.image,
                contentDescription = "",
                modifier = Modifier
                    .size(85.dp)


            ) {
                it.load(product.image)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.connectionerror)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

            }
                var bg = Color(0xD2FCFAFA)
                if (isSystemInDarkTheme()){
                    bg = Color(0xC90D0E0E)
                }
                if (product.subscribe){
                    Box(modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .background(bg, RoundedCornerShape(5)),){
                    SubScribe()
                    }
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {

                Text(
                    text = product.name,
                    maxLines = 2,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 15.sp
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            text = product.quantity,
                            fontSize = 14.sp, fontWeight = FontWeight.Light,
                            modifier = Modifier.align(Alignment.Start)

                        )
                        Text(
                            text = "₹${product.price}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        if (!showBtn.value) {
                            Button(
                                onClick = {

                                    homeViewModel.addToCartNum(product)
                                    showBtn.value = !showBtn.value

                                },
                                shape = RoundedCornerShape(20),
                                modifier = Modifier.padding(bottom = 5.dp)
                            ) {
                                Text(text = "+Add")
                            }
                        } else {
                            noOfItems.value = cartMap.value.getOrDefault(product.id, 0)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(top = 10.dp),

                                verticalAlignment = Alignment.Bottom,

                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    text = "-",
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.clickable {
                                        homeViewModel.removeCartNum(product)
                                        noOfItems.value--
                                        if (noOfItems.value <= 0) showBtn.value = !showBtn.value
                                    },
                                    fontSize = 18.sp
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Box(
                                    modifier = Modifier

                                        .padding(start = 5.dp, end = 5.dp)
                                ) {
                                    Text(
                                        text = "${noOfItems.value}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "+",
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.clickable {
                                        homeViewModel.addToCartNum(product)
                                        noOfItems.value++
                                    },
                                    fontSize = 18.sp
                                )
                            }

                        }
                    }
                }

            }
        }
    }
}


@Composable
fun Disclaimer() {
    Card(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(5.dp)) {
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Delivery & services provided by shop",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,

                )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Shop in using Bhuj to connect with you",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Fast, Transparently and Securely",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,

                )
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Bhuj ❤️ Making Transparent & Direct Connections",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,

                )
        }
    }
}


@Composable
fun SubScribe() {
    Column (horizontalAlignment =Alignment.CenterHorizontally){
        Icon(painterResource(id = R.drawable.event_repeat), contentDescription = "", tint = MaterialTheme.colorScheme.onBackground)
        Text(text = "Subscribe", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
    }
}

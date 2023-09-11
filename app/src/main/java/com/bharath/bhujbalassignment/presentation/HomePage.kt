package com.bharath.bhujbalassignment.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bharath.bhujbalassignment.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState, snackbar = {
                Snackbar(
                    shape = RoundedCornerShape(15),
                    content = {
                        Row {
                            Icon(
                                painterResource(id = R.drawable.shoppingbag),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(text = "")
                            Text(text = "View Order ")
                        }
                    },
                )
            }, modifier = Modifier.padding(bottom = 250.dp))
        }
    ) {

        val cartNum = homeViewModel.cartItemsNum.collectAsState()
        val totalPrice = homeViewModel.totalMoney.collectAsState()


        Surface(modifier = Modifier.padding(it)) {
            Column {
                TopBar()
                HomePage()
                ScrollableRow()

                SearchBox()
                Column {
                    Box(modifier = Modifier.fillMaxSize()) {


                        ProductsList()
                        if (cartNum.value > 0) {
                            Snackbar(
                                modifier = Modifier.padding(
                                    top = 200.dp,
                                    start = 15.dp,
                                    end = 15.dp
                                ),
                                containerColor = MaterialTheme.colorScheme.primaryContainer,

                                ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.shoppingbag),
                                        contentDescription = "",
                                        tint = MaterialTheme.colorScheme.onBackground
                                    )
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Column {

                                        Text(
                                            text = "${cartNum.value} Products",
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                        Text(
                                            text = "₹ ${totalPrice.value}",
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        Text(
                                            text = "View Order",
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                        Icon(
                                            painterResource(id = R.drawable.arrow_forward),
                                            contentDescription = "",
                                            tint = MaterialTheme.colorScheme.onBackground
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }


    }
}
package com.bharath.bhujbalassignment.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharath.bhujbalassignment.data.ShopDataBase
import com.bharath.bhujbalassignment.data.entities.Products
import com.bharath.bhujbalassignment.data.entities.ShopDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val shopDataBase: ShopDataBase,
) : ViewModel() {


    private val _loadingShop = MutableStateFlow(true)
    val loadingShop = _loadingShop.asStateFlow()


    private val _ShopDetail = MutableStateFlow(emptyList<ShopDetail>())
    val ShopDetail: StateFlow<List<ShopDetail>> = _ShopDetail.asStateFlow()

    //loading list
    private val _loadingProducts = MutableStateFlow(true)
    val loadingProducts = _loadingProducts.asStateFlow()

    // Products List
    private val _productList = MutableStateFlow(emptyList<Products>())
    val productList: StateFlow<List<Products>> = _productList.asStateFlow()
    // Current Paper
    private val _currentPaper = MutableStateFlow("Marathi NewsPaper")
    val currentPaper = _currentPaper.asStateFlow()
    //Cart Items  number
    private val _cartItemsNum = MutableStateFlow(0)
    val cartItemsNum = _cartItemsNum.asStateFlow()
    //
    private val cartlist :MutableList<Products> = mutableListOf()

    // cart Items
    private val _cartItems = MutableStateFlow(hashMapOf<Int,Int>())
    val cartItems = _cartItems.asStateFlow()

    //total money
    private val _totalMoney = MutableStateFlow(0)
    val totalMoney = _totalMoney.asStateFlow()

    //cart map
    var cartMap :HashMap<Int,Int> = hashMapOf()
        private set


    //Show SnackBar
    private val _showSnack = MutableStateFlow(false)
    val showSnack = _showSnack.asStateFlow()




    init {
        viewModelScope.launch(Dispatchers.IO) {
            _ShopDetail.tryEmit(shopDataBase.getShopDetails())

            _loadingShop.update { false }


        }
        viewModelScope.launch(Dispatchers.IO) {

        _productList.tryEmit(shopDataBase.getProducts())
            _loadingProducts.update { false }


            _productList.value.forEach {
                Log.d("List",it.name)
            }
        }


    }


    fun saveNewsPaper(string: String){
        _currentPaper.tryEmit(string)
    }

    fun addToCartNum(products: Products){
        cartMap.put(products.id,cartMap.getOrDefault(products.id,0)+1)
        cartlist.add(products)
        _cartItemsNum.tryEmit(++_cartItemsNum.value)
        _cartItems.tryEmit(cartMap)
        _totalMoney.tryEmit(_totalMoney.value+products.price)

        _showSnack.tryEmit(true)
    }
    fun removeCartNum(products: Products){
        cartlist.remove(products)
        cartMap.replace(products.id,cartMap.get(products.id)!!-1)
        _cartItemsNum.tryEmit(--_cartItemsNum.value)
        _totalMoney.tryEmit(_totalMoney.value-products.price)
        _cartItems.tryEmit(cartMap)
    }

}
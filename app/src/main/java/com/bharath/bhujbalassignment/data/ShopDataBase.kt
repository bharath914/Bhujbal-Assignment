package com.bharath.bhujbalassignment.data

import android.util.Log
import com.bharath.bhujbalassignment.data.entities.Products
import com.bharath.bhujbalassignment.data.entities.ShopDetail
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ShopDataBase {
    val fireStore = FirebaseFirestore.getInstance()
    val shopCollection = fireStore.collection(Const.shopCollection)

    val productCollection = fireStore.collection(Const.productCollection)


    suspend fun getProducts(): List<Products>{
        return try {
            Log.d("MYTAG","Getting products")

            productCollection.get().await().toObjects(Products::class.java)

        }catch (e:Exception){
            e.printStackTrace()
            emptyList<Products>()
        }
    }



    suspend fun getShopDetails(): List<ShopDetail>{
        return try {
            Log.d("MYTAG","Getting Shop Details")
            shopCollection.get().await().toObjects(ShopDetail::class.java)
        }catch (e:Exception){
            e.printStackTrace()
            emptyList<ShopDetail>()
        }

    }

companion object Const{
    const val shopCollection="ShopDetails"
    const val productCollection = "Products"
}
}

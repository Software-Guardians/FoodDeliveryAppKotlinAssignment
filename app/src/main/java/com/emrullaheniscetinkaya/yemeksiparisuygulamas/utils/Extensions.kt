package com.emrullaheniscetinkaya.yemeksiparisuygulamas.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationBarItemView

fun Navigation.gecisYap(it: View,id: Int){
    findNavController(it).navigate(id)
}
fun Navigation.gecisYap(it: View,id: NavDirections){
    findNavController(it).navigate(id)
}
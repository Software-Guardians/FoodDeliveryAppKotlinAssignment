package com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.viewmodel


import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.data.entity.Yemekler
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.data.repo.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Array
import javax.inject.Inject

@HiltViewModel
class FavorilerViewModel @Inject constructor(var repository: YemeklerRepository): ViewModel(){
    var yemekler= MutableLiveData<List<Yemekler>>()
    var favoriYemekler=ArrayList<Yemekler>()
    init {
        yemeklerGetir()
    }
    fun yemeklerGetir(){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                yemekler.value=repository.tumYemeklerGetir()
            }
            catch (e: Exception){
                Log.e("Favoriler Hata","Yemekler getirilemedi.")
            }
        }
    }


}
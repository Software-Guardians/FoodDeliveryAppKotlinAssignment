package com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.data.entity.SepetYemekler
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.data.entity.Yemekler
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.data.repo.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.EOFException
import javax.inject.Inject

@HiltViewModel
class DetayViewModel @Inject constructor(var repository: YemeklerRepository): ViewModel() {
    var yemeklerList= MutableLiveData<List<Yemekler>>()
    var sepetYemekler= MutableLiveData<List<SepetYemekler>>()
    val mutex= Mutex()
    init {
        yemekleriYukle()
    }
    fun yemekleriYukle(){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                yemeklerList.value=repository.tumYemeklerGetir()
            }catch (e: Exception){
                Log.e("YemeklerYukle","yemeklistesi getirilemedi")

            }
        }

    }
    fun sepeteYemekEkle(kullaniciAdi: String,yemekler: Yemekler,miktar: Int){

        CoroutineScope(Dispatchers.Main).launch {
            mutex.withLock {


            try {
                sepetYemekler.value=repository.sepetYemekGetir(kullaniciAdi)
                var sepetyemek=sepetYemekler.value.find { it.yemek_adi==yemekler.yemek_adi }
                if (sepetyemek!=null){
                    repository.sepettenYemekSil(sepetyemek.sepet_yemek_id,kullaniciAdi)
                    repository.sepeteYemekEkle(sepetyemek.yemek_adi,sepetyemek.yemek_resim_adi,sepetyemek.yemek_fiyat,sepetyemek.yemek_siparis_adet+miktar,kullaniciAdi)
                }
                else{
                    repository.sepeteYemekEkle(yemekler.yemek_adi,yemekler.yemek_resim_adi,yemekler.yemek_fiyat,miktar,kullaniciAdi)
                }

            }
            catch (e: EOFException){
                repository.sepeteYemekEkle(yemekler.yemek_adi,yemekler.yemek_resim_adi,yemekler.yemek_fiyat,miktar,kullaniciAdi)

            }




        }
    }
    }


}
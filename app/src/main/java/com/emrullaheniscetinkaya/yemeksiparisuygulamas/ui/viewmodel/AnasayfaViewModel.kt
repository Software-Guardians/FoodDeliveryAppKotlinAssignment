package com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.viewmodel

import android.content.Context
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
class AnasayfaViewModel @Inject constructor(var yemeklerRepository: YemeklerRepository)  : ViewModel(){
    var yemeklerList= MutableLiveData<List<Yemekler>>()
    var sepetYemekler= MutableLiveData<List<SepetYemekler>>()
    var yemeklerFiltrelenmisList= MutableLiveData<List<Yemekler>>()
    private val mutex= Mutex()
    init {
        yemekleriYukle()
    }
    fun yemekleriYukle(){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                yemeklerList.value=yemeklerRepository.tumYemeklerGetir()
            }catch (e: Exception){
                Log.e("YemeklerYukle","yemeklistesi getirilemedi")

            }
        }

    }
    fun sepeteYemekEkle(kullaniciAdi: String,yemekler: Yemekler){

        CoroutineScope(Dispatchers.Main).launch {
            mutex.withLock {


                try {
                    sepetYemekler.value = yemeklerRepository.sepetYemekGetir(kullaniciAdi)
                    var sepetyemek = sepetYemekler.value.find { it.yemek_adi == yemekler.yemek_adi }
                    if (sepetyemek != null) {
                        yemeklerRepository.sepettenYemekSil(sepetyemek.sepet_yemek_id, kullaniciAdi)

                        yemeklerRepository.sepeteYemekEkle(
                            sepetyemek.yemek_adi,
                            sepetyemek.yemek_resim_adi,
                            sepetyemek.yemek_fiyat,
                            sepetyemek.yemek_siparis_adet + 1,
                            kullaniciAdi
                        )
                    } else {
                        yemeklerRepository.sepeteYemekEkle(
                            yemekler.yemek_adi,
                            yemekler.yemek_resim_adi,
                            yemekler.yemek_fiyat,
                            1,
                            kullaniciAdi
                        )
                    }

                } catch (e: EOFException) {
                    yemeklerRepository.sepeteYemekEkle(
                        yemekler.yemek_adi,
                        yemekler.yemek_resim_adi,
                        yemekler.yemek_fiyat,
                        1,
                        kullaniciAdi
                    )

                }

            }

        }



    }
    fun yemekleriAra(aramaKelimesi: String){
        val filtrelenmis = yemeklerList.value.filter { it.yemek_adi.contains(aramaKelimesi, ignoreCase = true) }
        yemeklerFiltrelenmisList.value = filtrelenmis
    }
}
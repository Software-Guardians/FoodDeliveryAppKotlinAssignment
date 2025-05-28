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
class SepetViewModel @Inject constructor(var repository: YemeklerRepository):ViewModel() {
    var sepetYemekler= MutableLiveData<List<SepetYemekler>>()
    val mutex= Mutex()
    var sepetYemeklerTutar= MutableLiveData<Int>()
    init {
        sepetYemeklerTutar.value=0
        sepetYemekleriYukle()
    }
    fun sepetYemekleriYukle(){
        CoroutineScope(Dispatchers.Main).launch {


                try {

                    val liste = repository.sepetYemekGetir("TeknolojiMuhafizi")
                    sepetYemekler.value = liste
                    sepetYemeklerTutar.value = liste.sumOf { it.yemek_fiyat * it.yemek_siparis_adet }


                }
                catch (e: EOFException){
                    sepetYemekler.value=emptyList()
                    sepetYemeklerTutar.value=0
                    Log.e("Sepet Yemekler","Kullanıcı adı bulunamadı.")
                }

            }

    }
    fun sepettenYemekSilAdet(yemekler: SepetYemekler){
        CoroutineScope(Dispatchers.Main).launch {
            mutex.withLock {
                if (yemekler.yemek_siparis_adet>1){
                    repository.sepettenYemekSil(yemekler.sepet_yemek_id,"TeknolojiMuhafizi")
                    repository.sepeteYemekEkle(yemekler.yemek_adi,yemekler.yemek_resim_adi,yemekler.yemek_fiyat,yemekler.yemek_siparis_adet-1,"TeknolojiMuhafizi")
                }
                else{
                    repository.sepettenYemekSil(yemekler.sepet_yemek_id,"TeknolojiMuhafizi")
                }
                sepetYemekleriYukle()

            }

        }
    }
    fun sepettenYemekSil(yemekler: SepetYemekler){
        CoroutineScope(Dispatchers.Main).launch {
            mutex.withLock {
                repository.sepettenYemekSil(yemekler.sepet_yemek_id,"TeknolojiMuhafizi")
                sepetYemekleriYukle()

            }

        }
    }
    fun sepeteYemekEkle(kullaniciAdi: String,yemekler: SepetYemekler){

        CoroutineScope(Dispatchers.Main).launch {
            mutex.withLock {


                try {
                    sepetYemekler.value = repository.sepetYemekGetir(kullaniciAdi)
                    var sepetyemek = sepetYemekler.value.find { it.yemek_adi == yemekler.yemek_adi }
                    if (sepetyemek != null) {
                        repository.sepettenYemekSil(sepetyemek.sepet_yemek_id, kullaniciAdi)

                        repository.sepeteYemekEkle(
                            sepetyemek.yemek_adi,
                            sepetyemek.yemek_resim_adi,
                            sepetyemek.yemek_fiyat,
                            sepetyemek.yemek_siparis_adet + 1,
                            kullaniciAdi
                        )
                    } else {
                        repository.sepeteYemekEkle(
                            yemekler.yemek_adi,
                            yemekler.yemek_resim_adi,
                            yemekler.yemek_fiyat,
                            1,
                            kullaniciAdi
                        )
                    }

                } catch (e: EOFException) {
                    repository.sepeteYemekEkle(
                        yemekler.yemek_adi,
                        yemekler.yemek_resim_adi,
                        yemekler.yemek_fiyat,
                        1,
                        kullaniciAdi
                    )

                }
                sepetYemekleriYukle()

            }

        }



    }
    fun tamaminiSil(kullaniciAdi: String){
        CoroutineScope(Dispatchers.Main).launch {
            mutex.withLock {
                for (item in sepetYemekler.value){
                    repository.sepettenYemekSil(item.sepet_yemek_id,kullaniciAdi)

                }
                sepetYemekleriYukle()

            }
            }

    }
}
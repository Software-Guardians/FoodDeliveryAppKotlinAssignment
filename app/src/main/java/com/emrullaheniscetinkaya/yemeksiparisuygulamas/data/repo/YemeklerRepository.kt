package com.emrullaheniscetinkaya.yemeksiparisuygulamas.data.repo

import com.emrullaheniscetinkaya.yemeksiparisuygulamas.data.datasource.YemekDatasource
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.data.entity.SepetYemekler
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.data.entity.Yemekler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class YemeklerRepository(var yemekDatasource: YemekDatasource) {
    suspend fun tumYemeklerGetir(): List<Yemekler> = withContext(Dispatchers.IO) {
        yemekDatasource.tumYemeklerGetir()
    }
    suspend fun sepetYemekGetir(kullanici_adi: String): List<SepetYemekler> = withContext(
        Dispatchers.IO){
        yemekDatasource.sepetYemekGetir(kullanici_adi)

    }
    suspend fun sepeteYemekEkle(yemek_adi: String,yemek_resim_adi: String,yemek_fiyat: Int,yemek_siparis_adet: Int,kullanici_adi: String) = yemekDatasource.sepeteYemekEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
    suspend fun sepettenYemekSil(sepet_yemek_id: Int,kullanici_adi: String)=yemekDatasource.sepettenYemekSil(sepet_yemek_id,kullanici_adi)


    }
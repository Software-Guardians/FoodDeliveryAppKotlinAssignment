package com.emrullaheniscetinkaya.yemeksiparisuygulamas.data.datasource

import android.util.Log
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.data.entity.SepetYemekler
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.data.entity.Yemekler
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.database.retrofit.YemeklerDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class YemekDatasource(var yemeklerDao: YemeklerDao) {
    suspend fun tumYemeklerGetir(): List<Yemekler> = withContext(Dispatchers.IO){
        return@withContext yemeklerDao.tumYemeklerGetir().yemekler
    }
    suspend fun sepetYemekGetir(kullanici_adi: String): List<SepetYemekler> = withContext(
        Dispatchers.IO){
        return@withContext yemeklerDao.sepetYemekGetir(kullanici_adi).sepet_yemekler

    }
    suspend fun sepeteYemekEkle(yemek_adi: String,yemek_resim_adi: String,yemek_fiyat: Int,yemek_siparis_adet: Int,kullanici_adi: String){
        val crudCevap=yemeklerDao.sepeteYemekEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
        Log.e("Sepet Yemek","Success:${crudCevap.success} - Message:${crudCevap.message}")
    }
    suspend fun sepettenYemekSil(sepet_yemek_id: Int,kullanici_adi: String){
        val crudCevap=yemeklerDao.sepettenYemekSil(sepet_yemek_id,kullanici_adi)
        Log.e("Sepet Yemek","Success:${crudCevap.success} - Message:${crudCevap.message}")
    }

}
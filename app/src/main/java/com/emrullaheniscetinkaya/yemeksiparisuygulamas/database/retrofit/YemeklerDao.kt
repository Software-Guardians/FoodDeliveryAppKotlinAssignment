package com.emrullaheniscetinkaya.yemeksiparisuygulamas.database.retrofit

import androidx.lifecycle.LiveData
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.data.entity.CRUDCevap
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.data.entity.SepetYemeklerCevap
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.data.entity.YemeklerCevap
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface YemeklerDao {

    //yemekler/tumYemekleriGetir.php
    //yemekler/sepeteYemekEkle.php
    //yemekler/sepettekiYemekleriGetir.php
    //yemekler/sepettenYemekSil.php
    //resimler/ayran.png
    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun tumYemeklerGetir(): YemeklerCevap



    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    suspend fun sepetYemekGetir(
        @Field("kullanici_adi") kullanici_adi: String): SepetYemeklerCevap



    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    suspend fun sepeteYemekEkle(
        @Field("yemek_adi")yemek_adi: String,
        @Field("yemek_resim_adi")yemek_resim_adi: String,
        @Field("yemek_fiyat")yemek_fiyat: Int,
        @Field("yemek_siparis_adet")yemek_siparis_adet: Int,
        @Field("kullanici_adi")kullanici_adi: String

    ): CRUDCevap

    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun sepettenYemekSil(
        @Field("sepet_yemek_id")sepet_yemek_id: Int,
        @Field("kullanici_adi")kullanici_adi: String
    ): CRUDCevap

}
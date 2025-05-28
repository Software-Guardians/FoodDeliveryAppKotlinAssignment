package com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.data.entity.Yemekler
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.databinding.KartTasarimFavorilerBinding
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.fragment.FavorilerFragmentDirections
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.viewmodel.FavorilerViewModel
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.utils.gecisYap

class FavorilerAdapter(var mContext: Context,var yemekler: List<Yemekler>,var viewModel: FavorilerViewModel) : RecyclerView.Adapter<FavorilerAdapter.cardTutucu>() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): cardTutucu {
        sharedPreferences=mContext.getSharedPreferences("favorites", Context.MODE_PRIVATE)
        var binding= KartTasarimFavorilerBinding.inflate(LayoutInflater.from(mContext),parent,false)
        return cardTutucu(binding)
    }

    override fun onBindViewHolder(
        holder: cardTutucu,
        position: Int
    ) {
        val t=holder.tasarimFavorilerBinding
        val yemek=yemekler.get(position)
        t.textViewAd.text=yemek.yemek_adi
        t.textViewFiyat.text="₺"+yemek.yemek_fiyat.toString()
        resimGoster(yemek.yemek_resim_adi,t.imageView)
        t.imageButtonSilme.setOnClickListener {
            listedenElemanSil(yemek)
            notifyDataSetChanged()
        }
        t.imageView.setOnClickListener {
            val gonderilenNesne= FavorilerFragmentDirections.favorilerDetayGecis(yemek)
            Navigation.gecisYap(it,gonderilenNesne)
        }
    }

    override fun getItemCount(): Int {
        return yemekler.size

    }
    fun resimGoster(resimAdi: String,imageView: ImageView){
        val url="http://kasimadalan.pe.hu/yemekler/resimler/$resimAdi"
        Glide.with(mContext).load(url).override(150,150).into(imageView)
    }
    inner class cardTutucu(var tasarimFavorilerBinding: KartTasarimFavorilerBinding): RecyclerView.ViewHolder(tasarimFavorilerBinding.root)
    fun listedenElemanSil(yemek: Yemekler){
        var editor=sharedPreferences.edit()
        editor.putBoolean(yemek.yemek_adi,false).apply()
        viewModel.favoriYemekler.remove(yemek)
    }
}
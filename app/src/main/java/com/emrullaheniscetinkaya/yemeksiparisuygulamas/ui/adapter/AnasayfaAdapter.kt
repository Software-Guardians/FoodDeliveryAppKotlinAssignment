package com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.data.entity.Yemekler
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.databinding.KartTasarimiAnasayfaBinding
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.fragment.AnasayfaFragmentDirections
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.viewmodel.AnasayfaViewModel
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.utils.gecisYap
import com.google.android.material.snackbar.Snackbar

class AnasayfaAdapter(var mContext: Context,var yemekler: List<Yemekler>,var viewModel: AnasayfaViewModel): RecyclerView.Adapter<AnasayfaAdapter.cardTutucu>() {
    private var sharedPreferences=mContext.getSharedPreferences("favorites", Context.MODE_PRIVATE)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): cardTutucu {
        var binding= KartTasarimiAnasayfaBinding.inflate(LayoutInflater.from(mContext),parent,false)
        return cardTutucu(binding)
    }

    override fun onBindViewHolder(
        holder: cardTutucu,
        position: Int
    ) {
        val t=holder.tasarimBinding
        val yemek=yemekler.get(position)
        t.textViewAd.text=yemek.yemek_adi
        t.textViewFiyat.text="₺"+yemek.yemek_fiyat.toString()
        resimGoster(yemek.yemek_resim_adi,t.imageViewAna)
        if (isFavorite(yemek.yemek_adi))                t.imageButtonFavourites.setImageResource(mContext.resources.getIdentifier("favori_secili","drawable",mContext.packageName))
        else                 t.imageButtonFavourites.setImageResource(mContext.resources.getIdentifier("baseline_favorite_border_24","drawable",mContext.packageName))
        t.imageButtonFavourites.setOnClickListener {
            if (!isFavorite(yemek.yemek_adi)){
                t.imageButtonFavourites.setImageResource(mContext.resources.getIdentifier("favori_secili","drawable",mContext.packageName))
                sharedPreferences.edit().putBoolean(yemek.yemek_adi,true).apply()
            }
            else{
                t.imageButtonFavourites.setImageResource(mContext.resources.getIdentifier("baseline_favorite_border_24","drawable",mContext.packageName))
                sharedPreferences.edit().putBoolean(yemek.yemek_adi,false).apply()
            }
        }
        t.imageViewAna.setOnClickListener {
            val action= AnasayfaFragmentDirections.anasayfaDetayGecis(yemek)
            Navigation.gecisYap(it,action)
        }
        t.imageButtonEkleme.setOnClickListener {
            viewModel.sepeteYemekEkle("TeknolojiMuhafizi",yemek)
            Snackbar.make(t.root,"${yemek.yemek_adi} başarı ile sepete eklendi.", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return yemekler.size
    }

    inner class cardTutucu(var tasarimBinding: KartTasarimiAnasayfaBinding): RecyclerView.ViewHolder(tasarimBinding.root)
    fun resimGoster(resimAdi: String, imageView: ImageView){
        val url="http://kasimadalan.pe.hu/yemekler/resimler/$resimAdi"
        Glide.with(mContext).load(url).override(200,200).into(imageView)
    }
    fun isFavorite(yemekAdi: String): Boolean{
        return sharedPreferences.getBoolean(yemekAdi,false)
    }

}
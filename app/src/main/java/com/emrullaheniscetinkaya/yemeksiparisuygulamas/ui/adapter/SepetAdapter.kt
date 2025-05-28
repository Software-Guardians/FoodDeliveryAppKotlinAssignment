package com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.data.entity.SepetYemekler
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.databinding.KartSepetLayoutBinding
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.viewmodel.SepetViewModel
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class SepetAdapter(var mContext: Context,var sepetYemekler: List<SepetYemekler>,var viewModel: SepetViewModel):
    RecyclerView.Adapter<SepetAdapter.cardTutucu>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): cardTutucu {
        var binding= KartSepetLayoutBinding.inflate(LayoutInflater.from(mContext),parent,false)
        return cardTutucu(binding)

    }

    override fun onBindViewHolder(
        holder: cardTutucu,
        position: Int
    ) {
        var t=holder.kartSepetLayoutBinding
        var yemek=sepetYemekler.get(position)
        t.textViewAdSepet.text=yemek.yemek_adi
        t.textViewFiyatSepet.text="₺"+(yemek.yemek_fiyat*yemek.yemek_siparis_adet).toString()
        t.textViewSepetAdet.text=yemek.yemek_siparis_adet.toString()
        resimGoster(yemek.yemek_resim_adi,t.imageViewAnaSepet)
        t.imageButtonSilmeSepet.setOnClickListener {
                viewModel.sepettenYemekSilAdet(yemek)


        }
        t.imageButtonSilmeSepetCardView.setOnClickListener {
            viewModel.sepettenYemekSil(yemek)
        }
        t.imageButtonEklemeSepet.setOnClickListener {
            viewModel.sepeteYemekEkle("TeknolojiMuhafizi",yemek)
        }
    }

    override fun getItemCount(): Int {
        return sepetYemekler.size
    }

    inner class cardTutucu(var kartSepetLayoutBinding: KartSepetLayoutBinding): RecyclerView.ViewHolder(kartSepetLayoutBinding.root)
    fun resimGoster(resimAdi: String,imageView: ImageView){
        val url="http://kasimadalan.pe.hu/yemekler/resimler/$resimAdi"
        Glide.with(mContext).load(url).override(150,150).into(imageView)
    }
}
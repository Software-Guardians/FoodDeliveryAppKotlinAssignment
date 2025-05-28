package com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.R
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.databinding.FragmentDetayBinding
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.viewmodel.DetayViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetayFragment : Fragment() {
    private lateinit var binding: FragmentDetayBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel: DetayViewModel
    val args: DetayFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreferences=requireContext().getSharedPreferences("favorites", Context.MODE_PRIVATE)
        binding= FragmentDetayBinding.inflate(inflater,container,false)
        val yemek=args.yemeklerBundle
        resimGoster(yemek.yemek_resim_adi,binding.imageViewDetay)
        binding.textViewIsim.text=yemek.yemek_adi
        binding.textViewFiyatDetay.text="₺"+yemek.yemek_fiyat.toString()
        if (isFavorite(yemek.yemek_adi))                binding.imageButtonFavoriEkle.setImageResource(requireContext().resources.getIdentifier("favori_buyuk","drawable",requireContext().packageName))
        else                 binding.imageButtonFavoriEkle.setImageResource(requireContext().resources.getIdentifier("favori_degil_buyuk","drawable",requireContext().packageName))
        binding.imageButtonFavoriEkle.setOnClickListener {
            if (!isFavorite(yemek.yemek_adi)){
                binding.imageButtonFavoriEkle.setImageResource(requireContext().resources.getIdentifier("favori_buyuk","drawable",requireContext().packageName))
                sharedPreferences.edit().putBoolean(yemek.yemek_adi,true).apply()
            }
            else{
                binding.imageButtonFavoriEkle.setImageResource(requireContext().resources.getIdentifier("favori_degil_buyuk","drawable",requireContext().packageName))
                sharedPreferences.edit().putBoolean(yemek.yemek_adi,false).apply()
            }
        }
        binding.imageButtonSepeteEkle.setOnClickListener {
            if (binding.editTextDetay.text!=null && binding.editTextDetay.text.toString() !=""){
                viewModel.sepeteYemekEkle("TeknolojiMuhafizi",yemek,binding.editTextDetay.text.toString().toInt())
                val snackbar=Snackbar.make(it,"${yemek.yemek_adi} başarı ile sepete eklendi.", Snackbar.LENGTH_SHORT)
                val view = snackbar.view

                val params = view.layoutParams as FrameLayout.LayoutParams
                params.gravity = Gravity.CENTER
                view.layoutParams = params

                snackbar.show()
            }

        }
        return binding.root
    }
    fun resimGoster(resimAdi: String,imageView: ImageView){
        val url="http://kasimadalan.pe.hu/yemekler/resimler/$resimAdi"
        Glide.with(requireContext()).load(url).override(200,200).into(imageView)
    }
    fun isFavorite(yemekAdi: String): Boolean{
        return sharedPreferences.getBoolean(yemekAdi,false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: DetayViewModel by viewModels()
        viewModel=tempViewModel
    }
}
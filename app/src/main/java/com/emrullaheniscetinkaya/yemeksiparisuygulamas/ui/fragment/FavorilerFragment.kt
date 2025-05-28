package com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.R
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.data.entity.Yemekler
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.databinding.FragmentFavorilerBinding
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.adapter.AnasayfaAdapter
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.adapter.FavorilerAdapter
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.viewmodel.FavorilerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavorilerFragment : Fragment() {
    private lateinit var viewModel: FavorilerViewModel
    private lateinit var binding: FragmentFavorilerBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: FavorilerViewModel by viewModels()
        viewModel=tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFavorilerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences=requireContext().getSharedPreferences("favorites", Context.MODE_PRIVATE)
        val favorilerAdapter= FavorilerAdapter(requireContext(),viewModel.favoriYemekler,viewModel)
        binding.RecyclerView.adapter=favorilerAdapter
        binding.RecyclerView.layoutManager= StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)



        viewModel.yemekler.observe (viewLifecycleOwner){yemeklerListesi->

            yemekleriKontrolEt(yemeklerListesi)
            favorilerAdapter.notifyDataSetChanged()
        }


    }



    fun yemekleriKontrolEt(list: List<Yemekler>){
        viewModel.favoriYemekler.clear()
        for (item in list){
            val control= sharedPreferences.getBoolean(item.yemek_adi,false)
            if (control){
                viewModel.favoriYemekler.add(item)
            }
        }
    }
}
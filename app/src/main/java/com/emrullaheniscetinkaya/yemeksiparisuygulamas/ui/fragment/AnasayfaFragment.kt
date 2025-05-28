package com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.R
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.data.entity.Yemekler
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.databinding.ActivityMainBinding
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.databinding.FragmentAnasayfaBinding
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.adapter.AnasayfaAdapter
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.viewmodel.AnasayfaViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlin.coroutines.Continuation

@AndroidEntryPoint
class AnasayfaFragment : Fragment() {
    private lateinit var binding: FragmentAnasayfaBinding
    private lateinit var viewModel: AnasayfaViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var anasayfaAdapter: AnasayfaAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentAnasayfaBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences=requireContext().getSharedPreferences("favorites", Context.MODE_PRIVATE)
        viewModel.yemeklerList.observe (viewLifecycleOwner){yemeklerListesi->
            anasayfaAdapter= AnasayfaAdapter(requireContext(),yemeklerListesi,viewModel)
            binding.AnasayfaRecyclerView.adapter=anasayfaAdapter
            yemekleriKontrolEt(yemeklerListesi)
        }

        binding.AnasayfaRecyclerView.layoutManager= StaggeredGridLayoutManager(2,
            StaggeredGridLayoutManager.VERTICAL)
        binding.searchViewAnasayfa.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!=null && query!=""){
                    viewModel.yemekleriAra(query)
                    anasayfaAdapter.yemekler=viewModel.yemeklerFiltrelenmisList.value
                    anasayfaAdapter.notifyDataSetChanged()
                }
                else{
                    anasayfaAdapter.yemekler=viewModel.yemeklerList.value
                    anasayfaAdapter.notifyDataSetChanged()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!=null && newText!=""){
                    viewModel.yemekleriAra(newText)
                    anasayfaAdapter.yemekler=viewModel.yemeklerFiltrelenmisList.value
                    anasayfaAdapter.notifyDataSetChanged()
                }
                else{
                    anasayfaAdapter.yemekler=viewModel.yemeklerList.value
                    anasayfaAdapter.notifyDataSetChanged()
                }
                return true

            }

        }
        )


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: AnasayfaViewModel by viewModels()
        viewModel=tempViewModel
        anasayfaAdapter= AnasayfaAdapter(requireContext(),emptyList(),viewModel)
    }

    fun yemekleriKontrolEt(list: List<Yemekler>){
        val editor=sharedPreferences.edit()
        for (item in list){
            var hasKey=sharedPreferences.contains(item.yemek_adi)
            if (!hasKey){
                editor.putBoolean(item.yemek_adi,false).apply()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        anasayfaAdapter.notifyDataSetChanged()
    }
}
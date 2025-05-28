package com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.fragment

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.R
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.data.entity.SepetYemekler
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.databinding.FragmentSepetBinding
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.adapter.SepetAdapter
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.viewmodel.SepetViewModel
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.utils.gecisYap
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SepetFragment : Fragment() {
    private lateinit var binding: FragmentSepetBinding
    private lateinit var viewModel: SepetViewModel
    private lateinit var adapter: SepetAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSepetBinding.inflate(inflater,container,false)
        adapter= SepetAdapter(requireContext(),emptyList(),viewModel)
        binding.RecyclerView.adapter=adapter
                binding.RecyclerView.layoutManager= StaggeredGridLayoutManager(1,
            StaggeredGridLayoutManager.VERTICAL)
        viewModel.sepetYemekler.observe(viewLifecycleOwner) {
            butonKontrol(it)
            adapter.sepetYemekler=it
            adapter.notifyDataSetChanged()
        }
        binding.buttonOnayla.setOnClickListener {button->
            val snackbar= Snackbar.make(button,"Siparişi onaylamak istiyor musunuz?", Snackbar.LENGTH_SHORT).setAction("Onayla") {
                viewModel.tamaminiSil("TeknolojiMuhafizi")
                Navigation.gecisYap(button,R.id.sepetSplashGecis)
            }
            val view=snackbar.view
            val params=view.layoutParams as FrameLayout.LayoutParams
            params.gravity= Gravity.CENTER
            view.layoutParams=params
            snackbar.show()
        }
        viewModel.sepetYemeklerTutar.observe(viewLifecycleOwner) {tutar->
            textViewKontrol(tutar)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.sepetYemekleriYukle()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel:SepetViewModel by viewModels()
        viewModel=tempViewModel
    }
    fun textViewKontrol(tutar: Int){
        binding.textView2.text="₺"+tutar.toString()

    }
    fun butonKontrol(liste: List<SepetYemekler>){
        if (liste.isNullOrEmpty()){
            binding.textView2.visibility= View.GONE
            binding.buttonOnayla.visibility= View.GONE
        }
        else{
            binding.textView2.visibility= View.VISIBLE
            binding.buttonOnayla.visibility= View.VISIBLE
        }
    }
}
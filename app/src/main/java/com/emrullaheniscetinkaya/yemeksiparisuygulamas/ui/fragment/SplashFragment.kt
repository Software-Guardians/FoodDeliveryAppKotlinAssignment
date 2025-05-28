package com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.R
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.databinding.FragmentSplashBinding
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.ui.viewmodel.SplashViewModel
import com.emrullaheniscetinkaya.yemeksiparisuygulamas.utils.gecisYap
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    private lateinit var viewModel: SplashViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: SplashViewModel by viewModels()
        viewModel=tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSplashBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigasyonDurumu.observe (viewLifecycleOwner){hazir ->
            if (hazir){
                Navigation.gecisYap(view,R.id.splashAnasayfaGecis)
            }
        }

    }
}
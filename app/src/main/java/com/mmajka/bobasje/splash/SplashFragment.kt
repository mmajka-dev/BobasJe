package com.mmajka.bobasje.splash

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.mmajka.bobasje.R
import com.mmajka.bobasje.config.ConfigFragment
import com.mmajka.bobasje.databinding.SplashFragmentBinding
import com.mmajka.bobasje.home.HomeFragment
import com.mmajka.bobasje.utils.Preferences
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private lateinit var viewModel: SplashViewModel
    private lateinit var binding: SplashFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.splash_fragment, container, false)
        val anim = AnimationUtils.loadAnimation(binding.root.context, R.anim.fade_in)
        val navController = findNavController(this)

            if (onUserCheck()){
                val home = HomeFragment()
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, HomeFragment()).disallowAddToBackStack().commit()
                //navController.navigate(R.id.action_splashFragment_to_homeFragment)
            }else{
                val config = ConfigFragment()
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, config).disallowAddToBackStack().commit()
                //navController.navigate(R.id.action_splashFragment_to_configFragment)
            }

        GlobalScope.launch {
            viewModel.getChild()
            delay(200)
            binding.textView.startAnimation(anim)
        }
        return binding.root
    }

    private fun onUserCheck(): Boolean{
        val id = viewModel.generateID()
        val prefInstance = Preferences(context!!)

        return prefInstance.onUserCheck(id)
    }

}
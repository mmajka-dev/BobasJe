package com.mmajka.bobasje.config

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.mmajka.bobasje.R
import com.mmajka.bobasje.databinding.ConfigFragmentBinding
import com.mmajka.bobasje.home.HomeFragment
import com.mmajka.bobasje.utils.Preferences

class ConfigFragment : Fragment() {


    private lateinit var viewModel: ConfigViewModel
    private lateinit var binding: ConfigFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.config_fragment, container, false)
        viewModel = ViewModelProvider(this).get(ConfigViewModel::class.java)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val box = binding.inviteCheckBox

        val view = binding.inviteCheckBox
        val checked = view.isChecked
        val prefInstance = Preferences(context!!)

        box.setOnCheckedChangeListener { buttonView, isChecked ->
            when (buttonView!!.isChecked) {
                true -> {
                    binding.nameEdit.isEnabled = false
                    binding.codeEditText.visibility = View.VISIBLE
                    binding.goNext.setOnClickListener {
                        prefInstance.savePreferences(binding.codeEditText.text.toString())
                        goHome()
                    }
                }
                false -> {
                    binding.nameEdit.isEnabled = true
                    binding.codeEditText.visibility = View.GONE
                    binding.goNext.setOnClickListener {
                        setChild()
                        goHome()
                    }
                }
            }
        }
    }

    private fun setChild() {
        val name = binding.nameEdit.text.toString()
        viewModel.setValue(name)
    }

    fun goHome() {
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, HomeFragment()).disallowAddToBackStack().commit()
    }
}
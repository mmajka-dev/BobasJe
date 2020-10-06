package com.mmajka.bobasje.action

import android.app.Dialog
import android.content.SharedPreferences
import android.drm.DrmStore
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.mmajka.bobasje.R
import com.mmajka.bobasje.databinding.ActionFragmentBinding
import kotlinx.android.synthetic.main.action_fragment.view.*
import java.util.*

class ActionFragment : DialogFragment() {

    companion object{

        private const val KEY_TITLE = "KEY_TITLE"
        const val TAG = "SimpleDialog"

        fun newInstance(title: String): ActionFragment {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            val fragment = ActionFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: ActionViewModel
    private lateinit var binding: ActionFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ActionViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.action_fragment, container, false)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupClickListeners(view)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.window?.setBackgroundDrawable(null)
        dialog?.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    ///region------------------------Methods
    private fun setupView(view: View){
        view.title.text = arguments?.getString(KEY_TITLE)
        view.time.setOnClickListener {
            viewModel.callTimePicker(context!!, binding.time)
        }
    }

    private fun getTime(): String{
        return viewModel.getTime()
    }

    private fun getDate(): String{
        return viewModel.getDate()
    }

    private fun setupClickListeners(view: View){
        view.save.setOnClickListener {
            val title = view.title.text.toString()
            val text = view.additional_info.text.toString()
            viewModel.setActions(getDate(), getTime(), title, text)
            dismiss()
        }

        view.cancel.setOnClickListener {
            dismiss()
        }
    }


    //endregion
}
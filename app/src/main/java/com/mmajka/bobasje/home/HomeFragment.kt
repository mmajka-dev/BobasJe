package com.mmajka.bobasje.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.PopupMenu
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.mmajka.bobasje.R
import com.mmajka.bobasje.action.ActionFragment
import com.mmajka.bobasje.config.ConfigFragment
import com.mmajka.bobasje.databinding.HomeFragmentBinding
import com.mmajka.bobasje.invite.InviteFragment

class HomeFragment : Fragment() {

    private var isOpen: Boolean = false
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private lateinit var fab_open: Animation
    private lateinit var fab_close: Animation
    private lateinit var fab_clock: Animation
    private lateinit var fab_anticlock: Animation
    private lateinit var fadeIn: Animation
    private lateinit var fadeOut: Animation
    private lateinit var adapter: RvAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var popupMenu: PopupMenu
    private lateinit var toolbar: MaterialToolbar

    override fun onResume() {
        super.onResume()
        viewModel.getCare().observe(viewLifecycleOwner, Observer { newActions ->
            adapter = RvAdapter(newActions)
            recyclerView.adapter = adapter
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        fab_open = AnimationUtils.loadAnimation(binding.root.context, R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(binding.root.context, R.anim.fab_close)
        fab_clock = AnimationUtils.loadAnimation(binding.root.context, R.anim.fab_rotate_clock)
        fab_anticlock = AnimationUtils.loadAnimation(binding.root.context, R.anim.fab_rotate_anticlock)
        fadeIn = AnimationUtils.loadAnimation(binding.root.context, R.anim.fragment_fade_enter)
        fadeOut = AnimationUtils.loadAnimation(binding.root.context, R.anim.fragment_fade_exit)
        recyclerView = binding.homeRv
        toolbar = binding.toolbar


        val name = binding.name

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)


        //region------------------------- Listeners ------------------------------------------------


        binding.include.fabBath.setOnClickListener {
            callDialog(getString(R.string.bath))
            closeFabMenu()

        }
        binding.include.fabDiaper.setOnClickListener {
            callDialog(getString(R.string.diaper))
            closeFabMenu()
        }
        binding.include.fabSleep.setOnClickListener {
            callDialog(getString(R.string.sleep))
            closeFabMenu()
        }
        binding.include.fabFeeding.setOnClickListener {
            callDialog(getString(R.string.feeding))
            closeFabMenu()
        }
        binding.include.fabPlayTime.setOnClickListener {
            callDialog(getString(R.string.play_time))
            closeFabMenu()
        }
        binding.include.fabMain.setOnClickListener {
            if (isOpen){
                binding.include.fabMain.startAnimation(fab_anticlock)
                binding.include.container.setBackgroundColor(resources.getColor(R.color.transparent))
                binding.homeRv.suppressLayout(false)
                visibleOff()
                animationClose()
                isOpen = false
            }else{
                binding.include.fabMain.startAnimation(fab_clock)
                binding.include.container.setBackgroundColor(resources.getColor(R.color.expandableFabBG))
                binding.homeRv.suppressLayout(true)
                visibleOn()
                animationOpen()
                isOpen = true
            }
        }

            toolbar.setOnMenuItemClickListener { item ->
                when(item.itemId){
                    R.id.edit -> {
                        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, ConfigFragment()).addToBackStack("stack").commit()
                    }
                    R.id.invite -> {
                        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, InviteFragment()).addToBackStack("stack").commit()
                    }
                    R.id.clear -> {
                        viewModel.clearList()
                        setupRecycler()
                    }
                }
                true
            }

        //endregion
        //region ------------------------- Methods ------------------------------------------
        setupRecycler()
        getChild(name)
        //endregion

        //region ------------------------------Observers
        //endregion
        return binding.root
    }

    private fun setupRecycler(){
        viewModel.getCare()
        viewModel.actions.observe(viewLifecycleOwner, Observer {
            adapter = RvAdapter((it))
            val layoutManager = LinearLayoutManager(binding.root.context)
            layoutManager.reverseLayout = true
            layoutManager.stackFromEnd = true
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
        })


    }

    private fun getChild(name: TextView){
        viewModel.getChild(name)

    }

    private fun visibleOn(){
        binding.include.textviewBath.visibility = View.VISIBLE
        binding.include.textviewDiaper.visibility = View.VISIBLE
        binding.include.textviewFeeding.visibility = View.VISIBLE
        binding.include.textviewPlay.visibility = View.VISIBLE
        binding.include.textviewSleep.visibility = View.VISIBLE
        binding.include.fabPlayTime.visibility = View.VISIBLE
        binding.include.fabFeeding.visibility = View.VISIBLE
        binding.include.fabSleep.visibility = View.VISIBLE
        binding.include.fabDiaper.visibility = View.VISIBLE
        binding.include.fabBath.visibility = View.VISIBLE
    }

    private fun visibleOff(){
        binding.include.textviewBath.visibility = View.INVISIBLE
        binding.include.textviewDiaper.visibility = View.INVISIBLE
        binding.include.textviewFeeding.visibility = View.INVISIBLE
        binding.include.textviewPlay.visibility = View.INVISIBLE
        binding.include.textviewSleep.visibility = View.INVISIBLE
        binding.include.fabPlayTime.visibility = View.INVISIBLE
        binding.include.fabFeeding.visibility = View.INVISIBLE
        binding.include.fabSleep.visibility = View.INVISIBLE
        binding.include.fabDiaper.visibility = View.INVISIBLE
        binding.include.fabBath.visibility = View.INVISIBLE
    }

    private fun animationClose(){
        binding.include.textviewBath.startAnimation(fab_close)
        binding.include.textviewDiaper.startAnimation(fab_close)
        binding.include.textviewFeeding.startAnimation(fab_close)
        binding.include.textviewPlay.startAnimation(fab_close)
        binding.include.textviewSleep.startAnimation(fab_close)
        binding.include.fabPlayTime.startAnimation(fab_close)
        binding.include.fabFeeding.startAnimation(fab_close)
        binding.include.fabSleep.startAnimation(fab_close)
        binding.include.fabDiaper.startAnimation(fab_close)
        binding.include.fabBath.startAnimation(fab_close)
    }

    private fun animationOpen(){
        binding.include.textviewBath.startAnimation(fab_open)
        binding.include.textviewDiaper.startAnimation(fab_open)
        binding.include.textviewFeeding.startAnimation(fab_open)
        binding.include.textviewPlay.startAnimation(fab_open)
        binding.include.textviewSleep.startAnimation(fab_open)
        binding.include.fabPlayTime.startAnimation(fab_open)
        binding.include.fabFeeding.startAnimation(fab_open)
        binding.include.fabSleep.startAnimation(fab_open)
        binding.include.fabDiaper.startAnimation(fab_open)
        binding.include.fabBath.startAnimation(fab_open)
    }


    private fun closeFabMenu(){
        visibleOff()
        animationClose()
        binding.include.container.setBackgroundColor(resources.getColor(R.color.transparent))
        binding.include.fabMain.startAnimation(fab_anticlock)
        binding.homeRv.suppressLayout(false)
        isOpen = false
    }

    private fun callDialog(title: String){
        ActionFragment.newInstance(title)
            .dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            ActionFragment.newInstance(title).show(childFragmentManager, ActionFragment.TAG)
    }

}
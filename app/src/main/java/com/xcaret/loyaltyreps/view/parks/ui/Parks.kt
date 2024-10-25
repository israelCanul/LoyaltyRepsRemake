package com.xcaret.loyaltyreps.view.parks.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.data.api.XPark
import com.xcaret.loyaltyreps.databinding.FragmentParksBinding
import com.xcaret.loyaltyreps.databinding.FragmentTrainingBinding
import com.xcaret.loyaltyreps.view.base.BaseFragmentDataBinding
import com.xcaret.loyaltyreps.view.general.vm.MainViewModel
import com.xcaret.loyaltyreps.view.parks.vm.ParksViewModel
import com.xcaret.loyaltyv2.adapter.SlideInfoParksAdapter


class Parks() : BaseFragmentDataBinding<FragmentParksBinding>(),ParksInfoListeners {
    private val viewModel: MainViewModel by activityViewModels()
    lateinit var _viewModel: ParksViewModel
    override val tagForBar: String
        get() = "ParksFragment"
    override fun setHeaderFragment() {}
    lateinit var customAdapter: SlideInfoParksAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel = ViewModelProvider(this)[ParksViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBinding(FragmentParksBinding.inflate(inflater))
        setHeaderFragment()
        settingUpRecyclers()
        observer()

        return binding.root
    }

    private fun observer() {
        _viewModel.listParksTraining.observe(viewLifecycleOwner){
            Log.i(tagForBar, "observer: $it")
            it?.let{
                loadingDialog.dismiss()
                loadParks(it)
            }?:run{
                loadingDialog.show(parentFragmentManager,tagForBar)
            }
        }
    }
    override fun onResume() {
        super.onResume()
        _viewModel.fetchTrainingData()
    }
    fun settingUpRecyclers(){
        binding.lstParks.layoutManager = StaggeredGridLayoutManager(1, LinearLayout.HORIZONTAL)
        binding.lstParks.setHasFixedSize(true)

    }
    private fun loadParks(list: List<XPark>){
        customAdapter =  SlideInfoParksAdapter(::itemClickListener,requireContext(), list)
        binding.lstParks.adapter = customAdapter
    }

    override fun itemClickListener(item: XPark) {
        viewModel.setParkSection(item)
        val bundle = Bundle()
        bundle.putString("xpark_name", item.name)
        bundle.putString("xpark_id", item.id.toString())
//
        navigate(R.id.action_parks_to_parkDetail, bundle)//mandamos al detalle
    }

}

interface ParksInfoListeners{
    fun itemClickListener(item: XPark): Unit
}
package com.xcaret.loyaltyreps.view.home.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.data.config.FragmentTags
import com.xcaret.loyaltyreps.data.entity.XUser
import com.xcaret.loyaltyreps.data.utils.Utils.getUserLevel
import com.xcaret.loyaltyreps.databinding.FragmentHomeBinding
import com.xcaret.loyaltyreps.view.base.BaseFragmentDataBinding
import com.xcaret.loyaltyreps.view.home.vm.HomeViewModel

class FragmentHome: BaseFragmentDataBinding<FragmentHomeBinding>() {
    lateinit var _viewModel: HomeViewModel

    override val tagForBar: String
        get() = FragmentTags.HOME.value

    override fun setHeaderFragment() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog.show(parentFragmentManager, this.tag)
        _viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setBinding(FragmentHomeBinding.inflate(inflater))
        Log.i(tagForBar, "aqui")
        observers()
        listeners()
        fetchUserFromDB()
        return binding.root
    }
     private fun listeners(){
         binding.btnToTraining.setOnClickListener(){
             navigate(R.id.action_actionXHome_to_training)
         }
     }

    fun observers(){
        _parentActivity?.let{
            it._viewModel.currentUser.observe(viewLifecycleOwner) {xUser ->
                xUser?.let{user ->
                    loadingDialog.dismiss()
                    Log.i("$tagForBar current", "Observer: $user")

                    showUserInfo(user)
                }
            }
        }
    }
    fun fetchUserFromDB(){
        _parentActivity?.let{
            it._viewModel.setUser()
            it._viewModel.fetchUserDetailAPI()
        }
    }
    private fun showUserInfo(currentUser: XUser){
        val totalPoints = currentUser.puntosPorVentas
        val userLevel = getUserLevel(totalPoints)
        val animationLevel = userLevel * 0.1;
        Log.i(tagForBar, "$totalPoints $animationLevel $userLevel")
        binding.statusPoints.text = totalPoints.toString()
        binding.statusLevel.text = userLevel.toString()
        binding.statusStatus.text = if(currentUser.estatus) "Activo" else "Desactivado"
    }

}
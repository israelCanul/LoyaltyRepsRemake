package com.xcaret.loyaltyreps.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.xcaret.loyaltyreps.MainActivity
import com.xcaret.loyaltyreps.data.config.SettingsManager
import com.xcaret.loyaltyreps.view.general.ui.DialogLoadingFragment


abstract class BaseFragmentDataBinding<T>: Fragment(){
    private var _binding: T? = null
    val binding get() = _binding!!
    abstract val tagForBar: String
    fun setBinding(instance: T): T?{
        _binding = instance
        return _binding
    }


    fun hideBottomNavigation(){
        _parentActivity?.showBottomNavigation(false)
    }
    fun showBottomNavigation(){
        _parentActivity?.showBottomNavigation(true)
    }

    val loadingDialog: DialogLoadingFragment by lazy {
        DialogLoadingFragment.newInstance(Bundle.EMPTY)
    }
    fun getPackageName() = context?.let{ it.packageName}

    private var heightDefaultToolbar = 0f

    val _parentActivity: MainActivity? by lazy{
        requireActivity() as MainActivity
    }

    fun updateTitleOnToolbar(title: String? = ""){
        _parentActivity?.let{
            it.updateTitleToolbar(title)
        }
    }

    val settingsManager: SettingsManager by lazy { SettingsManager.getInstance(requireContext()) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }
    override fun onStart() {
        super.onStart()
    }
    fun onBackPressed(listener:() -> Unit) {
        requireActivity().onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                listener()
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navigate(destionation: Int, args: Bundle = Bundle.EMPTY){
        _parentActivity?.navigate(destionation, args)
    }

    fun popBackStack() = _parentActivity?.popBackStack()

    fun popBackStack(id: Int, inclusive: Boolean) = _parentActivity?.popBackStack(id, inclusive)

    fun fullscreen(){
        _parentActivity?.let{
            it.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }
    fun resetFullscreen(){
        _parentActivity?.let{
            it.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }

    abstract fun setHeaderFragment()
}
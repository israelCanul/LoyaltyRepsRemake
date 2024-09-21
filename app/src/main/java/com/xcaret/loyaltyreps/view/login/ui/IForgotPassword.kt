package com.xcaret.loyaltyreps.view.login.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.xcaret.loyaltyreps.databinding.ResetPasswordBinding
import com.xcaret.loyaltyreps.view.base.ActivityBase
import com.xcaret.loyaltyreps.view.login.vm.LoginViewModel

class IForgotPassword: ActivityBase() {
    private val _viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }
    private val loadingSuccess: DialogSuccessRetrievePass? by lazy {
        DialogSuccessRetrievePass.newInstance(Bundle.EMPTY)
    }

    private lateinit var binding: ResetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setObservers()
        setListeners()
    }

    fun setObservers() {
        _viewModel.successRetrieve.observe(this) {
            if (!it.isEmpty()) {
                hideLoading()
                binding.retrieveErrorCredentials.visibility = View.GONE
                binding.loginErrorCredentials.visibility = View.GONE
                binding.retrieveErrorCredentials.text = it
                binding.mRCX.text?.clear()
                binding.mRCX.clearFocus()
                loadingSuccess?.show(supportFragmentManager, loadingSuccess?.tag)
            }
        }
        _viewModel.errorRetrieve.observe(this) {
            if (!it.isEmpty()) {
                hideLoading()
                binding.retrieveErrorCredentials.visibility = View.VISIBLE
                binding.loginErrorCredentials.visibility = View.GONE
                binding.retrieveErrorCredentials.text = it
            }
        }
    }

    fun setListeners() {
        binding.retrieveButton.setOnClickListener {
            if (binding.mRCX.text.toString().isEmpty()) {
                binding.loginErrorCredentials.visibility = View.VISIBLE
            } else {
                //activamos el loading
                showLoading()
                //aqui mandamos a llamar el metodo
                _viewModel.retrieveEmail(binding.mRCX.text.toString())
            }
        }
    }
}
package com.xcaret.loyaltyreps.view.login.ui



import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.xcaret.loyaltyreps.MainActivity
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.databinding.ActivityLoginBinding
import com.xcaret.loyaltyreps.view.base.ActivityBase
import com.xcaret.loyaltyreps.view.login.vm.LoginViewModel


class LoginActivity : ActivityBase() {
    private val _viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }
    val CALL_PERMISION_CODE = 1
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
        setObservables()
    }

    fun setObservables(){
        _viewModel.mUser.observe(this){
            it?.let{
                _viewModel.saveODB(it)
                hideLoading()
                Log.i("Login View", "$it")
                launchMainActivity()
            }
        }
        _viewModel.errorLogin.observe(this){errorLogin ->
            if(errorLogin !== ""){
                hideLoading()
                binding.loginErrorCredentials.visibility = View.VISIBLE
            }
        }
    }
    private fun setListeners(){
        binding.loginNotice.makeLinks(
            Pair("(998) 980 0390", View.OnClickListener {
                makePhoneCall()
            })
        )
        binding.mUser.addTextChangedListener(rcxInputTextListener)
        binding.mPassword.addTextChangedListener(passWordInputTextListener)
        binding.btnRecuperarContrasena.setOnClickListener {
            goToRetrievePassword()
        }
    }

    private fun makePhoneCall(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CALL_PHONE),
                CALL_PERMISION_CODE)
        }else{
            try {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:9989800390")
                callIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(callIntent)
            }catch (er:Error){
                println(er)
            }
        }
    }
    fun launchMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun goToRetrievePassword(){
        val intent = Intent(this, IForgotPassword::class.java)
        startActivity(intent)
    }


    private val rcxInputTextListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
        }

        override fun beforeTextChanged(s: CharSequence, start: Int,
                                       count: Int, after: Int) {
            binding.emailField.error = null
        }

        override fun onTextChanged(s: CharSequence, start: Int,
                                   before: Int, count: Int) {
            activeLoginButton()
        }
    }

    private val passWordInputTextListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
        }

        override fun beforeTextChanged(s: CharSequence, start: Int,
                                       count: Int, after: Int) {
            binding.passwordField.error = null
        }

        override fun onTextChanged(s: CharSequence, start: Int,
                                   before: Int, count: Int) {
            activeLoginButton()
        }
    }

    private fun activeLoginButton() {
        binding.loginButton.isEnabled = validMail() && validPassword()
        if (binding.loginButton.isEnabled) {
            binding.loginButton.setOnClickListener {
                Log.i("Login Activity", "Ya esta habilitado")
                binding.loginErrorCredentials.visibility = View.GONE
                showLoading()
                _viewModel.login(binding.mUser.text.toString(), binding.mPassword.text.toString())
            }
            binding.loginButton.background = ContextCompat.getDrawable(this, R.drawable.button_green)
        } else {
            binding.loginButton.background = ContextCompat.getDrawable(this, R.drawable.button_disabled)
        }
    }

    private fun validMail() : Boolean {
        var valid = true
        if (binding.mUser.text.toString().isEmpty() ||
            binding.mUser.text.toString().length < 5 ) {
            binding.emailField.error = resources.getString(R.string.error_invalid_name)
            valid = false
        } else {
            binding.emailField.error = null
        }

        return valid
    }

    private fun validPassword() : Boolean {
        var valid = true
        if (binding.mPassword.text.toString().isEmpty() ||
            binding.mPassword.text.toString().length < 3) {
            binding.passwordField.error = resources.getString(R.string.error_incorrect_password)
            valid = false
        } else {
            binding.passwordField.error = null
        }
        return valid
    }

    fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
        val spannableString = SpannableString(this.text)
        for (link in links) {
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(view: View) {
                    Selection.setSelection((view as TextView).text as Spannable, 0)
                    view.invalidate()
                    link.second.onClick(view)
                }
            }
            val startIndexOfLink = this.text.toString().indexOf(link.first)
            spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        this.movementMethod = LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
        this.setText(spannableString, TextView.BufferType.SPANNABLE)
    }

}
package com.xcaret.loyaltyreps.view.general.ui


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.xcaret.loyaltyreps.BuildConfig
import com.xcaret.loyaltyreps.LoyaltyApp.Companion.getApp
import com.xcaret.loyaltyreps.MainActivity
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.data.config.FragmentTags
import com.xcaret.loyaltyreps.data.utils.Session
import com.xcaret.loyaltyreps.databinding.ActivitySplashBinding
import com.xcaret.loyaltyreps.view.general.vm.SplashViewModel
import com.xcaret.loyaltyreps.view.login.ui.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SplashActivity : AppCompatActivity() {
    val _viewModel: SplashViewModel by lazy {
        ViewModelProvider(this)[SplashViewModel::class.java]
    }

    val tagForBar: String
        get() = FragmentTags.Initial.value

    private lateinit var binding: ActivitySplashBinding
    private lateinit var fullscreenContent: FrameLayout
    private val hideHandler = Handler(Looper.myLooper()!!)
    @SuppressLint("InlinedApi")
    private val hidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar
        if (Build.VERSION.SDK_INT >= 30) {
            fullscreenContent.windowInsetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        } else {
            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            fullscreenContent.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LOW_PROFILE or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
    }
    private val showPart2Runnable = Runnable {
        // Delayed display of UI elements
        supportActionBar?.show()

    }
    private val hideRunnable = Runnable { hide() }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        delayedHide(100)
    }
    private fun setListeners(){

    }
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Set up the user interaction to manually show or hide the system UI.
        fullscreenContent = binding.frame

        var copyRight = "Versión: ${BuildConfig.VERSION_NAME}\n"
        copyRight += resources.getString(R.string.copy_right)
        binding.copyRight.text = copyRight
        setObservers()
        setListeners()
    }
    private fun setObservers(){
        _viewModel.resultDownload.observe(this){
            goMain()
        }
        _viewModel.currentUser.observe(this){
            goMain()
        }
    }
    override fun onStart() {
        super.onStart()
        _viewModel.load()//mandamos a descargar todq y carga user
    }

    private fun goMain(){
        //aqui se evalua si  lo mandado a descargar y el usuario asi como la animacion ya estan cargados completamente
        _viewModel.resultDownload.value?.let {  resultDownload ->
            if(_viewModel.resultUser.value == true && resultDownload.success){
                Log.i("Session que hay tuto", " descargado y continua")
                GlobalScope.launch(Dispatchers.IO) {
                    delay(2000)
                    _viewModel.currentUser.value?.let {
                        if(!Session.getTutoWatched(getApp().mContext)){
                            Log.i("Session que hay tuto", " tuto no visto")
                        }else{
                            Log.i("Session que hay tuto", " tuto visto yeih vamos a home ")
                        }
                        withContext(Dispatchers.Main){
                            launchMainActivity()
                        }
                    }?: run{
                        withContext(Dispatchers.Main){
                            launchLogin()
                        }
                    }
                }

            }
        }
    }

    fun launchMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun launchLogin(){
        Log.i("se carga login", "No hay user")
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }



    private fun hide() {
        // Hide UI first
        supportActionBar?.hide()

        // Schedule a runnable to remove the status and navigation bar after a delay
        hideHandler.removeCallbacks(showPart2Runnable)
        hideHandler.postDelayed(hidePart2Runnable, 3000.toLong())
    }

    /**
     * Schedules a call to hide() in [delayMillis], canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delayMillis: Int) {
        hideHandler.removeCallbacks(hideRunnable)
        hideHandler.postDelayed(hideRunnable, delayMillis.toLong())
    }
}

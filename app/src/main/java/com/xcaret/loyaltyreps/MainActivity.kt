package com.xcaret.loyaltyreps

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.xcaret.loyaltyreps.databinding.ActivityMainBinding
import com.xcaret.loyaltyreps.view.general.vm.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val _viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.mainNavHost) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
    private fun setupNavigation() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if(destination.label.toString().toLowerCase() != getString(R.string.menu_home).toLowerCase()){
                Log.i("Destino: ", " destino: $destination")
            }
        }
    }

    fun navigate(destination: Int, args: Bundle? = Bundle.EMPTY, navOptions: NavOptions? = null) {
        try {
            navController.navigate(destination, args, navOptions)
        } catch (e: Exception) {
            Log.e("Error navigate", destination.toString())
        }
    }
    fun updateTitleToolbar(title: String? = ""){
        //no se agrega titulo enesta version de app
    }
    fun popBackStack(){
        if (!navController.popBackStack()) finish()
    }
}
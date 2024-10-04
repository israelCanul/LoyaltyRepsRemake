package com.xcaret.loyaltyreps

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

    private var readPermissionGranted = false
    private var writePermissionGranted = false
    private lateinit var permissionsLauncher: ActivityResultLauncher<Array<String>>

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
        permissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            readPermissionGranted = permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE] ?: readPermissionGranted
            writePermissionGranted = permissions[android.Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: writePermissionGranted
        }
        setupNavigation()
        updateOrRequestPermissions()
    }
    private fun setupNavigation() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Log.i("Destino: ", " destino: ${destination.label}")
            when(destination.label.toString()){
                "QuizzFailedTraining" ->{
                    showBottomNavigation(false)
                    toggleToolbar(false)
                }
                "QuizzSuccessTraining" ->{
                    showBottomNavigation(false)
                    toggleToolbar(false)
                }
                else -> {
                    showBottomNavigation(true)
                    toggleToolbar(true)
                }
            }
//            if(destination.label.toString().lowercase() != getString(R.string.menu_home).lowercase()){
//                Log.i("Destino: ", " destino: $destination")
//            }
        }
    }

    fun showBottomNavigation(show: Boolean){
        binding.layoutBottomNav.visibility = if(show) View.VISIBLE else View.GONE
    }
    fun toggleToolbar(show: Boolean){
        binding.toolbarView.visibility = if(show) View.VISIBLE else View.GONE
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

    private fun updateOrRequestPermissions() {
        val hasReadPermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        val hasWritePermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        val minSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

        readPermissionGranted = hasReadPermission
        writePermissionGranted = hasWritePermission || minSdk29

        val permissionsToRequest = mutableListOf<String>()
        if(!writePermissionGranted) {
            permissionsToRequest.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if(!readPermissionGranted) {
            permissionsToRequest.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if(permissionsToRequest.isNotEmpty()) {
            permissionsLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }
}
package com.example.mkotestapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.mkotestapplication.databinding.ActivityMainBinding
import com.example.mkotestapplication.model.User
import com.example.mkotestapplication.service.InstAccessibilityService
import com.example.mkotestapplication.service.ServiceInterface
import com.example.mkotestapplication.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private val myService: InstAccessibilityService? by lazy {
        InstAccessibilityService.getSharedInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
        initObservers()
    }

    private fun initListeners() = with(binding) {
        buttonStartService.setOnClickListener {
            if (!checkAccessibilityPermission()) {
                Toast.makeText(this@MainActivity, getString(R.string.service_description), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private fun initObservers() {
        mainViewModel.user.asLiveData().observe(this) {
            binding.textUsername.text = it?.name
        }
    }

    private fun listenForService() {
        myService?.setListener(object : ServiceInterface {
            override fun onSuccessLogin(login: String) {
                mainViewModel.insertUser(User(login))
            }
        })
    }

    private fun checkAccessibilityPermission(): Boolean {
        val accessEnabled = Settings.Secure.getInt(this.contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED)
        return if (accessEnabled == 0) {
            // if not construct intent to request permission
            goToAccessibilitySettings()
            false
        } else {
            listenForService()
            openInstagramApplication()
            true
        }
    }

    private fun goToAccessibilitySettings() {
        startActivity(
            Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK }
        )
    }

    private fun openInstagramApplication() {
        val packageManager = this.packageManager
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0.toLong()))
            } else {
                @Suppress("DEPRECATION")
                packageManager.getPackageInfo(packageName, 0)
            }
            val launchIntent = packageManager.getLaunchIntentForPackage(getString(R.string.instagram_app_id))
            startActivity(launchIntent)
        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(this, "Instagram application is not installed", Toast.LENGTH_SHORT).show()
        }
    }

}
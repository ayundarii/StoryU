package com.dicoding.storyu

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dicoding.storyu.data.network.response.ApiResponse
import com.dicoding.storyu.databinding.ActivityMainBinding
import com.dicoding.storyu.presentation.add_story.AddStoryViewModel
import com.dicoding.storyu.presentation.login.LoginViewModel
import com.dicoding.storyu.presentation.register.RegisterViewModel
import com.dicoding.storyu.utils.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val loginViewModel: LoginViewModel by inject()
    private val registerViewModel: RegisterViewModel by inject()
    private val addStoryViewModel: AddStoryViewModel by inject()

    private lateinit var loginResultObserver: Observer<ApiResponse<String>>
    private lateinit var registerResultObserver: Observer<ApiResponse<String>>
    private lateinit var addStoryResultObserver: Observer<ApiResponse<String>>

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val navHostBottomBar =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navControllerBottomBar = navHostBottomBar.navController
        binding.bottomNavigation.setupWithNavController(navControllerBottomBar)
        navControllerBottomBar.addOnDestinationChangedListener { _, currentDestination, _ ->
            if (isMainPage(currentDestination)) {
                binding.bottomAppBar.visibility = View.VISIBLE
            } else {
                binding.bottomAppBar.visibility = View.GONE
            }
        }

        loginResultObserver = Observer { response: ApiResponse<String> ->
            Timber.d("Response is $response")
            val fragmentView = navHostFragment.childFragmentManager.fragments.firstOrNull()?.view
            when (response) {
                is ApiResponse.Success -> {
                    Timber.d("success")
                    loginViewModel.resetLoginResult()
                    navController.navigate(R.id.action_loginFragment_to_homeFragment)
                    fragmentView?.showSnackBar("Login successful, go and add a story!")
                }

                is ApiResponse.Error -> {
                    Timber.d("error")
                    fragmentView?.showSnackBar(response.errorMessage)
                }

                is ApiResponse.Loading -> {
                    Timber.d("loading")
                    fragmentView?.showSnackBar("Loading...")
                }

                is ApiResponse.Empty -> {
                    Timber.d("empty")
                }
            }
        }

        registerResultObserver = Observer { response ->
            Timber.d("Response is $response")
            val fragmentView = navHostFragment.childFragmentManager.fragments.firstOrNull()?.view
            when (response) {
                is ApiResponse.Success -> {
                    Timber.d("success")
                    registerViewModel.resetRegisterResult()
                    navController.navigate(R.id.action_registerFragment_to_loginFragment)
                    fragmentView?.showSnackBar("Account successfully made, go and login!")
                }

                is ApiResponse.Error -> {
                    Timber.d("error")
                    fragmentView?.showSnackBar(response.errorMessage)
                }

                is ApiResponse.Loading -> {
                    Timber.d("loading")
                    fragmentView?.showSnackBar("Loading...")
                }

                is ApiResponse.Empty -> {
                    Timber.d("empty")
                }
            }
        }

        addStoryResultObserver = Observer { response ->
            Timber.d("Response is $response")
            val fragmentView = navHostFragment.childFragmentManager.fragments.firstOrNull()?.view
            when (response) {
                is ApiResponse.Success -> {
                    Timber.d("success")
                    addStoryViewModel.resetAddStoryResult()
                    navController.navigate(R.id.action_addStoryFragment_to_homeFragment)
                    fragmentView?.showSnackBar("Story successfully added.")
                }

                is ApiResponse.Error -> {
                    Timber.d("error")
                    fragmentView?.showSnackBar("Make sure you added a photo.")
                }

                is ApiResponse.Loading -> {
                    Timber.d("loading")
                    fragmentView?.showSnackBar("Loading...")
                }

                is ApiResponse.Empty -> {
                    Timber.d("empty")
                }
            }

        }

        loginViewModel.loginResult.observe(this, loginResultObserver)
        registerViewModel.registerResult.observe(this, registerResultObserver)
        addStoryViewModel.addStoryResult.observe(this, addStoryResultObserver)
    }

    private fun isMainPage(currentDestination: NavDestination): Boolean {
        return currentDestination.id == R.id.homeFragment
                || currentDestination.id == R.id.mapsFragment
                || currentDestination.id == R.id.profileFragment
    }

    private fun View.showSnackBar(message: String) {
        Snackbar.make(
            this,
            message,
            Snackbar.LENGTH_LONG
        ).also { snackbar ->
            snackbar.setAction("OK") {
                snackbar.dismiss()
            }
        }.show()
    }
}

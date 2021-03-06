package com.joaquimley.transporteta.home

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.joaquimley.transporteta.R
import com.joaquimley.transporteta.home.favorite.FavoritesFragment
import com.joaquimley.transporteta.presentation.HomeViewModel
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HasSupportFragmentInjector/*, BottomNavigationView.OnNavigationItemSelectedListener*/ {

    lateinit var viewModel: HomeViewModel
    private val favouritesFragment = FavoritesFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupViewModel()
        if (hasReadSmsPermission().not()) {
            requestReadAndSendSmsPermission()
        }
        changeFragment(favouritesFragment)
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container, fragment)
                .commit()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    /**
     * Runtime permission shenanigans
     */
    private fun hasReadSmsPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestReadAndSendSmsPermission() {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS), SMS_PERMISSION_CODE)
    }

    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return fragmentInjector
    }

    companion object {
        const val SMS_PERMISSION_CODE = 1337
    }
}


//    private val favouritesFragment = FavoritesFragment.newInstance("")
//    lateinit var viewModel: HomeViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home)
//        setupViewModel()
//        setupNavigationView()
//    }
//
//    override fun onStart() {
//        super.onStart()
//        viewModel.observeSelectedScreen().observe(this, Observer {
//            when (it) {
//                NavigationType.FAVOURITES -> showFavouritesFragment()
//                NavigationType.DASHBOARD -> showSecondFragment()
//                NavigationType.NOTIFICATIONS -> showThirdAdminFragment()
//            }
//        })
//    }
//
//
//    private fun setupViewModel() {
//        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
//    }
//
//    private fun setupNavigationView() {
//        navigationView.setOnNavigationItemSelectedListener(this)
//    }
//
//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.navigation_favourites -> {
//                viewModel.setSelectedScreen(NavigationType.FAVOURITES)
//                true
//            }
//            R.id.navigation_dashboard -> {
//                viewModel.setSelectedScreen(NavigationType.DASHBOARD)
//                true
//            }
//            R.id.navigation_notifications -> {
//                viewModel.setSelectedScreen(NavigationType.NOTIFICATIONS)
//                true
//            }
//            else -> false
//        }
//    }
package com.awab.links.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.collection.forEach
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.awab.links.MainViewModel
import com.awab.links.R
import com.awab.links.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    private lateinit var binding:ActivityMainBinding
    private lateinit var navController:NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setupNavigation()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.fragment)

        binding.navigationView.setupWithNavController(navController)

        val destinations = mutableSetOf<Int>()
        navController.graph.nodes.forEach { _, value -> destinations.add(value.id) }
        appBarConfiguration = AppBarConfiguration(destinations, binding.drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

}
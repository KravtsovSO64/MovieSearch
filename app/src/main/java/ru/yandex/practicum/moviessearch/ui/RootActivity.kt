package ru.yandex.practicum.moviessearch.ui

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.yandex.practicum.moviessearch.R
import ru.yandex.practicum.moviessearch.databinding.ActivityRootBinding


class RootActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRootBinding
    private lateinit var dialog: MaterialAlertDialogBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.navigationBarColor = ContextCompat.getColor(this, R.color.dark)
        window.statusBarColor = ContextCompat.getColor(this, R.color.dark)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detailsFragment, R.id.moviesCastFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                else -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }

        dialog = MaterialAlertDialogBuilder(this)
            .setTitle("Вы действительно хотите выйти из приложения?")
            .setNegativeButton("Нет") {dialog, which ->

            }
            .setPositiveButton("Да") {dialog, which ->
                finish()
            }

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true
        ) {
            override fun handleOnBackPressed() {
                dialog.show()
            }

        })
    }
    fun animateBottomNavigationView() {
        binding.bottomNavigationView.visibility = View.GONE
    }

}
package com.duckinfotech.stikerdemo

import android.os.Bundle
import android.view.View
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.duckinfotech.stikerdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),
    NavController.OnDestinationChangedListener {

    private lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    }


    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.homeFragment -> {
                setBottomAppBarForHome(getBotttomAppBarMenuForDestination(destination))
            }
            R.id.stickerPackDetailsFragment -> {
                setBottomAppBarForDetails(getBotttomAppBarMenuForDestination(destination))
            }
        }
    }

    private fun setBottomAppBarForHome(@MenuRes menuRes: Int) {
        dataBinding.run {
            //fab.setImageState(intArrayOf(-android.R.attr.state_activated), true)
            bottomAppBar.visibility = View.VISIBLE
            //bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            bottomAppBar.replaceMenu(menuRes)
            bottomAppBar.performShow()
            //fab.show()
        }
    }

    private fun setBottomAppBarForDetails(@MenuRes menuRes: Int) {
        dataBinding.run {
            //fab.setImageState(intArrayOf(android.R.attr.state_activated), true)
            bottomAppBar.visibility = View.VISIBLE
            //bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            bottomAppBar.replaceMenu(menuRes)
            bottomAppBar.performShow()
            //fab.show()
        }
    }

    @MenuRes
    private fun getBotttomAppBarMenuForDestination(destination: NavDestination? = null): Int {
        val dest = destination ?: findNavController(R.id.nav_host_fragment).currentDestination
        return when (dest?.id) {
            R.id.homeFragment -> R.menu.bottom_app_bar_home_menu
            R.id.stickerPackDetailsFragment -> R.menu.bottom_app_bar_home_menu
            else -> R.menu.bottom_app_bar_home_menu
        }
    }
}

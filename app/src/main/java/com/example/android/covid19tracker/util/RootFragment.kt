package com.example.android.covid19tracker.util

import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.covid19tracker.R

open class RootFragment : Fragment() {
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> shareScreenshot()
            R.id.aboutFragment -> NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    fun shareScreenshot() {
        val bitmap = takeScreenshot(requireView().rootView)
        val uri = saveScreenshot(bitmap, requireActivity())
        val intent = getShareIntent(uri)
        startActivity(intent)
    }

    fun getShareIntent(uri: Uri) : Intent {
        return ShareCompat.IntentBuilder.from(requireActivity())
            .setStream(uri)
            .setType("image/jpeg")
            .intent
    }
}
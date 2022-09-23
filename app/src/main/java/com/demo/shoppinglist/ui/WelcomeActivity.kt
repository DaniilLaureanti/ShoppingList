package com.demo.shoppinglist.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.demo.shoppinglist.R

class WelcomeActivity : AppCompatActivity(), WelcomeFragment.OnAcceptListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val sharedPreferences = getSharedPreferences(
            SETTINGS_APP,
            Context.MODE_PRIVATE
        )
        hasVisited(sharedPreferences)

    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.welcome_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun hasVisited(sharedPreferences: SharedPreferences) {
        val hasVisited = sharedPreferences.getBoolean(HAS_VISITED, false)

        if (!hasVisited) {
            launchFragment(WelcomeFragment.newInstanceWelcomeFragment())
            val editor = sharedPreferences.edit()
            editor.putBoolean(HAS_VISITED, true)
            editor.apply()
        } else {
            launchMainActivity(this)
            finish()
        }
    }

    private fun launchMainActivity(context: Context) {
        val intent = MainActivity.newInstanceMainActivity(context)
        startActivity(intent)
    }

    companion object {
        private const val SETTINGS_APP = "settings_app"
        private const val HAS_VISITED = "has_visited"
    }

    override fun OnAccept() {
        supportFragmentManager.popBackStack()
        finish()
    }
}
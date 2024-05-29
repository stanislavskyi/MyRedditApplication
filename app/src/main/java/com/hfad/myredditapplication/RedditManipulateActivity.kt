package com.hfad.myredditapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RedditManipulateActivity : AppCompatActivity() {

    private var imageUrl = UNKNOWN_IMAGE_URL
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_reddit_manipulate)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.reddit_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        parseIntent()

        if(savedInstanceState == null) {
            launchRightMode()
        }

    }

    private fun launchRightMode() {
        val fragment = RedditFragment.newInstanceAddItem(imageUrl)
        launchFragment(fragment)
    }

    private fun launchFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.reddit_container, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_IMAGE_URL)) {
            throw RuntimeException("Param extra image is absent")
        }
        imageUrl = intent.getStringExtra(EXTRA_IMAGE_URL) ?: ""
    }

    companion object {
        private const val EXTRA_IMAGE_URL = "extra_image_url"
        private const val UNKNOWN_IMAGE_URL = ""

        fun newIntentImageMode(imageUrl: String, context: Context): Intent {
            val intent = Intent(context, RedditManipulateActivity::class.java)
            intent.putExtra(EXTRA_IMAGE_URL, imageUrl)
            return intent
        }
    }
}
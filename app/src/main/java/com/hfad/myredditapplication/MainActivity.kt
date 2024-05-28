package com.hfad.myredditapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hfad.myredditapplication.adapters.RedditAdapter
import com.hfad.myredditapplication.viewmodels.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var recyclerViewRedditPopularPosts: RecyclerView
    private lateinit var redditAdapter: RedditAdapter

    private var redditContainer: FragmentContainerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        redditContainer = findViewById(R.id.reddit_container)

        recyclerViewSetup()
        loadData()
    }
    private fun isOnePaneMode(): Boolean{
        return redditContainer == null
    }
    private fun loadData(){
        lifecycleScope.launch(Dispatchers.IO) {
            mainViewModel.listData.collect {
                redditAdapter.submitData(it)
            }
        }
    }
    private fun recyclerViewSetup() {
        recyclerViewRedditPopularPosts = findViewById(R.id.recyclerViewRedditPopularPosts)
        recyclerViewRedditPopularPosts.layoutManager = LinearLayoutManager(this@MainActivity)

        redditAdapter = RedditAdapter()

        recyclerViewRedditPopularPosts.adapter = redditAdapter

        setupClickListener()

    }
    private fun setupClickListener() {
        redditAdapter.onRedditPostClickListener = {
            if(isOnePaneMode()) {

                val intent = RedditManipulateActivity.newIntentImageMode(it,this@MainActivity)
                startActivity(intent)

            }else{
                val fragment = RedditFragment.newInstanceAddItem(it)
                launchFragment(fragment)
            }
        }
    }
    private fun launchFragment(fragment: Fragment){
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.reddit_container, fragment)
            .addToBackStack(null)
            .commit()
    }
    companion object{
        private const val REQUEST_CODE = 1001
    }
}
package com.hfad.myredditapplication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hfad.myredditapplication.databinding.FragmentRedditItemBinding
import com.hfad.myredditapplication.viewmodels.RedditViewModel
import com.hfad.myredditapplication.viewmodels.RedditViewModelFactory
import com.squareup.picasso.Picasso

class RedditFragment : Fragment() {

    private lateinit var imageUrl: String


    private var _binding: FragmentRedditItemBinding? = null
    private val binding: FragmentRedditItemBinding
        get() = _binding ?: throw RuntimeException("FragmentRedditItemBinding == null")

    private val viewModelFactory by lazy {
        RedditViewModelFactory(imageUrl, requireActivity().application)
    }

    private val redditViewModel: RedditViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[RedditViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRedditItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        val args = requireArguments()
        imageUrl = args.getString(EXTRA_SCREEN_MODE)!!

        Log.d("TEST_OF_DOWNLOAD_IMAGE", imageUrl)

        binding.buttonSaveImage.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                redditViewModel.downloadImage()
            } else {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            STORAGE_PERMISSION_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    redditViewModel.downloadImage()
                } else {
                    Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                // Ignore all other requests
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    private fun initViews() {

        val args = requireArguments()
        val image = args.getString(EXTRA_SCREEN_MODE)
        Picasso.get().load(image).error(android.R.drawable.ic_menu_gallery).into(binding.imageView)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val STORAGE_PERMISSION_CODE: Int = 1000

        fun newInstanceAddItem(imageUrl: String): RedditFragment {
            return RedditFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, imageUrl)
                }
            }
        }
    }
}
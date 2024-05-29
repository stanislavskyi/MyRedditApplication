package com.hfad.myredditapplication.viewmodels

import android.app.Application
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.hfad.myredditapplication.R
import com.squareup.picasso.Picasso
import java.io.OutputStream
import java.util.UUID


class RedditViewModel (
    private val imageUrl: String,
    private val application: Application,
) : ViewModel() {

    fun downloadImage() {
        Picasso.get().load(imageUrl).into(object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                bitmap?.let {
                    saveImageToGallery(it)
                }
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

            }
        })
    }

    private fun saveImageToGallery(bitmap: Bitmap) {

        val uniqueFileName = "downloadedImage_${UUID.randomUUID()}.jpg"

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, uniqueFileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
        }

        val uri = application.applicationContext.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )

        if (uri != null) {
            val outputStream: OutputStream? =
                application.applicationContext.contentResolver.openOutputStream(uri)
            outputStream?.let { os ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
                os.close()
                Toast.makeText(
                    application.applicationContext,
                    application.applicationContext.getString(R.string.image_saved_to_gallery_as, uniqueFileName),
                    Toast.LENGTH_SHORT
                ).show()
            } ?: run {
                Toast.makeText(
                    application.applicationContext,
                    application.applicationContext.getString(R.string.failed_to_save_image),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                application.applicationContext,
                application.applicationContext.getString(R.string.failed_to_create_new_mediastore_record),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}
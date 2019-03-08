package com.cittus.isv.complements.camera

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.CheckBox
import android.widget.ImageView
import com.cittus.isv.complements.Permissions
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class TakePicture(
    private var context: FragmentActivity,
    private var imageView: ImageView,
    private var captureButton: CheckBox
) {

    // Variable Main - Path
    private var mCurrentPhotoPath: String? = null;
    private var mCurrentPhoto: String = "";
    private var permissions: Permissions = Permissions(context)
    var fileUri: Uri? = null

    var count = 0;

    fun initProcess(request: Int) {
        if (permissions.checkPermissions(true)) launchCamera(request) else permissions.requestPermission(true)
    }

    private fun launchCamera(request: Int) {
        val values = ContentValues(1)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        val fileUri = context.contentResolver
            .insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(context.packageManager) != null) {
            this.fileUri = fileUri
            mCurrentPhotoPath = fileUri.toString()
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
            intent.addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION
                        or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
            context.startActivityForResult(intent, request)
        }
    }

    fun getFileUriMain(): Uri? {
        return this.fileUri
    }

    fun getPath(): String? {
        return mCurrentPhotoPath
    }

    fun getPathPhoto(): String? {
        return mCurrentPhoto
    }


    fun processCapturedPhoto(path: String?) {
        Log.e("Path Main", path)
        val file = createFile()
        val uri = Uri.fromFile(file)
        this.imageView.setImageURI(uri)
        resizeImage(file)
        captureButton.isChecked = true
    }


    //TODO hacer temporales para obtener despues 800 x 400


    fun resizeImage(file: File, scaleToW: Int = 800, scaleToH: Int = 400) {
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(file.absolutePath, bmOptions)
        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight

        // Determine how much to scale down the image
        val scaleFactor = Math.min(photoW / scaleToW, photoH / scaleToH)

        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor

        val resized = BitmapFactory.decodeFile(file.absolutePath, bmOptions) ?: return
        file.outputStream().use {
            resized.compress(Bitmap.CompressFormat.JPEG, 75, it)
            resized.recycle()
        }
    }

    @Throws(IOException::class)
    private fun createFile(): File {


        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        // Make File
        val file = File.createTempFile(
            "ITTUS_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = absolutePath
        }
        return file
    }
}



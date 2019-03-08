package com.cittus.isv.complements.camera

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import com.cittus.isv.complements.Permissions
import java.io.File
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
            print("Info Tack Came")
            this.fileUri = fileUri
            println(this.fileUri)
            mCurrentPhotoPath = fileUri.toString()
            println(mCurrentPhotoPath)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
            intent.addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION
                        or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
            println(intent)
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
        val file = createFile(path)

    }

    private val IMAGE_DIRECTORY_NAME = "ITTUS"

    private fun createFile(path: String?): File? {


        //Start Variables
        val mediaStorageDir = getDirectory()
        val pathImageMain = getPathImage(path)
        val imageFileName = getNameImage()

        // Make File
        val fileOld: File = File(pathImageMain)
        var fileNew: File = File(mediaStorageDir, imageFileName)
        fileOld.copyTo(fileNew)
        fileOld.delete()


        // Resize
        resizeImage(fileNew)

        captureButton.isChecked = true
        Log.i("Info", "File: $fileNew")
        return null
    }

    private fun resizeImage(file: File, scaleToW: Int = 800, scaleToH: Int = 400) {
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
        // Set Image
        val uriPath = Uri.parse(file.absolutePath)
        this.imageView.setImageURI(uriPath)

    }


    private fun getNameImage(): String {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        return "ITTUS_$timeStamp.jpg";
    }


    private fun getPathImage(path: String?): String? {
        val cursor = context.contentResolver.query(
            Uri.parse(path),
            Array(1) { android.provider.MediaStore.Images.ImageColumns.DATA },
            null, null, null
        )
        cursor.moveToFirst()
        val storageDirs = cursor.getString(0)
        cursor.close()
        return storageDirs
    }

    private fun getDirectory(): File? {
        // External sdcard location
        val mediaStorageDir = File(
            getStoragePath(),
            Environment.DIRECTORY_PICTURES + "/" + IMAGE_DIRECTORY_NAME
        )
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                displayMessage(context, "Unable to create directory.")
                return null
            }
        }
        return mediaStorageDir
    }

    private fun getStoragePath(): File {
        return Environment.getExternalStorageDirectory()
    }

    private fun displayMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }


    // File



}



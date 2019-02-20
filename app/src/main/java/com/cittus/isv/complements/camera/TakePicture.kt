package com.cittus.isv.complements.camera
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import com.cittus.isv.complements.Permissions
import com.cittus.isv.model.ActionsRequest.Companion.PERMISSION_REQUEST_CODE
import com.cittus.isv.model.ActionsRequest.Companion.REQUEST_IMAGE_CAPTURE
import com.cittus.isv.model.ActionsRequest.Companion.TAKE_PHOTO_REQUEST
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class TakePicture (private var context: Activity,private var imageView: ImageView,private var captureButton: CheckBox) {

    // Variable Main - Path
    private var mCurrentPhotoPath: String? = null;
    private var permissions:Permissions = Permissions(context)

    fun initProcess() {
        if (permissions.checkPermissionsCamera()) launchCamera() else permissions.requestPermission()
    }

    private fun launchCamera() {
        val values = ContentValues(1)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        val fileUri = context.contentResolver
            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(intent.resolveActivity(context.packageManager) != null) {
            mCurrentPhotoPath = fileUri.toString()
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            context.startActivityForResult(intent, TAKE_PHOTO_REQUEST)


        }


    }


    public fun processCapturedPhoto() {
        val cursor = context.contentResolver.query(Uri.parse(mCurrentPhotoPath),
            Array(1) {android.provider.MediaStore.Images.ImageColumns.DATA},
            null, null, null)
        cursor.moveToFirst()
        val photoPath = cursor.getString(0)
        cursor.close()
        //TODO hacer temporales para obtener despues
        val file = File(photoPath)
        resizeImage(file,125)
        val uri = Uri.fromFile(file)
        imageView.setImageURI(uri)
        captureButton.isChecked = true
    }

    fun resizeImage(file: File, scaleTo: Int = 1024) {
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(file.absolutePath, bmOptions)
        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight

        // Determine how much to scale down the image
        val scaleFactor = Math.min(photoW / scaleTo, photoH / scaleTo)

        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor

        val resized = BitmapFactory.decodeFile(file.absolutePath, bmOptions) ?: return
        file.outputStream().use {
            resized.compress(Bitmap.CompressFormat.JPEG, 75, it)
            resized.recycle()
        }
    }

    fun scaleBitmap(bitmapToScale: Bitmap?, newWidth: Float, newHeight: Float): Bitmap? {
        if (bitmapToScale == null)
            return null
        //get the original width and height
        val width = bitmapToScale.width
        val height = bitmapToScale.height
        // create a matrix for the manipulation
        val matrix = Matrix()

        // resize the bit map
        matrix.postScale(newWidth / width, newHeight / height)

        // recreate the new Bitmap and set it back
        return Bitmap.createBitmap(bitmapToScale, 0, 0, bitmapToScale.width, bitmapToScale.height, matrix, true)
    }

}



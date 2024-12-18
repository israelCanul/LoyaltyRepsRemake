package com.xcaret.loyaltyreps.data.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.xcaret.loyaltyreps.R
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    const val DRAWABLE = "drawable"

    fun getLanguageLargeNameByRef( name: String): String {
        var lName: String = "Español"
        when (name) {
            "ES" -> {
                lName = "Español"
            }
            "EN" ->{
                lName = "Inglés"
            }
            "FR" ->{
                lName = "Francés"
            }
            "DE" ->{
                lName = "Alemán"
            }
            "RU" ->{
                lName = "Ruso"
            }
            "CH" ->{
                lName = "Chino"
            }
        }
        return lName
    }
    fun getLanguageFlagFromDrawableByName(context: Context, name: String): Bitmap? {
        var image: Int = R.drawable.mex
        when (name) {
            "ES" -> {
                image = R.drawable.mex
            }
            "EN" ->{
                image = R.drawable.usa
            }
            "FR" ->{
                image = R.drawable.fran
            }
            "DE" ->{
                image = R.drawable.alem
            }
            "RU" ->{
                image = R.drawable.rus
            }
            "CH" ->{
                image = R.drawable.frame
            }
        }
        val d = ContextCompat.getDrawable(context, image)
        val bitmap = d?.let { drawableToBitmap(it) }
        return bitmap
    }

    fun getParkLogoBitmapFromDrawableByName(context: Context, name: String): Bitmap?{
        var image: Int = R.drawable.parques_logo
        when(name){
            "XCARET" -> {
                image = R.drawable.xcaret_logo
            }
            "XAILING CATAMARÁN" -> {
                image = R.drawable.catamaran_logo
            }
            "XAILING FERRY" -> {
                image = R.drawable.ferry_logo
            }
            "COBÁ" -> {
                image = R.drawable.coba_logo
            }
            "Actividades Extraordinarias" -> {
//                image = R.drawable.extraordinarias
            }
            "Parques" -> {
                image = R.drawable.parques_logo
            }
            "XPLOR FUEGO" -> {
                image = R.drawable.xplor_fuego_logo
            }
            "XICHÉN CLÁSICO" -> {
                image = R.drawable.xichen_clasico_logo
            }
            "XAVAGE" -> {
                image = R.drawable.xavage_logo
            }
            "XENOTES" -> {
                image = R.drawable.xenotes_logo
            }
            "XICHÉN DELUXE" -> {
                image = R.drawable.xichen_deluxe_logo
            }
            "XENSES" -> {
                image = R.drawable.xenses_logo
            }
            "XOXIMILCO" -> {
                image = R.drawable.xoximilco_logo
            }
            "XPLOR" -> {
                image = R.drawable.xplor_logo
            }
            "XEL-HÁ" -> {
                image = R.drawable.xelha_logo
            }
            else ->{
                image = R.drawable.parques_logo
            }
        }
        val d = ContextCompat.getDrawable(context, image)
        val bitmap = d?.let { drawableToBitmap(it) }
        return bitmap
    }
    fun getParkBitmapFromDrawableByName(context: Context, name: String): Bitmap?{
        var image: Int = R.drawable.parques
        when(name){
            "XCARET" -> {
                image = R.drawable.xcaret
            }
            "XAILING CATAMARÁN" -> {
                image = R.drawable.xailing_catamaran
            }
            "XAILING FERRY" -> {
                image = R.drawable.xailing_ferry
            }
            "COBÁ" -> {
                image = R.drawable.coba
            }
            "Actividades Extraordinarias" -> {
                image = R.drawable.extraordinarias
            }
            "Parques" -> {
                image = R.drawable.parques
            }
            "XPLOR FUEGO" -> {
                image = R.drawable.xplor_fuego
            }
            "XICHÉN CLÁSICO" -> {
                image = R.drawable.xichen_clasico
            }
            "XAVAGE" -> {
                image = R.drawable.xavage
            }
            "XENOTES" -> {
                image = R.drawable.xenotes
            }
            "XICHÉN DELUXE" -> {
                image = R.drawable.xichen_deluxe
            }
            "XENSES" -> {
                image = R.drawable.xenses
            }
            "XOXIMILCO" -> {
                image = R.drawable.xoximilco
            }
            "XPLOR" -> {
                image = R.drawable.xplor
            }
            "XEL-HÁ" -> {
                image = R.drawable.xelha
            }
            else ->{
                image = R.drawable.parques
            }
        }
        val d = ContextCompat.getDrawable(context, image)
        val bitmap = d?.let { drawableToBitmap(it) }
        return bitmap
    }
    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // Single color bitmap will be created of 1x1 pixel
        } else {
            Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        }

        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
    fun getDrawableId(context: Context?, name: String?): Int?{
        var id: Int? = null
        try {
            context?.let {
                id = context.resources.getIdentifier(name, DRAWABLE, context.packageName)
            }
        }catch (e: Exception){
            id = 0
        }
        return id
    }

    fun responseHandler(code: Int, result : (success: Boolean?) -> Unit, error: (error: Boolean?) -> Unit){
        when(code){
            204 -> result(false )
            in 401.. 525 -> error(true)
            200 -> result(true)
        }
    }

    fun fixHeightTopView(view: View){
        try {
            val params = view.layoutParams
            params.height = params.height + getStatusBarHeigth(view.context)
            view.layoutParams = params
        }catch (e: Exception) { e.printStackTrace() }
    }
    fun getStatusBarHeigth(cntx: Context): Int{
        val resourceId = cntx.resources.getIdentifier("status_bar_height", "dimen", "android")
        return if(resourceId > 0){ cntx.resources.getDimensionPixelSize(resourceId) } else { 0 }
    }
    @SuppressLint("SimpleDateFormat")
    fun getTimeInMilliseconds(finalDate: String) : Long {
        var timeLeftInMillis = 0L

        try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val fDate: Date = dateFormat.parse(finalDate)!!

            println("current time in sdsdsdmillis ${fDate.time}")

            val currDateInMillis = Date().time
            println("current time in millis $currDateInMillis")

            timeLeftInMillis = fDate.time - currDateInMillis
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        return timeLeftInMillis
    }


    fun getImageGallery(directory: String, imageName: String, imgUrl: String, callback: (file: File?, urlImage: String?) -> Unit) {
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()+ "/${directory}/JPEG_${imageName}.jpg")
        Log.i("GAdapter","$file ${file.exists()}")
        if(file.exists()){
            return callback(file, null)
        }else{
            return callback(null, imgUrl)
        }
    }

     fun saveImage(image: Bitmap,directory: String, name: String, context: Context): String? {
        var savedImagePath: String? = null
        val imageFileName = "JPEG_$name.jpg"
        val storageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString() + "/$directory"
        )
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }
        if (success) {
            val imageFile = File(storageDir, imageFileName)
            savedImagePath = imageFile.getAbsolutePath()
            try {
                val fOut: OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            // Add the image to the system gallery
            galleryAddPic(savedImagePath, context)
            //Toast.makeText(this, "IMAGE SAVED", Toast.LENGTH_LONG).show() // to make this working, need to manage coroutine, as this execution is something off the main thread
        }
        return savedImagePath
    }

    private fun galleryAddPic(imagePath: String?, context: Context) {
        imagePath?.let { path ->
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val f = File(path)
            val contentUri: Uri = Uri.fromFile(f)
            mediaScanIntent.data = contentUri
            context?.sendBroadcast(mediaScanIntent)
        }
    }

    fun getUserLevel(totalPoints: Int) : Int {
        var myLevel:Int = 1;
        when(totalPoints){
            in 0..1999 ->{
                println("topreplevel in 1 $totalPoints")
                myLevel = 1
            }
            in 2000..3999 ->{
                myLevel = 2
            }
            in 4000..5999 ->{
                myLevel = 3
            }
            in 6000..7999 ->{
                myLevel = 4
            }
            in 8000..9999 ->{
                myLevel = 5
            }
            in 10000..11999 ->{
                myLevel = 6
            }
            in 12000..13999 ->{
                myLevel = 7
            }
            in 14000..15999 ->{
                myLevel = 8
            }
            in 16000..17999 ->{
                myLevel = 9
            }
            else -> myLevel = 10
        }

        return myLevel
    }

}
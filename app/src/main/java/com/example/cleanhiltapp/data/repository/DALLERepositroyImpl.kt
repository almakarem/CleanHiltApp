package com.example.cleanhiltapp.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.example.cleanhiltapp.data.remote.DALLEApi
import com.example.cleanhiltapp.data.remote.data_transfer_object.GeneratedImage
import com.example.cleanhiltapp.domain.model.DALLERequestBody
import com.example.cleanhiltapp.domain.repository.DALLERepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class DALLERepositroyImpl   @Inject constructor(
    private val api: DALLEApi
) : DALLERepository {
    override suspend fun getPrompt(message: DALLERequestBody): GeneratedImage {
        return api.getPrompt(message)
    }

    override suspend fun editImage(
        image: File,
        n: Int,
        size: String
    ): Response<GeneratedImage> {
        // Convert the original image file to PNG format
        val pngImageFile = convertImageToPng(image)

        // Create a MultipartBody.Part using the PNG image file
        val requestFile: RequestBody = pngImageFile.asRequestBody("image/png".toMediaTypeOrNull())
        val body: MultipartBody.Part = MultipartBody.Part.createFormData("image", pngImageFile.name, requestFile)

        // Convert other parameters to RequestBody as before
        val requestBodyN = n.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val requestBodySize = size.toRequestBody("text/plain".toMediaTypeOrNull())

        // Call the API with the modified parameters
        return api.editImage(body, requestBodyN, requestBodySize)
    }

    private fun convertImageToPng(originalImageFile: File): File {
        // Decode the original bitmap
        val originalBitmap = BitmapFactory.decodeFile(originalImageFile.absolutePath)

        // Create a mutable bitmap with RGBA format
        val convertedBitmap = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, Bitmap.Config.ARGB_8888)

        // Copy the original bitmap to the new one, this ensures the new bitmap has an alpha channel
        val canvas = Canvas(convertedBitmap)
        canvas.drawBitmap(originalBitmap, 0f, 0f, null)

        // Create a new file for the converted image
        val pngFile = File(originalImageFile.parent, "converted_image_with_alpha.png")
        FileOutputStream(pngFile).use { outStream ->
            // Compress and write the new bitmap to the file as PNG
            convertedBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
        }

        // Clean up if needed
        originalBitmap.recycle()

        return pngFile
    }


}
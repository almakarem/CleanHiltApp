package com.example.cleanhiltapp.presentation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.net.http.HttpException
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresExtension
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.cleanhiltapp.R
import com.example.cleanhiltapp.common.Resource
import com.example.cleanhiltapp.databinding.ActivityMainBinding
import com.example.cleanhiltapp.databinding.ActivityTextToImageBinding
import com.example.cleanhiltapp.domain.model.DALLEImageRequestBody
import com.example.cleanhiltapp.domain.model.DALLERequestBody
import com.example.cleanhiltapp.domain.model.Message
import com.example.cleanhiltapp.presentation.ChatGPTResponse.ChatGPTViewModel
import com.example.cleanhiltapp.presentation.Image.ToImageViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

@AndroidEntryPoint
class TextToImageActivity : AppCompatActivity() {

    var isSelected: Boolean = false

    lateinit var file: File

    private val imagePickerRequestCode = 1001

    private lateinit var binding: ActivityTextToImageBinding
    private val viewModel: ToImageViewModel by viewModels()
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityTextToImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.upload.setOnClickListener{
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openImageSelector()
            } else {
                // Request the permission. The result is handled in onRequestPermissionsResult.
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), imagePickerRequestCode)
            }
        }

        binding.idEdtQuery.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                binding.idTVResponse.text = "Please wait.."
                val queryText = binding.idEdtQuery.text.toString()
                binding.idTVQuestion.text = queryText
                if (queryText.isNotEmpty()) {
                    // Create a message to send
                    val userMessage = Message("user", queryText)
                    // Prepare the request body with the model, messages, and any other parameters
                    if (isSelected){
                        val imageRequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
                        val promptRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), userMessage.content)
                        val nRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), "1") // Assuming n is always 1
                        val sizeRequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), "1024x1024") // Assuming the size is always 1024x1024

                        try {
                            viewModel.editImage(
                                MultipartBody.Part.createFormData("image", file.name, imageRequestBody),
                                1,
                                userMessage.content,
                                "1024x1024".trim()
                            )
                        } catch (e: HttpException) {
                            // Handle the HttpException, e.g., log it or display an error message
                            Log.e("RetrofitError", "HttpException: ${e.message}")
                        } catch (e: Exception) {
                            // Handle any other exceptions
                            Log.e("RetrofitError", "Exception: ${e.message}")
                        }

                    }else{
                        val requestBody = DALLERequestBody(
                            "dall-e-3",
                            listOf(userMessage).last().content,
                            "1024x1024",
                            1
                        )
                        try {
                            viewModel.sendMessage(requestBody)
                        } catch (e: HttpException) {
                            // Handle the HttpException, e.g., log it or display an error message
                            Log.e("RetrofitError", "HttpException: ${e.message}")
                        } catch (e: Exception) {
                            // Handle any other exceptions
                            Log.e("RetrofitError", "Exception: ${e.message}")
                        }
                    }




                } else {
                    Toast.makeText(this@TextToImageActivity, "Please enter your query..", Toast.LENGTH_SHORT).show()
                }
                return@OnEditorActionListener true
            }
            false
        })

        lifecycleScope.launchWhenStarted {
            viewModel.chatResponse.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // Show loading indicator
                        binding.idTVResponse.text = "Loading..."
                    }
                    is Resource.Success -> {
                        // Update UI with the successful response
                        val data = resource.data
                        if (data != null && data.data.isNotEmpty()) {
                            println(data.data[0].url)
                            println(data.data[0].revised_prompt)
                            binding.responseImage.load(data.data[0].url)
                            binding.idTVResponse.setText(data.data[0].revised_prompt)
                            binding.idEdtQuery.setText("")
                        } else {
                            binding.idTVResponse.text = "No response received."
                        }
                    }
                    is Resource.Error -> {
                        // Show error message
                        Toast.makeText(this@TextToImageActivity, resource.message ?: "An error occurred", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    private fun openImageSelector() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, imagePickerRequestCode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == imagePickerRequestCode && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openImageSelector()
        } else {
            Toast.makeText(this, "Permission is required to access images.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == imagePickerRequestCode) {
            // The URI of the selected image is in data.getData()
            val imageUri: Uri? = data?.data
            file = uriToFile(this,imageUri!!)!!
            binding.uploadedImage.load(imageUri)
            isSelected = true
        }
    }

    fun uriToFile(context: Context, uri: Uri): File? {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        inputStream?.let {
            val tempFile = createTempFile(context)
            tempFile?.let { file ->
                try {
                    val outputStream = FileOutputStream(file)
                    inputStream.use { input ->
                        outputStream.use { output ->
                            input.copyTo(output)
                        }
                    }
                    return file
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return null
    }

    private fun createTempFile(context: Context): File? {
        val fileName = "temp_image_" + System.currentTimeMillis()
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDir)
    }

}
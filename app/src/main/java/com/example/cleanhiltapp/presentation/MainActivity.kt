package com.example.cleanhiltapp.presentation

import android.net.http.HttpException
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.cleanhiltapp.R
import com.example.cleanhiltapp.common.Resource
import com.example.cleanhiltapp.databinding.ActivityMainBinding
import com.example.cleanhiltapp.domain.model.ChatRequestBody
import com.example.cleanhiltapp.presentation.ChatGPTResponse.ChatGPTViewModel
import com.example.cleanhiltapp.presentation.ui.theme.CleanHiltAppTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.RequestBody
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ChatGPTViewModel by viewModels()

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.idEdtQuery.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                binding.idTVResponse.text = "Please wait.."
                if (binding.idEdtQuery.text.toString().isNotEmpty()) {
                    val requestBody = ChatRequestBody("text-davinci-003",binding.idEdtQuery.text.toString(),100,
                        0f, 1f,0, 0, "\n")
                    try {
                        viewModel.sendMessage(requestBody)
                    } catch (e: HttpException) {
                        // Handle the exception, e.g., log it or display an error message
                        Log.e("RetrofitError", "HttpException: ${e.message}")
                    } catch (e: Exception) {
                        // Handle any other exceptions
                        Log.e("RetrofitError", "Exception: ${e.message}")
                    }


                } else {
                    Toast.makeText(this, "Please enter your query..", Toast.LENGTH_SHORT).show()
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
                    }
                    is Resource.Success -> {
                        // Update UI with the successful response
                        val data = resource.data
                        if (data !== null){
                            binding.idTVResponse.text = binding.idEdtQuery.text.toString() + data.response.get(0).toString()
                        }
                        else
                            binding.idTVResponse.text = binding.idEdtQuery.text.toString()
                    }
                    is Resource.Error -> {
                        // Show error message
                        Toast.makeText(this@MainActivity, resource.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    }
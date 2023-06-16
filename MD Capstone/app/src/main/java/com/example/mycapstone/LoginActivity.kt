package com.example.mycapstone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mycapstone.app.ApiConfig
import com.example.mycapstone.databinding.ActivityLoginBinding

import com.example.mycapstone.model.postSignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this@LoginActivity, ProfileActivity::class.java)
            startActivity(intent)
        }
        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    fun login() {
        if (binding.edtEmail.text.isEmpty()) {
            binding.edtEmail.error = "Kolom Email tidak boleh kosong"
            binding.edtEmail.requestFocus()
            return
        } else if (binding.edtPassword.text.isEmpty()) {
            binding.edtPassword.error = "Kolom Password tidak boleh kosong"
            binding.edtPassword.requestFocus()
            return
        }

        binding.pb.visibility = View.VISIBLE
        /*
        ApiConfig.instanceRetrofit.login(binding.edtEmail.text.toString(), binding.edtPassword.text.toString()).enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<postSignUpResponse>, t: Throwable) {
                binding.pb.visibility = View.GONE
                Toast.makeText(this@LoginActivity, "Error:" + t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<postSignUpResponse>, response: Response<postSignUpResponse>) {
                binding.pb.visibility = View.GONE
                val respon = response.body()!!
                if (respon.success == 1) {



                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@LoginActivity, "Selamat datang " , Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@LoginActivity, "Error:" , Toast.LENGTH_SHORT).show()
                }
            }
        })

         */
    }


}



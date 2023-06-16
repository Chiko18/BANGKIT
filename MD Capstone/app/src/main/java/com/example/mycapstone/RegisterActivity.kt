package com.example.mycapstone

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.mycapstone.app.ApiConfig
import com.example.mycapstone.databinding.ActivityRegisterBinding
import com.example.mycapstone.model.postSignUpResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : BaseActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReg.setOnClickListener { view ->
            postSignUp(binding.etFirstname.text.toString(),
                binding.etEmail.text.toString(),
                binding.etAddress.text.toString(),
                binding.etPasswordReg.text.toString(),
                binding.etPasswordRegConfrm.text.toString()
            )
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            validasiregister()

        }
        binding.loginReg.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun postSignUp(name: String, email: String, address: String, password: String, confirmPassword: String) {
        val client = ApiConfig.getApiService().register("","","","","")
        client.enqueue(object : Callback<postSignUpResponse>{
            override fun onResponse(
                call: Call<postSignUpResponse>,
                response: Response<postSignUpResponse>
            ) {
                val responeBody = response.body()
                if (response.isSuccessful && responeBody != null){
                    Toast.makeText(applicationContext,"You Are Success Sign Up", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<postSignUpResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "You Are Failed to Signup", Toast.LENGTH_SHORT).show()
            }

        })

        //eyJhbGciOiJSUzI1NiIsImtpZCI6IjY3YmFiYWFiYTEwNWFkZDZiM2ZiYjlmZjNmZjVmZTNkY2E0Y2VkYTEiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vbXktZmlyc3QtcHJvamVjdC1iZTY1ZCIsImF1ZCI6Im15LWZpcnN0LXByb2plY3QtYmU2NWQiLCJhdXRoX3RpbWUiOjE2ODY2NzIyODEsInVzZXJfaWQiOiJ1QlVVZXhZb0hOTklzcEVVVmNJTk9ubGVrOEsyIiwic3ViIjoidUJVVWV4WW9ITk5Jc3BFVVZjSU5PbmxlazhLMiIsImlhdCI6MTY4NjY3MjI4MSwiZXhwIjoxNjg2Njc1ODgxLCJlbWFpbCI6InJlemFwYWxldmllMjAxQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJyZXphcGFsZXZpZTIwMUBnbWFpbC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.dDqhrHL8Dosn-ZRBgOyah6iIWbw-OO0fX1DBUu-j-z_G4s0Eo8S3ECQsem536e3N1PGVIQwIzKpwyi41YLh-hzhUvFBPEbMDiPvl9zp1HvciOZuI0OHIggJ9iUWne_42FGZtuSwdjTjuvYqw7xvCCyopv8ZiqtBvUK0Dkc3hWW91aFY39toMtAm32zpYygW2-utEEmgT5W3mGou167XlWtoxTzWdzFIEz5-sP8J9Zxj9RvtPEPBnfiQp75Y0_72O5XJW6D291Dd9g4YvGNCNGCK2Y8Z9zKm5kxGtcnn11kWm_-QhR11hg8DBA8umBWH8U_bmqfpfrQvOituxbFR2Kg

    }

    private fun validasiregister(): Boolean{
        return when{
            TextUtils.isEmpty(binding.etFirstname.text.toString().trim{it <= ' '}) -> {
                showErrorSnackbar(resources.getString(R.string.err_nama_depan), true)
                false
            }
            TextUtils.isEmpty(binding.etAddress.text.toString().trim{it <= ' '}) -> {
                showErrorSnackbar(resources.getString(R.string.err_nama_belakang), true)
                false
            }
            TextUtils.isEmpty(binding.etEmail.text.toString().trim{it <= ' '}) -> {
                showErrorSnackbar(resources.getString(R.string.err_email), true)
                false
            }
            TextUtils.isEmpty(binding.etPasswordReg.text.toString().trim{it <= ' '}) -> {
                showErrorSnackbar(resources.getString(R.string.err_password), true)
                false
            }
            TextUtils.isEmpty(binding.etPasswordRegConfrm.text.toString().trim{it <= ' '}) -> {
                showErrorSnackbar(resources.getString(R.string.err_repeat_password), true)
                false
            }
            else -> {
               
                showErrorSnackbar("Data Anda Valid", false)
                true
            }
        }
    }

}
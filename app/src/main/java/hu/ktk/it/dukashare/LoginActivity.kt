package hu.ktk.it.dukashare

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hu.ktk.it.dukashare.databinding.LoginLayoutBinding
import hu.ktk.it.dukashare.model.Login
import hu.ktk.it.dukashare.model.User
import hu.ktk.it.dukashare.service.LoginService
import hu.ktk.it.dukashare.service.UserService

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginLayoutBinding.inflate(layoutInflater)

        binding.btnLogin.setOnClickListener {
            getUser()
        }
        this.supportActionBar?.hide()
        setContentView(binding.root)
    }

    private fun getUser() {
        LoginService().loginUser(Login(binding.emailField.editText?.text.toString(), binding.passwordField.editText?.text.toString())) {
            if (it != null) {
                ApplicationContext.user = it
                startActivity(Intent(this, ActivityDetailHostActivity::class.java))
            } else Toast.makeText(
                this@LoginActivity,
                "Incorrect email or password",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
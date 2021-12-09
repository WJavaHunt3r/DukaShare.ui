package hu.ktk.it.dukashare

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hu.ktk.it.dukashare.databinding.LoginLayoutBinding
import hu.ktk.it.dukashare.model.User
import hu.ktk.it.dukashare.service.UserService

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginLayoutBinding
    private val userService: UserService = UserService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginLayoutBinding.inflate(layoutInflater)

        binding.btnLogin.setOnClickListener {
            getUser(binding.emailField.editText?.text.toString())
        }
        this.supportActionBar?.hide()
        setContentView(binding.root)

    }

    private fun getUser(email: String) {
        userService.getUserByEmail(email) {
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
package hu.ktk.it.dukashare

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DukaShare : AppCompatActivity() {
    companion object{
        const val ACTIVITY_ID = "ACTIVITY_ID"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(!ApplicationContext.isLoggedIn) {
            startActivity(Intent(this, LoginActivity::class.java))
        }else{
            startActivity(Intent(this, ActivityDetailHostActivity::class.java))
        }
    }
}
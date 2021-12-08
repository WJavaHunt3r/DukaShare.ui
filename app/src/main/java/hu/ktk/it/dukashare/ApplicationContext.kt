package hu.ktk.it.dukashare

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.ktk.it.dukashare.model.Registration
import hu.ktk.it.dukashare.model.User
import kotlinx.coroutines.launch

class ApplicationContext : Application() {
    companion object{
        var user: User? = null
        var isLoggedIn: Boolean  = user != null
    }

    override fun onCreate() {
        super.onCreate()
    }
}
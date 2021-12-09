package hu.ktk.it.dukashare

import android.app.Application
import hu.ktk.it.dukashare.model.User

class ApplicationContext : Application() {
    companion object {
        var user: User? = null
        var isLoggedIn: Boolean = user != null
    }
}
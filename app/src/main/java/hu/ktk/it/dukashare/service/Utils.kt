package hu.ktk.it.dukashare.service

import hu.ktk.it.dukashare.ApplicationContext
import hu.ktk.it.dukashare.model.Registration
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset

object Utils {
    fun convertStringToOffsetDateTime(date: String): OffsetDateTime {
        val instant: Instant = Instant.parse(date + "Z")
        return instant.atOffset(ZoneOffset.UTC)
    }

    fun isUserRegistered(regs: List<Registration>): Boolean {
        if (ApplicationContext.user != null) {
            for (reg: Registration in regs) {
                if (reg.userId == ApplicationContext.user!!.id) return true
            }
        }
        return false
    }

    fun updateUser() {
        UserService().getUserById(ApplicationContext.user?.id!!) {
            if (it != null) {
                ApplicationContext.user = it
            }
        }
    }
}
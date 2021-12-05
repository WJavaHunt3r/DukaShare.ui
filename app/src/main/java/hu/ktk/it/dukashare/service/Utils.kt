package hu.ktk.it.dukashare.service

import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset

object Utils {
    fun convertStringToOffsetDateTime(date: String): OffsetDateTime{
        val instant: Instant = Instant.parse(date +"Z")
        return instant.atOffset(ZoneOffset.UTC)
    }
}
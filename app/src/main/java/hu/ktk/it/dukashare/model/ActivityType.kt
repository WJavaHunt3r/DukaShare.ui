package hu.ktk.it.dukashare.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActivityType (
    val id: Long,
    val name: String
        ):Parcelable{

}
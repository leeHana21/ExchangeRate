package com.github.hanalee.exchangerateproject.domain.retrofit.entity.response


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RateResponse(
    @SerializedName("privacy")
    val privacy: String = "",
    @SerializedName("quotes")
    val quotes: Quotes = Quotes(),
    @SerializedName("source")
    val source: String = "",
    @SerializedName("success")
    val success: Boolean = false,
    @SerializedName("terms")
    val terms: String = "",
    @SerializedName("timestamp")
    val timestamp: Int = 0
) : Parcelable {
    @Keep
    @Parcelize
    data class Quotes(
        @SerializedName("USDKRW")
        val usdKrw: Double = 0.0,
        @SerializedName("USDJPY")
        val usdJpy: Double = 0.0,
        @SerializedName("USDPHP")
        val usdPhp: Double = 0.0,
    ) : Parcelable
}
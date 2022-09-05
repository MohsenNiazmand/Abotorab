package ir.nilva.abotorab.helper

import com.google.gson.annotations.SerializedName

data class CountryModel(@SerializedName("name") val name: String,
                        @SerializedName("en_name") val enName: String,
                        @SerializedName("native_name") val nativeName: String,
                        @SerializedName("capital") val capital: String,
                        @SerializedName("en_capital") val enCapital: String,
                        @SerializedName("alpha_2") val alpha2: String,
                        @SerializedName("alpha_3") val alpha3: String,
                        @SerializedName("phone_code") val phoneCode: String)

class CountryList(internal var countries: List<CountryModel>)

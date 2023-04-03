package com.pacom.baseproject.mode.reponse


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TestResponse(
    @SerializedName("body")
    var body: String = "", // quia et suscipit suscipit recusandae consequuntur expedita et cum reprehenderit molestiae ut ut quas totam nostrum rerum est autem sunt rem eveniet architecto
    @SerializedName("id")
    var id: Int = 0, // 1
    @SerializedName("title")
    var title: String = "", // sunt aut facere repellat provident occaecati excepturi optio reprehenderit
    @SerializedName("userId")
    var userId: Int = 0 // 1
)
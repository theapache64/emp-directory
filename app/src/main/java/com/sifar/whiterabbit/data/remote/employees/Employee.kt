package com.sifar.whiterabbit.data.remote.employees

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/**
 * Created by theapache64 : Feb 17 Wed,2021 @ 17:37
 */
@Entity(
    tableName = "employees"
)
@JsonClass(generateAdapter = true)
data class Employee(
    @PrimaryKey
    @Json(name = "id")
    val id: Int, // 10

    @Json(name = "name")
    val name: String, // Clementina DuBuque

    @Json(name = "username")
    val username: String, // Moriah.Stanton

    @Json(name = "email")
    val email: String, // Rey.Padberg@karina.biz

    @Json(name = "profile_image")
    val profileImage: String?, // https://randomuser.me/api/portraits/men/11.jpg

    @Embedded
    @Json(name = "address")
    val address: Address,

    @Json(name = "phone")
    val phone: String?, // 024-648-3804

    @Json(name = "website")
    val website: String?, // ambrose.net

    @Embedded
    @Json(name = "company")
    val company: Company?
) {
    @JsonClass(generateAdapter = true)
    data class Address(
        @Json(name = "street")
        val street: String, // Kattie Turnpike
        @Json(name = "suite")
        val suite: String, // Suite 198
        @Json(name = "city")
        val city: String, // Lebsackbury
        @Json(name = "zipcode")
        val zipcode: String, // 31428-2261

        @Embedded
        @Json(name = "geo")
        val geo: Geo
    ) {
        @JsonClass(generateAdapter = true)
        data class Geo(
            @Json(name = "lat")
            val lat: String, // -38.2386
            @Json(name = "lng")
            val lng: String // 57.2232
        )

    }

    @JsonClass(generateAdapter = true)
    data class Company(
        @Json(name = "name")
        val companyName: String, // Hoeger LLC
        @Json(name = "catchPhrase")
        val catchPhrase: String, // Centralized empowering task-force
        @Json(name = "bs")
        val bs: String // target end-to-end models
    )
}